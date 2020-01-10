#include <ArduinoWebsockets.h>
#include <WiFi.h>
const char* ssid = "Inther"; //Enter SSID
const char* password = "inth3rmoldova"; //Enter Password
const char* websockets_server_host = "echo.websocket.org"; //Enter server adress
const uint16_t websockets_server_port = 80; // Enter server port

using namespace websockets;


// defines pins numbers
const int trigPin = 2;
const int echoPin = 5;

// defines variables
long duration;
int distance;
boolean isAvailable1; 
WebsocketsClient client;
void setup() {
pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin, INPUT); // Sets the echoPin as an Input
Serial.begin(9600); // Starts the serial communication

// Connect to wifi
    WiFi.begin(ssid, password);

    // Wait some time to connect to wifi
    for(int i = 0; i < 10 && WiFi.status() != WL_CONNECTED; i++) {
        Serial.print(".");
        delay(1000);
    }

    // Check if connected to wifi
    if(WiFi.status() != WL_CONNECTED) {
        Serial.println("No Wifi!");
        return;
    }

    Serial.println("Connected to Wifi, Connecting to server.");
    // try to connect to Websockets server
    bool connected = client.connect(websockets_server_host, websockets_server_port, "/");
    if(connected) {
        Serial.println("Connected!");

        // test
//        char buf[16]; // need a buffer for that
//        
// 
//      sprintf(buf,"%d",distance);
//      const char* p = buf;
      
        
//        client.send(p);
    } else {
        Serial.println("Not Connected!");
    }
    
    // run callback when messages are received
    client.onMessage([&](WebsocketsMessage message){
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
      distance= duration*0.034/2;
// converting int to char*
      char buf[16]; // need a buffer for that
      sprintf(buf,"%d",distance);
      const char* p = buf;
      client.send(p);


// let the websockets client check for incoming messages
    if(client.available()) {
        client.poll();
    }
    delay(500);
    
// Prints the distance on the Serial Monitor
//Serial.print("Distance: ");
//Serial.println(distance);
//
//if (distance<=20){
//  Serial.println("Park place is occupied:");
//  isAvailable1= true;
//  Serial.println(isAvailable1);
//
//}
//else {
//  Serial.println("Park place is free");
//  isAvailable1=false;
//  Serial.println(isAvailable1);
//
//}


}