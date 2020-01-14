#include <ArduinoWebsockets.h>

#include <WiFi.h>

const char *ssid = "Inther";                            //Enter SSID
const char *password = "inth3rmoldova";                 //Enter Password
const char *websockets_server_host = "172.17.41.30";    //Enter server address
const uint16_t websockets_server_port = 8080;           // Enter server port

using namespace websockets;

// defines pins numbers
const int trigPin = 2;
const int echoPin = 5;

// defines variables
long duration;
int distance;

boolean isLotFree = false;                                              // boolean variable to define if the status of parking lot was changed or not

WebsocketsClient client;

int test_lot_number = 1;                                                //hardcoded test number

String status_free = "FREE";                                            //if string not compile use char *
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";

char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2";      //some security token to verify connection ({SHA1}"arduino")


void setup() {
    pinMode(trigPin, OUTPUT);   // Sets the trigPin as an Output
    pinMode(echoPin, INPUT);    // Sets the echoPin as an Input
    Serial.begin(9600);         // Starts the serial communication

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
    // Clears the trigPin
    digitalWrite(trigPin, LOW);
    delayMicroseconds(2);

    // Sets the trigPin on HIGH state for 10 micro seconds
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);

    // Reads the echoPin, returns the sound wave travel time in microseconds
    duration = pulseIn(echoPin, HIGH);

    // Calculating the distance
    distance = duration * 0.034 / 2;

    // converting int to char*
    //char buf[16]; // need a buffer for that
    //sprintf(buf, "%d", distance);
    //const char * p = buf;

    char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

    if (distance > 200) {
        client.send(msg + test_lot_number + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") + security_token + String("\"}"));
        isLotFree = true;
    } 

    if (distance < 200 && isLotFree){ 
        client.send(msg + test_lot_number + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + security_token + String("\"}"));
        isLotFree = false;
    }

    // let the websockets client check for incoming messages
    if (client.available()) {
        client.poll();
    }

    delay(500);
}
