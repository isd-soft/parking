#include <ArduinoWebsockets.h>

#include <WiFi.h>

const char *ssid = "Inther";                            //Enter SSID
const char *password = "inth3rmoldova";                 //Enter Password
const char *websockets_server_host = "172.17.41.30";    //Enter server address
const uint16_t websockets_server_port = 8080;           // Enter server port

using namespace websockets;

boolean isLotFree = false;                                              // boolean variable to define if the status of parking lot was changed or not

WebsocketsClient client;

int test_lot_number = 1;                                                //hardcoded test number

String status_free = "FREE";                                            //if string not compile use char *
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";

char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2";      //some security token to verify connection ({SHA1}"arduino")


long readUltrasonicDistance(int triggerPin, int echoPin)
{
  pinMode(triggerPin, OUTPUT);  // Clear the trigger
  digitalWrite(triggerPin, LOW);
  delayMicroseconds(2);
  // Sets the trigger pin to HIGH state for 10 microseconds
  digitalWrite(triggerPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(triggerPin, LOW);
  pinMode(echoPin, INPUT);
  // Reads the echo pin, and returns the sound wave travel time in microseconds
  return pulseIn(echoPin, HIGH);
}

void setup() {
    Serial.begin(9600);         // Starts the serial communication
    pinMode(13, OUTPUT);


    // Connect to wifi
    WiFi.begin(ssid, password);

    // Wait some time to connect to wifi
    for (int i = 0; i < 10 && WiFi.status() != WL_CONNECTED; i++) {
        Serial.print(".");
        delay(1000);
    }

    // Check if connected to wifi
    if (WiFi.status() != WL_CONNECTED) {
        Serial.println("No Wifi!");
        return;
    }

    Serial.println("Connected to Wifi, Connecting to server.");
    // try to connect to Websockets server
    bool connected = client.connect(websockets_server_host, websockets_server_port, "/arduino");
    if (connected) {
        Serial.println("Connected!");

    } else {
        Serial.println("Not Connected!");
    }

    // run callback when messages are received
    client.onMessage([&](WebsocketsMessage message) {
        Serial.print("Got Message: ");
        Serial.println(message.data());
    });
}

void loop() {
    Serial.println(0.01723 * readUltrasonicDistance(8, 8));

    char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

  if (0.01723 * readUltrasonicDistance(8, 8) > 100  && isLotFree == false) {
        digitalWrite(13, HIGH);
        Serial.println("free");
        client.send(msg + test_lot_number + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") + security_token + String("\"}"));
        isLotFree = true;
    } 

    if (0.01723 * readUltrasonicDistance(8, 8) < 100 && isLotFree == true) {
        digitalWrite(13, LOW);
        Serial.println("occupied");
        client.send(msg + test_lot_number + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + security_token + String("\"}"));
        isLotFree = false;
    }

    // let the websockets client check for incoming messages
    if (client.available()) {
        client.poll();
    }

    delay(500);
}
