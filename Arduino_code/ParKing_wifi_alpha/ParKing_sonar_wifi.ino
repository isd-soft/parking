#include <ArduinoWebsockets.h>

#include <WiFi.h>

//Sonar pins
#define trigger1 2                              // Trigger Pin
#define echo1 5                                 // Echo Pin


const char *ssid = "Inther";                            //Enter SSID
const char *password = "inth3rmoldova";                 //Enter Password
const char *websockets_server_host = "172.17.41.101";   //Enter server address
const uint16_t websockets_server_port = 8080;           // Enter server port


int inRange = 45;                               //Wide Range First sight of Target
int TargetRange = 12;                           //Minimal Parking Range to Target
const int NoiseReject = 40;                     //Percentage of reading closeness for rejection filter
long duration, distance, lastDuration, unfiltered, Sonar, RawSonar;
const unsigned int maxDuration = 11650;         // around 200 cm, the sensor gets flaky at greater distances.
const long speed_of_sound = 29.1;               // speed of sound microseconds per centimeter
boolean isLotFree = false;                      // boolean variable to define if the status of parking lot was changed or not

using namespace websockets;
WebsocketsClient client;

int test_lot_number = 1;                                                //hardcoded test number

String status_free = "FREE";                                            //if string not compile use char *
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";

char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2";      //some security token to verify connection ({SHA1}"arduino")

void setup() {
    Serial.begin(9600);

    //Sensor Connections
    pinMode(trigger1, OUTPUT);
    pinMode(echo1, INPUT);

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
    // try to connect to WebSockets server
    bool connected = client.connect(websockets_server_host, websockets_server_port, "/test");
    if (connected) {
        Serial.println("Connected to WebSocket!");

    } else {
        Serial.println("Not Connected to WebSocket!");
    }

    // run callback when messages are received
    //  client.onMessage([&](WebsocketsMessage message) {
    //    Serial.print("Got Message: ");
    //    Serial.println(message.data());
    //  });

    client.onEvent(onEventsCallback);

}

void loop() {
    SingleSonar();

    ////// PRINT FOR PROOF CHECKING //////
    //  Serial.print("Filtered Sonar = ");
    //Serial.println(Sonar);
    //  Serial.print(" cm, ");
    //  Serial.print("Unfiltered Sonar = ");
    //  Serial.print(RawSonar);
    //  Serial.print(" cm");
    //  Serial.println();
    ////// END OF PRINT-CHECK //////

    char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

    if (Sonar >= 20 && isLotFree == false) {
        Serial.println("Lot is free!");
        Serial.println(Sonar);
        client.send(
                msg + String(test_lot_number) + String("\", \"status\":\"") + status_free + String("\", \"token\":\"") +
                security_token + String("\"}"));
        isLotFree = true;
    }

    if (Sonar < 20 && isLotFree == true) {
        Serial.println("Lot is occupied!");
        Serial.println(Sonar);
        client.send(msg + String(test_lot_number) + String("\", \"status\":\"") + status_occupied +
                    String("\", \"token\":\"") + security_token + String("\"}"));
        isLotFree = false;
    }

    //  if (Sonar == 0) {
    //    Serial.println("Status Lot is uknown!");
    //    Serial.println(Sonar);
    //    client.send(msg + String(test_lot_number) + String("\", \"status\":\"") + status_unknown + String("\", \"token\":\"") + security_token + String("\"}"));
    //  }

    // let the WebSockets client check for incoming messages
    if (client.available()) {
        client.poll();
    }

    delay(500);
}

void SonarSensor(int trigPin, int echoPin) {

    digitalWrite(trigPin, LOW);
    delayMicroseconds(2);
    digitalWrite(trigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);
    duration = pulseIn(echoPin, HIGH);

    unfiltered = (duration / 2) / speed_of_sound;               //Stores preliminary reading to compare
    if (duration <= 8) duration = ((inRange + 1) * speed_of_sound * 2);

    //Rejects very low readings, kicks readout to outside detection range
    if (lastDuration == 0) lastDuration = duration;

    //Compensation parameters for intial start-up
    if (duration > (5 * maxDuration)) duration = lastDuration;

    //Rejects any reading defined to be out of sensor capacity (>1000)
    //Sets the fault reading to the last known "successful" reading
    if (duration > maxDuration) duration = maxDuration;

    //Caps Reading output at defined maximum distance (~200)
    if ((duration - lastDuration) < ((-1) * (NoiseReject / 100) * lastDuration)) {
        distance = (lastDuration / 2) / speed_of_sound;         //Noise filter for low range drops
    }

    distance = (duration / 2) / speed_of_sound;
    lastDuration = duration;                                    //Stores "successful" reading for filter compensation
}

void SingleSonar() {
    SonarSensor(trigger1, echo1);
    Sonar = distance;
    RawSonar = unfiltered;
    delay(50);                                                  //Delay 50ms before next reading.
}


void onEventsCallback(WebsocketsEvent event, String data) {

    if (event == WebsocketsEvent::ConnectionOpened) {
        Serial.println("WebSocket connnection opened!");
    } else if (event == WebsocketsEvent::ConnectionClosed) {
        Serial.println("WebSocket connection closed!");
        Serial.println("Trying to reconnect to WebSocket ...");
        delay(10000);
        bool connected = client.connect(websockets_server_host, websockets_server_port, "/test");
    } else if (event == WebsocketsEvent::GotPing) {
        Serial.println("Got a Ping!");
    } else if (event == WebsocketsEvent::GotPong) {
        Serial.println("Got a Pong!");
    }
}