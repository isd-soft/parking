/**
* Master Arduino firmware
*/


#include <ArduinoWebsockets.h>
#include <WiFi.h>

#define ARRAY_SIZE(array) ((sizeof(array))/(sizeof(array[0])))  // used to calculate de length of an array

#define MASTER_EN   5                                           // connected to RS485 Enable pin

int slave_ids[] = {1, 2, 3};                                    // add new id when adding new slaves
int sonar_ids[] = {1, 2, 3};                                    // add new id when will be added more sensors

int active_sonars[9];                                           // active sensors with which will work master

char slave_id;
char sensor_id;
String status_of_lot;
String last_known_status[9];                                    // statuses of each sensor, index of the status must be the same as the id of sensor
                                                                // change size when will be added more sensors

//Wifi
using namespace websockets;
WebsocketsClient client;

const char *ssid = "Inther";                                    // Enter SSID
const char *password = "inth3rmoldova";                         // Enter Password
const char *websockets_server_host = "128.199.34.112";          // Enter server address 
const uint16_t websockets_server_port = 8080;                   // Enter server port

//WebSocket
//sample message for WebSocket
char *msg = "{\"mBody\":\"Arduino data\", \"id\":\"";

//security
char *security_token = "4a0a8679643673d083b23f52c21f27cac2b03fa2"; //some security token to verify connection ({SHA1}"arduino")

// timer to restart program every 60 min
long timer;

void(* resetFunc) (void) = 0;                                   // reseting the arduino board

void setup() {
  timer = millis();                                   

  pinMode(MASTER_EN , OUTPUT);                                  // Declare Enable pin as output
  Serial.begin(9600);                                           // set serial communication baudrate 
  digitalWrite(MASTER_EN , LOW);                                // Make Enable pin low
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

    // check which sensors are working
    getActiveSonars();

}

void loop() {
  String answer;
  status_of_lot = ""; 
    for (int i = 0; i < ARRAY_SIZE(active_sonars); i++) {
      if (active_sonars[i]) {
        digitalWrite(MASTER_EN , HIGH);                            // Make Enable pin high to send Data
        delay(50);                                                 // required minimum delay of 5ms
        Serial.println(active_sonars[i]);                          // Send slave and sensor id
        Serial.flush();                                            // wait for transmission of data
        digitalWrite(MASTER_EN , LOW);                             // Receiving mode ON
      
        delay(500);
        while(Serial.available()) {
          answer = Serial.readString();
          answer.trim();
        }
      
        slave_id = answer.charAt(0);
        sensor_id = answer.charAt(1);
        status_of_lot = answer.substring(2);
        
    
        if (status_of_lot != "") {
          if (status_of_lot != last_known_status[i]) {
            client.send(msg +/* String(slave_id) + */String(sensor_id) + String("\", \"status\":\"") + status_of_lot + String("\", \"token\":\"") + security_token + String("\"}"));
          }
          last_known_status[i] = status_of_lot;
        }
      }
    }
  


  if (client.available())
    {
        client.poll();
        client.ping();

    }

   /// reset arduino after 1 hour    
  if ((millis() - timer) > 3600000) {
    resetFunc();
  }
}




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

int getSize(int arr[]) {
  int size = sizeof(arr)/sizeof(arr[0]);
  return size;
}

void getActiveSonars() {
  int index = 0;
  int last_sonar;
  String answer;
  for (int j = 0; j < ARRAY_SIZE(slave_ids); j++) {
    for (int i = 0; i < ARRAY_SIZE(sonar_ids); i++) {
      if (sonar_ids[i]) {
        digitalWrite(MASTER_EN , HIGH);                                // Make Enable pin high to send Data
        delay(50);                                                     // required minimum delay of 5ms
        Serial.print(slave_ids[j]); Serial.println(sonar_ids[i]);      // Send slave and sensor id
        Serial.flush();                                                // wait for transmission of data
        digitalWrite(MASTER_EN , LOW);                                 // Receiving mode ON
      
        delay(500);
        while(Serial.available()) {
          answer = Serial.readString();
          answer.trim();
        }
      
        slave_id = answer.charAt(0);
        sensor_id = answer.charAt(1);
        status_of_lot = answer.substring(2);
        
    
        if (status_of_lot != "") {
          int sonar = (slave_id - '0') * 10 + (sensor_id - '0');
          if (sonar != last_sonar) {
            active_sonars[index] = sonar;
          }
          index++;
          last_sonar = sonar;
        }
      }
    }
  }
}
