/**
 * Slave Arduino firmware
 * This firmware is ment to work with 3 ultrasonic sensors SR-04
 * by using NewPing library.
 * By default this firware works with two sensors
 * Uncomment code for third sensor if you want to use 3 of them, or comment the second if you are going to use just one.
 */

#include <NewPing.h>

//Sonar 1 details
#define TRIGGER_PIN_1  2                                          // Arduino pin tied to trigger pin on the ultrasonic sensor 1.
#define ECHO_PIN_1     5                                          // Arduino pin tied to echo pin on the ultrasonic sensor 1.

//Sonar 2 details
#define TRIGGER_PIN_2  3                                          // Arduino pin tied to trigger pin on the ultrasonic sensor 2.
#define ECHO_PIN_2     6                                          // Arduino pin tied to echo pin on the ultrasonic sensor 2.
                                                                                                                                             // <-- Uncomment for using third sensor
//Sonar 3 details
// #define TRIGGER_PIN_3  4                                          // Arduino pin tied to trigger pin on the ultrasonic sensor 3.
// #define ECHO_PIN_3     7                                          // Arduino pin tied to echo pin on the ultrasonic sensor 3.
                                                                                                                                             // --> Uncomment for using third sensor

#define MAX_DISTANCE 500                                          // Maximum distance we want to ping for (in centimeters). 
                                                                  // Maximum sensor distance is rated at 400-500cm.

#define SLAVE_EN  8
#define SLAVE_ID  1

#define SONAR_1_ID 1
#define SONAR_2_ID 2
                                                                                                                                             // <-- Uncomment for using third sensor
// #define SONAR_3_ID 3
                                                                                                                                             // --> Uncomment for using third sensor

//Sonar variables
unsigned int filteredSonarDistance_1;                             // real time distance from sonar 1
unsigned int lastSonarDistance_1;                                 // last known distance of sonar 1, used to prevent double values

unsigned int filteredSonarDistance_2;                             // real time distance from sonar 2
unsigned int lastSonarDistance_2;                                 // last known distance of sonar 2, used to prevent double values

                                                                                                                                             // <-- Uncomment for using third sensor
// unsigned int filteredSonarDistance_3;                             // real time distance from sonar 3
// unsigned int lastSonarDistance_3;                                 // last known distance of sonar 3, used to prevent double values
                                                                                                                                             // --> Uncomment for using third sensor

long targetDistance = 100;                                       // target distance value when the status should be trigerred, common for all sensors (can be added for each sensor)

//parking lot
boolean isLotFreeSonar_1 = false;                                // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized_1 = false;                              // initialized flag for sonar

boolean isLotFreeSonar_2 = false;                                // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized_2 = false;                              // initialized flag for sonar


                                                                                                                                             // <-- Uncomment for using third sensor
// boolean isLotFreeSonar_3 = false;                                // Sonar's boolean variable to define if the status of parking lot was changed or not
// boolean sonarInitialized_3 = false;                              // initialized flag for sonar
                                                                                                                                             // --> Uncomment for using third sensor
//parking lot states
String status_free = "FREE";
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";
String actual_status_1;
String actual_status_2;
                                                                                                                                             // <-- Uncomment for using third sensor
// String actual_status_3;
                                                                                                                                             // --> Uncomment for using third sensor

NewPing sonar_1(TRIGGER_PIN_1, ECHO_PIN_1, MAX_DISTANCE);        // NewPing setup of pins and maximum distance for first sensor
NewPing sonar_2(TRIGGER_PIN_2, ECHO_PIN_2, MAX_DISTANCE);        // NewPing setup of pins and maximum distance for second sensor

                                                                                                                                             // <-- Uncomment for using third sensor
// NewPing sonar_3(TRIGGER_PIN_3, ECHO_PIN_3, MAX_DISTANCE);     // NewPing setup of pins and maximum distance for third sensor
                                                                                                                                             // --> Uncomment for using third sensor

