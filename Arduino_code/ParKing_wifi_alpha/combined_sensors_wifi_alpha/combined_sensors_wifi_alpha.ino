#include <ArduinoWebsockets.h>
#include <NewPing.h>
#include <WiFi.h>

//Sonar 1 details
#define TRIGGER_PIN_1  2  // Arduino pin tied to trigger pin on the ultrasonic sensor 1.
#define ECHO_PIN_1     5  // Arduino pin tied to echo pin on the ultrasonic sensor 1.

//Sonar 2 details
#define TRIGGER_PIN_2  4  // Arduino pin tied to trigger pin on the ultrasonic sensor 1.
#define ECHO_PIN_2     19  // Arduino pin tied to echo pin on the ultrasonic sensor 1.

#define MAX_DISTANCE 500 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

//Laser detector pins
#define pinLaser 4     // output signal pin of laser module/laser pointer
#define pinReceiver 18 // input signal pin of receiver/detector (the used module does only return a digital state)

//Sonar variables
unsigned int filteredSonarDistance_1; // real time distance from sonar 1
unsigned int lastSonarDistance_1; // last known distance of sonar 1, used to prevent double values

unsigned int filteredSonarDistance_2; // real time distance from sonar 2
unsigned int lastSonarDistance_2; // last known distance of sonar 2, used to prevent double values

long targetDistance = 100; // target distance value when the status should be trigerred

//parking lot
int lotId;                        // ID of the parking lot
boolean isLotFreeSonar_1 = false;   // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean isLotFreeSonar_2 = false;   // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean isLotFreeLaser = false;   // Laser's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized_1 = false; // initialized flag for sonar
boolean sonarInitialized_2 = false; // initialized flag for sonar
boolean laserInitialized = false; // initialized flag for laser

//parking lot states
String status_free = "FREE";
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";

//Wifi
using namespace websockets;
WebsocketsClient client;

const char *ssid = "Inther";                         //Enter SSID
const char *password = "inth3rmoldova";              //Enter Password
const char *websockets_server_host = "172.17.41.34"; //Enter server address
const uint16_t websockets_server_port = 8080;        // Enter server port

//WebSocket
//sample message for WebSocket
char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

//security
char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2"; //some security token to verify connection ({SHA1}"arduino")

NewPing sonar_1(TRIGGER_PIN_1, ECHO_PIN_1, MAX_DISTANCE); // NewPing setup of pins and maximum distance.
NewPing sonar_2(TRIGGER_PIN_2, ECHO_PIN_2, MAX_DISTANCE); // NewPing setup of pins and maximum distance.


void setup()
{
    //Laser setup
    pinMode(pinLaser, OUTPUT);    // set the laser pin to output mode
    pinMode(pinReceiver, INPUT);  // set the laser pin to output mode
    digitalWrite(pinLaser, HIGH); // emit red laser

    Serial.begin(9600);

    //Sensor Connections
    pinMode(TRIGGER_PIN_1, OUTPUT);
    pinMode(ECHO_PIN_1, INPUT);

    pinMode(TRIGGER_PIN_2, OUTPUT);
    pinMode(ECHO_PIN_2, INPUT);

    // Connect to wifi
    WiFi.begin(ssid, password);

    // Wait some time to connect to wifi
    for (int i = 0; i < 10 && WiFi.status() != WL_CONNECTED; i++)
    {
        Serial.print(".");
        delay(1000);
    }

    // Check if connected to wifi
    if (WiFi.status() != WL_CONNECTED)
    {
        Serial.println("No Wifi!");
        return;
    }

    Serial.println("Connected to Wifi, Connecting to server.");
    // try to connect to WebSockets server
    bool connected = client.connect(websockets_server_host, websockets_server_port, "/test");
    if (connected)
    {
        Serial.println("Connected to WebSocket!");
    }
    else
    {
        Serial.println("Not Connected to WebSocket!");
    }

    // run callback when messages are received
    client.onMessage([&](WebsocketsMessage message) {
        Serial.print("Got Message: ");
        Serial.println(message.data());
    });

    client.onEvent(onEventsCallback); 
    
     
}

