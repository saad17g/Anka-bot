#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

const char* ssid = "ekip"; //P2I6tmp
const char* password = "qlfndapnlol17";
WiFiUDP Udp;

void setup() {

  Serial.begin(115200);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500); Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println(WiFi.localIP());
  Udp.begin(5000);
  pinMode(D6, OUTPUT);
  pinMode(D5, OUTPUT);
  //pinMode(D1, OUTPUT);
}

void loop() {
  char incomingPacket[255];
  int packetSize = Udp.parsePacket();
  if (packetSize) {
    int len = Udp.read(incomingPacket, 255);
    String msg = String(incomingPacket).substring(0,len);
    msg.trim();
    Serial.print("UDP packet contents: ");
    Serial.println(msg);
    if (msg == "aller devant"){
      Serial.println("devant");
      int len = Udp.read(incomingPacket, 255);
      /* code qui permet aux deux moteurs de démarrer */
      digitalWrite(D6, 0);
      digitalWrite(D5, 1);
      int i = 1020;
    
      // pwm de 0 a 1023
      analogWrite(D1, i);
      analogWrite(D7, 950);
        
      Udp.endPacket();
    } else if(msg == "retourner en arrière"){
      Serial.println("arrière");
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      digitalWrite(D5, 0);
      int i = 1020;
    
      // pwm de 0 a 1023
      analogWrite(D1, i);
      analogWrite(D7, 950);
        
      Udp.endPacket();
    }else if(msg == "tourner à droite"){
      Serial.println("droite");
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      digitalWrite(D5, 1);
      int i = 1023;
    
      // pwm de 0 a 1023
      analogWrite(D1, i);
      analogWrite(D7, i);
        
      Udp.endPacket();
    }else if(msg == "tourner à gauche"){
      Serial.println("droite");
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 0);
      digitalWrite(D5, 0);
      int i = 1023;
    
      // pwm de 0 a 1023
      analogWrite(D1, i);
      analogWrite(D7, i);
        
      Udp.endPacket();
    } else if(msg == "stop"){
      Serial.println("stop");
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      digitalWrite(D5, 1);
      int i = 1023;
    
      // pwm de 0 a 1023
      analogWrite(D1, 0);
      analogWrite(D7, 0);
        
      Udp.endPacket();
      
    }else if(msg =="moteur1 vitesse1"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      int i = 400;
      analogWrite(D1, i);
    } else if(msg =="moteur1 vitesse2"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      int i = 700;
      analogWrite(D1, i);
    }else if(msg =="moteur1 vitesse3"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      int i = 900;
      analogWrite(D1, i);
    }else if(msg =="moteur1 vitesse4"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D6, 1);
      int i = 1023;
      analogWrite(D1, i);
    } else if(msg =="moteur2 vitesse1"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D5, 0);
      int i = 400;
      analogWrite(D7, i);
    } else if(msg =="moteur2 vitesse2"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D5, 0);
      int i = 700;
      analogWrite(D7, i);
    }else if(msg =="moteur2 vitesse3"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D5, 0);
      int i = 900;
      analogWrite(D7, i);
    }else if(msg =="moteur2 vitesse4"){
      int len = Udp.read(incomingPacket, 255);
      digitalWrite(D5, 0);
      int i = 1023;
      analogWrite(D7, i);
    }
    
  }
}
