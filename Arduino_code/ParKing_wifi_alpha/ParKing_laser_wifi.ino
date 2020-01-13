#include <ArduinoWebsockets.h>
#include <WiFi.h>

#define DETECT 2                // pin 2 for  sensor
#define ACTION 8                // pin 8 for action to do something


const char *ssid = "Inther";                            //Enter SSID
const char *password = "inth3rmoldova";                 //Enter Password
const char *websockets_server_host = "172.17.41.30";    //Enter server address
const uint16_t websockets_server_port = 8080;           // Enter server port

using namespace websockets;

WebsocketsClient client;

int test_lot_number = 1;                                                //hardcoded test number

boolean isLotFree = false;                                              // boolean variable to define if the status of parking lot was changed or not

String status_free = "FREE";                                            //if string not compile use char *
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";

char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2";      //some security token to verify connection ({SHA1}"arduino")

void setup() {
    Serial.begin(9600);
    Serial.println("Laser Module Test");
    pinMode(DETECT, INPUT);         //define detect input pin
    pinMode(ACTION, OUTPUT);        //define ACTION output pin

    / Connect to wifi
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

    int detected = digitalRead(DETECT);     // read Laser sensor
    char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";


    if (detected == HIGH) {
        digitalWrite(ACTION, HIGH);         // set the buzzer ON
        Serial.println("Detected!");
        isLotFree = true;
        client.send(msg + test_lot_number + "\", \"status\":\"" + status_free + "\", \"token\":\"" + security_token + "\"}");
    } 
    
    if (detected == LOW && isLotFree) {
        digitalWrite(ACTION, LOW);          // Set the buzzer OFF
        Serial.println("No laser");
        client.send(msg + test_lot_number + "\", \"status\":\"" + status_occupied + "\", \"token\":\"" + security_token + "\"}");
        isLotFree = false;
    }

    // let the websockets client check for incoming messages
    if (client.available()) {
        client.poll();
    }

    delay(200);
}
