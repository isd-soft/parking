#include <ArduinoWebsockets.h>
#include <NewPing.h>
#include <WiFi.h>

//Sonar details
#define TRIGGER_PIN  2  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN     5  // Arduino pin tied to echo pin on the ultrasonic sensor.
#define MAX_DISTANCE 500 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

//Laser detector pins
#define pinLaser 4     // output signal pin of laser module/laser pointer
#define pinReceiver 18 // input signal pin of receiver/detector (the used module does only return a digital state)

//Sonar variables
unsigned int filteredSonarDistance; // real time distance
unsigned int lastSonarDistance; // last known distance, used to prevent double values
long targetDistance = 100; // target distance value when the status should be trigerred

//parking lot
int lotId;                        // ID of the parking lot
boolean isLotFreeSonar = false;   // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean isLotFreeLaser = false;   // Laser's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized = false; // initialized flag for sonar
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

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE); // NewPing setup of pins and maximum distance.

void setup()
{
    //Laser setup
    pinMode(pinLaser, OUTPUT);    // set the laser pin to output mode
    pinMode(pinReceiver, INPUT);  // set the laser pin to output mode
    digitalWrite(pinLaser, HIGH); // emit red laser

    Serial.begin(9600);

    //Sensor Connections
    pinMode(TRIGGER_PIN, OUTPUT);
    pinMode(ECHO_PIN, INPUT);

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


    filteredSonarDistance = sonar.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar
//      Serial.print("filtered: "); Serial.println(filteredSonarDistance);
      
    //data form sonar
    if (filteredSonarDistance != lastSonarDistance) { 
      if (!sonarInitialized || ((filteredSonarDistance >= targetDistance || filteredSonarDistance == 0)&& !isLotFreeSonar) 
          || (filteredSonarDistance < targetDistance && isLotFreeSonar))
      {
          lotId = 1;
          sonarInitialized = true;
          isLotFreeSonar = (filteredSonarDistance >= targetDistance || filteredSonarDistance == 0) ? true : false;
          Serial.println(isLotFreeSonar ? "SONAR : FREE " + String(filteredSonarDistance) : "SONAR : OCCUPIED " + String(filteredSonarDistance));
          client.send(isLotFreeSonar ? msg + String(lotId) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") + 
                                      security_token + String("\"}")
                                      : msg + String(lotId) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + 
                                      security_token + String("\"}"));
      } 
      lastSonarDistance = filteredSonarDistance;
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