void setup() {
  pinMode(SLAVE_EN , OUTPUT);                                    // Declare Enable pin as output
  Serial.begin(9600);                                            // set serial communication baudrate 

  //Sensor Connections
  pinMode(TRIGGER_PIN_1, OUTPUT);
  pinMode(ECHO_PIN_1, INPUT);

  pinMode(TRIGGER_PIN_2, OUTPUT);
  pinMode(ECHO_PIN_2, INPUT);

                                                                                                                                             // <-- Uncomment for using third sensor
  // pinMode(TRIGGER_PIN_3, OUTPUT);
  // pinMode(ECHO_PIN_3, INPUT);
                                                                                                                                             // --> Uncomment for using third sensor
}

void loop() {
   
  digitalWrite(SLAVE_EN , LOW);                                  // Make Enable pin low
                                                                 // Receiving mode ON
  delay(5);
  
  while(Serial.available())                                      // If serial data is available then enter into while loop
  {
    int request = Serial.parseInt();
    if (request != 0) {
      Serial.print("request: ");Serial.println(request);
      if ((request / 10U) % 10 == SLAVE_ID) {
        if ((request / 1U) % 10 == SONAR_1_ID) {
          filteredSonarDistance_1 = sonar_1.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 1
          if (!sonarInitialized_1 || ((filteredSonarDistance_1 >= targetDistance || filteredSonarDistance_1 == 0)&& !isLotFreeSonar_1) 
                                  || (filteredSonarDistance_1 < targetDistance && isLotFreeSonar_1))
          {
            sonarInitialized_1 = true;
            isLotFreeSonar_1 = (filteredSonarDistance_1 >= targetDistance || filteredSonarDistance_1 == 0) ? true : false;
            actual_status_1 = isLotFreeSonar_1 ? status_free : status_occupied;
          } 
          
          digitalWrite(SLAVE_EN , HIGH);                         // Make Enable pin high to send Data
          delay(5);                                              // required minimum delay of 5ms
          Serial.println(String(SLAVE_ID)+ String(SONAR_1_ID) + String(actual_status_1));
          Serial.flush();
        } 
        
        if ((request / 1U) % 10 == SONAR_2_ID) {
          filteredSonarDistance_2 = sonar_2.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 2
          if (!sonarInitialized_2 || ((filteredSonarDistance_2 >= targetDistance || filteredSonarDistance_2 == 0)&& !isLotFreeSonar_2) 
                                  || (filteredSonarDistance_2 < targetDistance && isLotFreeSonar_2))
          {
            sonarInitialized_2 = true;
            isLotFreeSonar_2 = (filteredSonarDistance_2 >= targetDistance || filteredSonarDistance_2 == 0) ? true : false;
            actual_status_2 = isLotFreeSonar_2 ? status_free : status_occupied;
          } 
          
          digitalWrite(SLAVE_EN , HIGH);                          // Make Enable pin high to send Data
          delay(5);                                               // required minimum delay of 5ms
          Serial.println(String(SLAVE_ID)+ String(SONAR_2_ID) + String(actual_status_2));
          Serial.flush();
        }
        

                                                                                                                                             // <-- Uncomment for using third sensor
   /*     if ((request / 1U) % 10 == SONAR_3_ID) {
          filteredSonarDistance_3 = sonar_3.ping_median(10) / US_ROUNDTRIP_CM; // get real time distance in cm from sonar 3
          if (!sonarInitialized_3 || ((filteredSonarDistance_3 >= targetDistance || filteredSonarDistance_3 == 0)&& !isLotFreeSonar_3) 
                                  || (filteredSonarDistance_3 < targetDistance && isLotFreeSonar_3))
          {
            sonarInitialized_3 = true;
            isLotFreeSonar_3 = (filteredSonarDistance_3 >= targetDistance || filteredSonarDistance_3 == 0) ? true : false;
            actual_status_3 = isLotFreeSonar_3 ? status_free : status_occupied;
          } 
          
          digitalWrite(SLAVE_EN , HIGH);                            // Make Enable pin high to send Data
          delay(5);                                                // required minimum delay of 5ms
          Serial.println(String(SLAVE_ID)+ String(SONAR_3_ID) + String(actual_status_3));
          Serial.flush();
        } */
                                                                                                                                             // --> Uncomment for using third sensor		
      }
    }
  }
}
