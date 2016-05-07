#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>
#define  relay1 31
#define  relay2 32
#define  relay3 33
#define  relay4 34
#define  relay5 35
#define  relay6 36
#define  relay7 37
#define  relay8 38


AndroidAccessory acc("Manufacturer",
                     "Model",
                     "Description",
                     "1.0",
                     "http://yoursite.com",
                     "0000000012345678");
void setup()
{
  // set communiation speed
  Serial.begin(115200);
  pinMode(relay1, OUTPUT);
  pinMode(relay2, OUTPUT);
  pinMode(relay3, OUTPUT);
  pinMode(relay4, OUTPUT);
  pinMode(relay5, OUTPUT);
  pinMode(relay6, OUTPUT);
  pinMode(relay7, OUTPUT);
  pinMode(relay8, OUTPUT);

  digitalWrite(relay1 , HIGH); // turn off light
    digitalWrite(relay2 , HIGH); // turn off light
    digitalWrite(relay3 , HIGH); // turn off light
    digitalWrite(relay4 , HIGH); // turn off light
    digitalWrite(relay5 , HIGH); // turn off light
    digitalWrite(relay6 , HIGH); // turn off light
    digitalWrite(relay7 , HIGH); // turn off light
    digitalWrite(relay8 , HIGH); // turn off light


  
  acc.powerOn();
}

void loop()
{
  byte msg[0];
  if (acc.isConnected()) {
    int len = acc.read(msg, sizeof(msg), 1); // read data into msg variable

    if (len > 0) {
      Serial.println(msg[0]);
      switch (msg[0]) {
        case 10 :
          digitalWrite(relay1, LOW); // turn off light
          break;
        case 11 :
          digitalWrite(relay1, HIGH); // turn on light
          break;
        case 20 :
          digitalWrite(relay2, LOW); // turn off light
          break;
        case 21 :
          digitalWrite(relay2, HIGH); // turn on light
          break;
        case 30 :
          digitalWrite(relay3, LOW); // turn off light
          break;
        case 31 :
          digitalWrite(relay3, HIGH); // turn on light
          break;
        case 40 :
          digitalWrite(relay4, LOW); // turn off light
          break;
        case 41 :
          digitalWrite(relay4, HIGH); // turn on light
          break;
        case 50 :
          digitalWrite(relay5, LOW); // turn off light
          break;
        case 51 :
          digitalWrite(relay5, HIGH); // turn on light
          break;
        case 60 :
          digitalWrite(relay6, LOW); // turn off light
          break;
        case 61 :
          digitalWrite(relay6, HIGH); // turn on light
          break;
        case 70 :
          digitalWrite(relay7, LOW); // turn off light
          break;
        case 71 :
          digitalWrite(relay7, HIGH); // turn on light
          break;
        case 80 :
          digitalWrite(relay8, LOW); // turn off light
          break;
        case 81 :
          digitalWrite(relay8, HIGH); // turn on light
          break;
      }
    }
  }
  else {
//    digitalWrite(relay1 , HIGH); // turn off light
//    digitalWrite(relay2 , HIGH); // turn off light
//    digitalWrite(relay3 , HIGH); // turn off light
//    digitalWrite(relay4 , HIGH); // turn off light
//    digitalWrite(relay5 , LOW); // turn off light
//    digitalWrite(relay6 , LOW); // turn off light
//    digitalWrite(relay7 , LOW); // turn off light
//    digitalWrite(relay8 , LOW); // turn off light
  }
}

