#include "FastLED.h"
#define NUM_LEDS 18
#define one 1

CRGB leds[4][NUM_LEDS];
CRGB sensorled[1][one];

#define PIN1 9
#define PIN2 8
#define PIN3 7
#define PIN4 6

const int ledPin =  13; 
int ledState = HIGH;

void setPixel( int Pixel, byte red, byte green, byte blue, int strip = 7);
void setAll(byte red, byte green, byte blue, int strip = 0);
void setAllS(int i, byte red, byte green, byte blue, int wrud = 000000);

void setup()
{
  pinMode(ledPin, OUTPUT);
  FastLED.addLeds<WS2811, PIN1, GRB>(leds[0], NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.addLeds<WS2811, PIN2, GRB>(leds[1], NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.addLeds<WS2811, PIN3, GRB>(leds[2], NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.addLeds<WS2811, PIN4, GRB>(leds[3], NUM_LEDS).setCorrection( TypicalLEDStrip );
  Serial.begin(9600);
}

void loop() {
 //digitalWrite(13, HIGH);
 //LoadGear();
 //LoadFuel();
 //RGBLoop();
 //Aim();
 //Default();
 //Seizure();
 //EndGame();
 ReadCommands(); 
}

void Default() {
    NewKITT(0xff, 0, 0, 4, 20, 50);
}

void LoadFuel() {
    RunningLights(0x00,0xff,0x00,50,4,0, 2);
}

void LoadGear() {
    RunningLights(0xff,0xf0,0, 70,11,0, 3);
}

void Aim(){
  for(int q = 20; q > 0;q-=2){
    CylonBounce(0xff, 0, 0, 4, q, 50);
  }
}

void Seizure() {
   for(int q = 0; q < 15; q++){
//  setAll(0x24,0x24,0x24,0);
    setAllSP(0xFF, 0xFF, 0xFF);
    delay(15);
    setAll(0,0,0,0);
    delay(15);
   }
}

void OnTarget() {
   Seizure();
}

void EndGame() {
  for(int q = 0; q < 15; q++){
    setAllSP(0,0,0);
    delay(15);
    setAll(0xFF, 0, 0, 0);
    delay(15);
  }
}


void CylonBounce(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay){

  for(int i = 0; i < NUM_LEDS-EyeSize-2; i++) {
    setAllSP(0,0,0);
    setAllS(i, red/10, green/10, blue/10, 1);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(i+j, red, green, blue, 1); 
    }
    setAllS(i+EyeSize+1, red/10, green/10, blue/10, 1);
    if (showStrip())
      return;
    delay(SpeedDelay);
  }

  delay(ReturnDelay);

  for(int i = NUM_LEDS-EyeSize-2; i > 0; i--) {
    setAllSP(0,0,0);
    setAllS(i, red/10, green/10, blue/10, 1);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(i+j, red, green, blue, 1); 
    }
    setAllS(i+EyeSize+1, red/10, green/10, blue/10, 1);
    if (showStrip())
      return;
    delay(SpeedDelay);
  }
  
  delay(ReturnDelay);
}

void DoubleChase(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay){

  for(int pos1 = 0; pos1 < NUM_LEDS; pos1++) {
    setAll(0,0,0);

    int pos2 = (pos1 + NUM_LEDS/2) % NUM_LEDS;

    for (int j=0; j<NUM_LEDS; j++) {
      if (abs(j-pos1)<EyeSize)
        setPixel(j, red, green, blue); 
      else if (abs(j-pos2)<EyeSize)
        setPixel(j, red, green, blue);
      //else if (abs(j-pos1)<EyeSize*2)
                  
    }
    
    if (showStrip())
      return;
    delay(SpeedDelay);
  }
  delay(ReturnDelay);
}



void RunningLights(byte red, byte green, byte blue, int WaveDelay, float ballSize, float verticalOffSet, int wrud) {
  
  for(int Position=0; Position<ballSize*2; Position++)
  {
      setAllSP(0,0,0);    
      for(int i=0; i<NUM_LEDS; i++) {
        float value = sin((i+Position)*PI/ballSize) + verticalOffSet;
        if (value>0) {
          setAllS(i, dim8_raw(dim8_raw(value*red)), dim8_raw(dim8_raw(value*green)), dim8_raw(dim8_raw(value*blue)), wrud);
        }
      }
      
      if (showStrip())
        return;
      delay(WaveDelay);
  }
}

void RGBLoop(){
  for(int j = 0; j < 3; j++ ) { 
    // Fade IN
    for(int k = 0; k < 256; k++) { 
      switch(j) { 
        case 0: setAll(k,0,0); break;
        case 1: setAll(0,k,0); break;
        case 2: setAll(0,0,k); break;
      }
      if (showStrip())
        return;

      delay(3);
    }
    // Fade OUT
    for(int k = 255; k >= 0; k--) { 
      switch(j) { 
        case 0: setAll(k,0,0); break;
        case 1: setAll(0,k,0); break;
      }
    }
  }
}

void NewKITT(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay){
  RightToLeft(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  LeftToRight(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  OutsideToCenter(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  CenterToOutside(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  LeftToRight(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  RightToLeft(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  OutsideToCenter(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
  CenterToOutside(red, green, blue, EyeSize, SpeedDelay, ReturnDelay);
}

void CenterToOutside(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay) {
  for(int i =((NUM_LEDS-EyeSize)/2); i>=0; i--) {
    setAllSP(0,0,0);
    
    setAllS(i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(i+j, red, green, blue); 
    }
    setAllS(i+EyeSize+1, red/10, green/10, blue/10);
    
    setAllS(NUM_LEDS-i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(NUM_LEDS-i-j, red, green, blue); 
    }
    setAllS(NUM_LEDS-i-EyeSize-1, red/10, green/10, blue/10);
    
    if (showStrip())
      return;
      
    delay(SpeedDelay);
  }
  delay(ReturnDelay);
}

void OutsideToCenter(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay) {
  for(int i = 0; i<=((NUM_LEDS-EyeSize)/2); i++) {
    setAllSP(0,0,0);
    
    setAllS(i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(i+j, red, green, blue); 
    }
    setAllS(i+EyeSize+1, red/10, green/10, blue/10);
    
    setAllS(NUM_LEDS-i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(NUM_LEDS-i-j, red, green, blue); 
    }
    setAllS(NUM_LEDS-i-EyeSize-1, red/10, green/10, blue/10);
    
    if (showStrip())
      return;
    
    delay(SpeedDelay);
  }
  delay(ReturnDelay);
}

void LeftToRight(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay) {
  for(int i = 0; i < NUM_LEDS-EyeSize-2; i++) {
    setAllSP(0,0,0);
    setAllS(i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(i+j, red, green, blue); 
    }
    setAllS(i+EyeSize+1, red/10, green/10, blue/10);
    if (showStrip())
      return;
      
    delay(SpeedDelay);
  }
  delay(ReturnDelay);
}

void RightToLeft(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay) {
  for(int i = NUM_LEDS-EyeSize-2; i > 0; i--) {
    setAllSP(0,0,0);
    setAllS(i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setAllS(i+j, red, green, blue); 
    }
    setAllS(i+EyeSize+1, red/10, green/10, blue/10);
    if (showStrip())
      return;
      
    delay(SpeedDelay);
  }
  delay(ReturnDelay);
}

bool showStrip() {
 #ifdef ADAFRUIT_NEOPIXEL_H 
   // NeoPixel
   strip.show();
 #else
   // FastLED
   FastLED.show();
 #endif
 return Serial.available() > 0;
}

void setPixel( int Pixel, byte red, byte green, byte blue, int strip) {
 #ifdef ADAFRUIT_NEOPIXEL_H 
   // NeoPixel
   strip.setPixelColor(Pixel, strip.Color(red, green, blue));
 #endif
 #ifndef ADAFRUIT_NEOPIXEL_H 
   // FastLED
   leds[strip][Pixel].r = red;
   leds[strip][Pixel].g = green;
   leds[strip][Pixel].b = blue;
 #endif
}

void setAllSP(byte red, byte green, byte blue) {
  for(int i = 0; i < NUM_LEDS; i++ ) {
    setPixel(i, red, green, blue, 0);
    setPixel(i, red, green, blue, 1);
    setPixel(i, red, green, blue, 2);
    setPixel(i, red, green, blue, 3);
     
  }
    if (showStrip())
      return;
}

void setAll(byte red, byte green, byte blue, int strip) {
  for(int i = 0; i < NUM_LEDS; i++ ) {
    setPixel(i, red, green, blue, strip); 
  }
    if (showStrip())
      return;
}

void setAllS(int i, byte red, byte green, byte blue, int wrud) {
    if (wrud == 1){
      setPixel(i, red, green, blue, 0);
      setPixel(i, red, green, blue, 1);
      setPixel(i, red, green, blue, 2); 
      setPixel(0, 0xff, 0xff, 0xff, 3);
    }else if (wrud == 2){
      setPixel(i, red, green, blue, 0); 
      for(int j = NUM_LEDS; j > NUM_LEDS/2; j--) {       
        setPixel(j, 0x00,0xff,0x00, 1);
      }
      setPixel(i, red, green, blue, 2); 
      setPixel(0, 0xff, 0xff, 0xff, 3);      
    }else if (wrud == 3){
      setPixel(i, red, green, blue, 0); 
      for(int j = 0; j < NUM_LEDS/2; j++) {       
        setPixel(j, 0xff,0xf0,0, 1);
      }
      setPixel(i, red, green, blue, 2); 
      setPixel(0, 0xff, 0xff, 0xff, 3);    
    }else{
      setPixel(i, red, green, blue, 0); 
      setPixel(i, red, green, blue, 1);
      setPixel(i, red, green, blue, 2); 
      setPixel(0, red, green, blue, 3);
    }
    

  
}
void setSensorLed(byte red, byte green, byte blue) {
   int Pixel = 0;
   sensorled[0][0].r = red;
   sensorled[0][0].g = green;
   sensorled[0][0].b = blue;
}





//void loopBlinkOnLED()
//{
//
//  long currentMillis = millis();
// 
//  if(currentMillis - previousMillis > interval) {
//    // save the last time you blinked the LED 
//    previousMillis = currentMillis;   
//
//    if (ledState == LOW)
//      ledState = HIGH;
//    else
//      ledState = LOW;
//
//    digitalWrite(ledPin, ledState);
//  }
//}

void ReadCommands() {
  static char command[40];
  int pos = 0;
  char letter = '\0';

  if (Serial.available() > 0) {
    do {
      if (Serial.available() > 0) {
        letter = Serial.read();
        if (letter != ' ' && letter != '\n') {
          command[pos++] = letter;
        }
      }
    } while (letter != ' ' && letter != '\n');
    Serial.read();
    command[pos++] = '\0';  // add null terminator
  }

  if (strcmp(command, "LoadFuel") == 0) {
    LoadFuel();
  }
  else if (strcmp(command, "LoadGear") == 0) {
    LoadGear();
  }
  else if (strcmp(command, "Aim") == 0) {
    Aim();
  }
  else if (strcmp(command, "OnTarget") == 0) {
    OnTarget();
    strcpy( command, "" );
  }
  else if (strcmp(command, "Default") == 0) {
    Default();
  }  
  else if (strcmp(command, "EndGame") == 0) {
    EndGame();
  }
}

