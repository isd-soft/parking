#include <ArduinoWebsockets.h>

#include <WiFi.h>

//Sonar pins
#define pinTrigger 2 // Trigger Pin for sonar
#define pinEcho 5    // Echo Pin for sonar

//Laser detector pins
const int pinLaser = 4;     // output signal pin of laser module/laser pointer
const int pinReceiver = 18; // input signal pin of receiver/detector (the used module does only return a digital state)

//Sonar variables
int inRange = 45;                  //Wide Range First sight of Target
int targetParkingRange = 12;       //Minimal Parking Range to Target
const int noiseRejectPercent = 40; //Percentage of reading closeness for rejection filter
long duration, distance, lastDuration, unfilteredDistance, filteredSonarDistance, rawSonarDistance;
const unsigned int maxDuration = 11650; // around 200 cm, the sensor gets flaky at greater distances.
const long speed_of_sound = 29.1;       // speed of sound microseconds per centimeter

//parking lot
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
const char *websockets_server_host = "172.17.40.57"; //Enter server address
const uint16_t websockets_server_port = 8080;        // Enter server port

//security
char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2"; //some security token to verify connection ({SHA1}"arduino")

void setup()
{
    // put your setup code here, to run once:

    //Laser setup
    pinMode(pinLaser, OUTPUT);    // set the laser pin to output mode
    pinMode(pinReceiver, INPUT);  // set the laser pin to output mode
    digitalWrite(pinLaser, HIGH); // emit red laser

    Serial.begin(9600);

    //Sensor Connections
    pinMode(pinTrigger, OUTPUT);
    pinMode(pinEcho, INPUT);

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

    //sample message for WebSocket
    char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

    int laserValue = digitalRead(pinReceiver); // receiver/detector send either LOW or HIGH (no analog values!)

    readFromSingleSonar();

    //data form sonar

    // if (filteredSonarDistance >= 20 && isLotFreeSonar == false) {
    //     Serial.println("Lot is free! Message from sonar. Parking lot 1");
    //     client.send(msg + String(1) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") +
    //                 security_token + String("\"}"));
    //     Serial.println(filteredSonarDistance);
    //     isLotFree = true;
    // }

    // if (filteredSonarDistance < 20 && isLotFree == true) {
    //     Serial.println("Lot is occupied! Message from sonar. Parking lot 1");
    //     client.send(msg + String(1) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") +
    //                 security_token + String("\"}"));
    //     Serial.println(filteredSonarDistance);
    //     isLotFree = false;
    // }

    if (!sonarInitialized || (filteredSonarDistance >= 20 && !isLotFreeSonar) || (filteredSonarDistance < 20  && isLotFreeSonar))
        {
            sonarInitialized = true;
            isLotFreeSonar = filteredSonarDistance >= 20;
            client.send(isLotFreeSonar ? msg + String(1) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") +
                                             security_token + String("\"}")
                                       : msg + String(1) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + security_token + String("\"}"));
        }

    //data from laser

    if(!laserInitialized || (laserValue == 0 && isLotFreeLaser) || (laserValue == 1 && isLotFreeLaser)){
        laserInitialized = true;
        isLotFreeLaser = laserValue == 0;
        client.send(isLotFreeLaser ? msg + String(2) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") +
                    security_token + String("\"}") : 
                    msg + String(2) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") +
                    security_token + String("\"}")
                );
    }

    // if (laserValue == 1 && isLotFree1 == false)
    // {
    //     Serial.println("Lot is free! Message from laser. Parking lot 2");
    //     client.send(msg + String(2) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") +
    //                 security_token + String("\"}"));
    //     isLotFree1 = true;
    // }

    // if (laserValue == 0 && isLotFree1 == true)
    // {
    //     Serial.println("Lot is occupied! Message from laser. Parking lot 2");
    //     client.send(msg + String(2) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") +
    //                 security_token + String("\"}"));
    //     isLotFree1 = false;
    // }

    // let the WebSockets client check for incoming messages
    if (client.available())
    {
        client.poll();
    }

    delay(500);
}

void readFromSingleSonar()
{

    readSonarSensor(pinTrigger, pinEcho);

    filteredSonarDistance = distance;
    rawSonarDistance = unfilteredDistance;

    delay(50); //Delay 50ms before next reading.
}

void readSonarSensor(int trigPin, int echoPin)
{

    digitalWrite(trigPin, LOW);
    delayMicroseconds(2);
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);
    duration = pulseIn(echoPin, HIGH);

    unfilteredDistance = (duration / 2) / speed_of_sound; //Stores preliminary reading to compare

    if (duration <= 8)
        duration = ((inRange + 1) * speed_of_sound * 2);

    //Rejects very low readings, kicks readout to outside detection range
    if (lastDuration == 0)
        lastDuration = duration;

    //Compensation parameters for initial start-up
    if (duration > (5 * maxDuration))
        duration = lastDuration;

    //Rejects any reading defined to be out of sensor capacity (>1000)
    //Sets the fault reading to the last known "successful" reading
    if (duration > maxDuration)
        duration = maxDuration;

    //Caps Reading output at defined maximum distance (~200)
    if ((duration - lastDuration) < ((-1) * (noiseRejectPercent / 100) * lastDuration))
    {
        distance = (lastDuration / 2) / speed_of_sound; //Noise filter for low range drops
    }

    distance = (duration / 2) / speed_of_sound;
    lastDuration = duration; //Stores "successful" reading for filter compensation
}

void onEventsCallback(WebsocketsEvent event, String data)
{

    if (event == WebsocketsEvent::ConnectionOpened)
    {
        Serial.println("WebSocket connection opened!");
    }
    else if (event == WebsocketsEvent::ConnectionClosed)
    {
        Serial.println("WebSocket connection closed!");
        Serial.println("Trying to reconnect to WebSocket ...");
        delay(10000);
        bool connected = client.connect(websockets_server_host, websockets_server_port, "/test");
    }
    else if (event == WebsocketsEvent::GotPing)
    {
        Serial.println("Got a Ping!");
    }
    else if (event == WebsocketsEvent::GotPong)
    {
        Serial.println("Got a Pong!");
    }
}
