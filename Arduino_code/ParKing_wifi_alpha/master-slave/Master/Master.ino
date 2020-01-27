#include <ArduinoWebsockets.h>
#include <WiFi.h>

#define LED         13  // Declare LED pin
#define MASTER_EN   5   // connected to RS485 Enable pin
#define SLAVE_ONE   1   // declare id of first slave
#define SLAVE_TWO   2   // deckare id of second slave

String slave_id;
String status_of_lot;
String last_known_status;

//Wifi
using namespace websockets;
WebsocketsClient client;

const char *ssid = "Inther";                         //Enter SSID
const char *password = "inth3rmoldova";              //Enter Password
const char *websockets_server_host = "172.17.41.36"; //Enter server address
const uint16_t websockets_server_port = 8080;        // Enter server port

//WebSocket
//sample message for WebSocket
char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

//security
char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2"; //some security token to verify connection ({SHA1}"arduino")


void setup() {
  pinMode(LED , OUTPUT);            // Declare LED pin as output
  pinMode(MASTER_EN , OUTPUT);      // Declare Enable pin as output
  Serial.begin(9600);               // set serial communication baudrate 
  digitalWrite(MASTER_EN , LOW);    // Make Enable pin low
                                    // Receiving mode ON 

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

void loop() {
  String answer;
  status_of_lot = ""; 
  
  digitalWrite(MASTER_EN , HIGH);     // Make Enable pin high to send Data
  delay(1000);                        // required minimum delay of 5ms
  Serial.println(SLAVE_ONE);          // Send character A serially
  Serial.flush();                     // wait for transmission of data
  digitalWrite(MASTER_EN , LOW);      // Receiving mode ON

  delay(1500);
  while(Serial.available()) {
    answer = Serial.readString();
  }

  slave_id = answer.substring(0, 1);
  status_of_lot = answer.substring(2);

  Serial.println("Lot: " + slave_id + String(status_of_lot));

  if (status_of_lot != last_known_status) {
    client.send(msg + String(slave_id) + String("\", \"status\":\"") + status_of_lot + String("\", \"token\":\"") + security_token + String("\"}"));
    last_known_status = status_of_lot;
  }


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
