#include <NewPing.h>

//Sonar 1 details
#define TRIGGER_PIN_1  2  // Arduino pin tied to trigger pin on the ultrasonic sensor 1.
#define ECHO_PIN_1     5  // Arduino pin tied to echo pin on the ultrasonic sensor 1.

#define MAX_DISTANCE 500 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.

#define SLAVE_EN  8
#define SLAVE_ID  '1'

//Sonar variables
unsigned int filteredSonarDistance_1; // real time distance from sonar 1
unsigned int lastSonarDistance_1; // last known distance of sonar 1, used to prevent double values

long targetDistance = 250; // target distance value when the status should be trigerred

//parking lot
int lotId;                        // ID of the parking lot
boolean isLotFreeSonar_1 = false;   // Sonar's boolean variable to define if the status of parking lot was changed or not
boolean sonarInitialized_1 = false; // initialized flag for sonar

//parking lot states
String status_free = "FREE";
String status_occupied = "OCCUPIED";
String status_unknown = "UNKNOWN";
String actual_status;

NewPing sonar_1(TRIGGER_PIN_1, ECHO_PIN_1, MAX_DISTANCE); // NewPing setup of pins and maximum distance.


void setup() {
  pinMode(SLAVE_EN , OUTPUT);                   // Declare Enable pin as output
  Serial.begin(9600);                           // set serial communication baudrate 
  digitalWrite(SLAVE_EN , LOW);                 // Make Enable pin low
                                                // Receiving mode ON 

                                                    //Sensor Connections
  pinMode(TRIGGER_PIN_1, OUTPUT);
  pinMode(ECHO_PIN_1, INPUT);
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
          actual_status = isLotFreeSonar_1 ? status_free : status_occupied;
          Serial.print(actual_status);

      } 
      lastSonarDistance_1 = filteredSonarDistance_1;
    }
  
  while(Serial.available())                     // If serial data is available then enter into while loop
  {
    if(Serial.read() == SLAVE_ID)               // if available data for first slave
    {

      digitalWrite(SLAVE_EN, HIGH);
      delay(1000);
      Serial.print(String(SLAVE_ID)+ " " + String(actual_status));
      Serial.flush();
    }
  }
  digitalWrite(SLAVE_EN, LOW);
}
