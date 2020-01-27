#include <NewPing.h>

//Sonar 1 details
#define TRIGGER_PIN_1  2  // Arduino pin tied to trigger pin on the ultrasonic sensor 1.
#define ECHO_PIN_1     5  // Arduino pin tied to echo pin on the ultrasonic sensor 1.

//Sonar 1 details
#define TRIGGER_PIN_2  3  // Arduino pin tied to trigger pin on the ultrasonic sensor 2.
#define ECHO_PIN_2     6  // Arduino pin tied to echo pin on the ultrasonic sensor 2.

#define MAX_DISTANCE 500 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

#define SLAVE_EN  8
#define SLAVE_ID  1

#define SONAR_1_ID 1
#define SONAR_2_ID 2

//Sonar variables
unsigned int filteredSonarDistance_1; // real time distance from sonar 1
unsigned int lastSonarDistance_1; // last known distance of sonar 1, used to prevent double values

unsigned int filteredSonarDistance_2; // real time distance from sonar 2
unsigned int lastSonarDistance_2; // last known distance of sonar 2, used to prevent double values

long targetDistance = 100; // target distance value when the status should be trigerred

//parking lot
boolean isLotFreeSonar_1 = false;   // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized_1 = false; // initialized flag for sonar

boolean isLotFreeSonar_2 = false;   // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized_2 = false; // initialized flag for sonar

//parking lot states
String status_free = "FREE";
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";
String actual_status_1;
String actual_status_2;

NewPing sonar_1(TRIGGER_PIN_1, ECHO_PIN_1, MAX_DISTANCE); // NewPing setup of pins and maximum distance.
NewPing sonar_2(TRIGGER_PIN_2, ECHO_PIN_2, MAX_DISTANCE); // NewPing setup of pins and maximum distance.


void setup() {
  pinMode(SLAVE_EN , OUTPUT);                   // Declare Enable pin as output
  Serial.begin(9600);                           // set serial communication baudrate 
  digitalWrite(SLAVE_EN , LOW);                 // Make Enable pin low
                                                // Receiving mode ON 

                                                    //Sensor Connections
  pinMode(TRIGGER_PIN_1, OUTPUT);
  pinMode(ECHO_PIN_1, INPUT);

    pinMode(TRIGGER_PIN_2, OUTPUT);
  pinMode(ECHO_PIN_2, INPUT);
}

void loop() {
  filteredSonarDistance_1 = sonar_1.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 1
//  Serial.print("filtered_1: "); Serial.println(filteredSonarDistance_1);  

  if (filteredSonarDistance_1 != lastSonarDistance_1) { 
      if (!sonarInitialized_1 || ((filteredSonarDistance_1 >= targetDistance || filteredSonarDistance_1 == 0)&& !isLotFreeSonar_1) 
          || (filteredSonarDistance_1 < targetDistance && isLotFreeSonar_1))
      {
          sonarInitialized_1 = true;
          isLotFreeSonar_1 = (filteredSonarDistance_1 >= targetDistance || filteredSonarDistance_1 == 0) ? true : false;
          actual_status_1 = isLotFreeSonar_1 ? status_free : status_occupied;
          Serial.print(actual_status_1);

      } 
      lastSonarDistance_1 = filteredSonarDistance_1;
    }

      filteredSonarDistance_2 = sonar_2.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 1
//  Serial.print("filtered_2: "); Serial.println(filteredSonarDistance_2);  

  if (filteredSonarDistance_2 != lastSonarDistance_2) { 
      if (!sonarInitialized_2 || ((filteredSonarDistance_2 >= targetDistance || filteredSonarDistance_2 == 0)&& !isLotFreeSonar_2) 
          || (filteredSonarDistance_2 < targetDistance && isLotFreeSonar_2))
      {
          sonarInitialized_2 = true;
          isLotFreeSonar_2 = (filteredSonarDistance_2 >= targetDistance || filteredSonarDistance_2 == 0) ? true : false;
          actual_status_2 = isLotFreeSonar_2 ? status_free : status_occupied;
          Serial.print(actual_status_2);

      } 
      lastSonarDistance_2 = filteredSonarDistance_2;
    }
  
  while(Serial.available())                     // If serial data is available then enter into while loop
  {
    int request = Serial.parseInt();
    if (request != 0) {
      if ((request / 10U) % 10 == SLAVE_ID) {
        if ((request / 1U) % 10 == SONAR_1_ID) {
          digitalWrite(SLAVE_EN, HIGH);
          delay(500);
          Serial.print(String(SLAVE_ID)+ String(SONAR_1_ID) + String(actual_status_1));
          Serial.flush();
        } else if ((request / 1U) % 10 == SONAR_2_ID) {
          digitalWrite(SLAVE_EN, HIGH);
          delay(500);
          Serial.print(String(SLAVE_ID)+ String(SONAR_2_ID) + String(actual_status_2));
          Serial.flush();
        }
      }
    }
  }
  digitalWrite(SLAVE_EN, LOW);
}