void loop()
{
    int laserValue = digitalRead(pinReceiver); // receiver/detector send either LOW or HIGH (no analog values!)


    filteredSonarDistance_1 = sonar_1.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 1
    filteredSonarDistance_2 = sonar_2.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 2
      Serial.print("filtered_1: "); Serial.println(filteredSonarDistance_1); 
//      Serial.print("filtered_2: "); Serial.println(filteredSonarDistance_2);
      
    //data form sonar 1
    if (filteredSonarDistance_1 != lastSonarDistance_1) { 
      if (!sonarInitialized_1 || ((filteredSonarDistance_1 >= targetDistance || filteredSonarDistance_1 == 0)&& !isLotFreeSonar_1) 
          || (filteredSonarDistance_1 < targetDistance && isLotFreeSonar_1))
      {
          lotId = 1;
          sonarInitialized_1 = true;
          isLotFreeSonar_1 = (filteredSonarDistance_1 >= targetDistance || filteredSonarDistance_1 == 0) ? true : false;
          Serial.println(isLotFreeSonar_1 ? "SONAR_1 : FREE " + String(filteredSonarDistance_1) : "SONAR_1 : OCCUPIED " + String(filteredSonarDistance_1));
          client.send(isLotFreeSonar_1 ? msg + String(lotId) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") + 
                                      security_token + String("\"}")
                                      : msg + String(lotId) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + 
                                      security_token + String("\"}"));
      } 
      lastSonarDistance_1 = filteredSonarDistance_1;
    }

    //data form sonar 2
    if (filteredSonarDistance_2 != lastSonarDistance_2) { 
      if (!sonarInitialized_2 || ((filteredSonarDistance_2 >= targetDistance || filteredSonarDistance_2 == 0)&& !isLotFreeSonar_2) 
          || (filteredSonarDistance_2 < targetDistance && isLotFreeSonar_2))
      {
          lotId = 2;
          sonarInitialized_2 = true;
          isLotFreeSonar_2 = (filteredSonarDistance_2 >= targetDistance || filteredSonarDistance_2 == 0) ? true : false;
          Serial.println(isLotFreeSonar_2 ? "SONAR_2 : FREE " + String(filteredSonarDistance_2) : "SONAR_2 : OCCUPIED " + String(filteredSonarDistance_2));
          client.send(isLotFreeSonar_2 ? msg + String(lotId) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") + 
                                      security_token + String("\"}")
                                      : msg + String(lotId) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + 
                                      security_token + String("\"}"));
      } 
      lastSonarDistance_2 = filteredSonarDistance_2;
    }

    //data from laser
//    if (!laserInitialized || (laserValue == 0 && !isLotFreeLaser) || (laserValue == 1 && isLotFreeLaser))
//    {
//        lotId = 2;
//        laserInitialized = true;
//        isLotFreeLaser = laserValue == 0;
//        Serial.println(isLotFreeLaser ? "LASER : FREE " + String(filteredSonarDistance) : "LASER : OCCUPIED " + String(filteredSonarDistance));
//
//        client.send(isLotFreeLaser ? msg + String(lotId) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") +
//                                         security_token + String("\"}")
//                                   : msg + String(lotId) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") +
//                                         security_token + String("\"}"));
//    }

    // let the WebSockets client check for incoming messages
    if (client.available())
    {
        client.poll();
        client.ping();

    }

}
void(* resetFunc) (void) = 0;


void onEventsCallback(WebsocketsEvent event, String data)
{

    if (event == WebsocketsEvent::ConnectionOpened)
    {
        Serial.println("WebSocket connection opened!");
        client.send("test mess");
    }
    else if (event == WebsocketsEvent::ConnectionClosed)
    {
        Serial.println("WebSocket connection closed!");
        Serial.println("Trying to reconnect to WebSocket ...");
        delay(10000);
        bool connected = client.connect(websockets_server_host, websockets_server_port, "/test");
        resetFunc();

        
    }
    else if (event == WebsocketsEvent::GotPing)
    {
        Serial.println("Got a Ping!");
    }
    else if (event == WebsocketsEvent::GotPong)
    {
//        Serial.println("Got a Pong!");
    }
}
