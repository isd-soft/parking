#include <ArduinoWebsockets.h>

#include <WiFi.h>

const char *ssid = "Inther";                            //Enter SSID
const char *password = "inth3rmoldova";                 //Enter Password
const char *websockets_server_host = "172.17.41.36";    //Enter server address
const uint16_t websockets_server_port = 8080;           // Enter server port

using namespace websockets;

boolean isLotFree = false;                                              // boolean variable to define if the status of parking lot was changed or not
WebsocketsClient client;

int test_lot_number = 1;                                                //hardcoded test number

String status_free = "FREE";                                            //if string not compile use char *
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";

char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2";      //some security token to verify connection ({SHA1}"arduino")


const int pinLaser = 2; // output signal pin of laser module/laser pointer
const int pinReceiver = 18; // input signal pin of receiver/detector (the used module does only return a digital state)

void setup() {
  pinMode(pinLaser, OUTPUT); // set the laser pin to output mode
  pinMode(pinReceiver, INPUT); // set the laser pin to output mode
  digitalWrite(pinLaser,  HIGH); // emit red laser
  Serial.begin(9600); // Setup serial connection for print out to console
  Serial.println("START");


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
  bool connected = client.connect(websockets_server_host, websockets_server_port, "/test");
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
  int value = digitalRead(pinReceiver); // receiver/detector send either LOW or HIGH (no analog values!)
  Serial.println(value); // send value to console
  delay(100); // wait for 1000ms

  char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

  if (value == 0 && isLotFree == false) {
    Serial.println("Lot is free!");
    client.send(msg + String(test_lot_number) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") + security_token + String("\"}"));
    isLotFree = true;
  }

  if (value == 1 && isLotFree == true) {
    Serial.println("Lot is occupied!");
    client.send(msg + String(test_lot_number) + String("\", \"status\":\"") + status_occupied + String("\", \"token\":\"") + security_token + String("\"}"));
    isLotFree = false;
  }

  // let the websockets client check for incoming messages
  if (client.available()) {
    client.poll();
  }

  delay(500);
}
