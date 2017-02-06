#include "FastLED.h"
#define NUM_LEDS 22

CRGB leds[2][NUM_LEDS];

#define PIN1 6
#define PIN2 8
void setPixel( int Pixel, byte red, byte green, byte blue, int strip = 0);
void setAll(byte red, byte green, byte blue, int strip = 0);

void setup()
{
  setupBlinkOnLED();
  FastLED.addLeds<WS2811, PIN1, GRB>(leds[0], NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.addLeds<WS2811, PIN2, GRB>(leds[1], NUM_LEDS).setCorrection( TypicalLEDStrip );
  Serial.begin(9600);
}

void loop() { 
  //loopBlinkOnLED();
 //LoadGear();
 //LoadFuel();
// setAll(0,0,0,0);
// setAll(0,0,0,1);
// delay(250);
// setAll(0,0xff,0,0);
 //setAll(0,0,0xff,0);
// showStrip();
// delay(250);
 //RGBLoop();
 //Aim();

  ReadCommands(); 
}

void LoadFuel() {
    RunningLights(0x00,0xff,0x00,50,4,0,0);
}

void LoadGear() {
    RunningLights(0xff,0xf0,0, 70,11,0,0);
}

void Aim(){
  for(int q = 20; q > 0;q-=2){
    CylonBounce(0xff, 0, 0, 4, q, 50);
  }
   for(int q = 0; q < 15; q++){
//  setAll(0x24,0x24,0x24,0);
    setAll(0xFF, 0xFF, 0xFF, 0);
    delay(15);
    setAll(0,0,0,0);
    delay(15);
   }
}

void CylonBounce(byte red, byte green, byte blue, int EyeSize, int SpeedDelay, int ReturnDelay){

  for(int i = 0; i < NUM_LEDS-EyeSize-2; i++) {
    setAll(0,0,0);
    setPixel(i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setPixel(i+j, red, green, blue); 
    }
    setPixel(i+EyeSize+1, red/10, green/10, blue/10);
    showStrip();
    delay(SpeedDelay);
  }

  delay(ReturnDelay);

  for(int i = NUM_LEDS-EyeSize-2; i > 0; i--) {
    setAll(0,0,0);
    setPixel(i, red/10, green/10, blue/10);
    for(int j = 1; j <= EyeSize; j++) {
      setPixel(i+j, red, green, blue); 
    }
    setPixel(i+EyeSize+1, red/10, green/10, blue/10);
    showStrip();
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
    
    showStrip();
    delay(SpeedDelay);
  }

  delay(ReturnDelay);
}



void RunningLights(byte red, byte green, byte blue, int WaveDelay, float ballSize, float verticalOffSet, int strip) {
  
  for(int Position=0; Position<ballSize*2; Position++)
  {
      setAll(0,0,0,strip);    
      for(int i=0; i<NUM_LEDS; i++) {
        float value = sin((i+Position)*PI/ballSize) + verticalOffSet;
        if (value>0) {
          setPixel(i, dim8_raw(dim8_raw(value*red)), dim8_raw(dim8_raw(value*green)), dim8_raw(dim8_raw(value*blue)), strip);
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
      showStrip();
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

void setAll(byte red, byte green, byte blue, int strip) {
  for(int i = 0; i < NUM_LEDS; i++ ) {
    setPixel(i, red, green, blue, strip); 
  }
  showStrip();
}

const int ledPin =  13; 
int ledState = LOW;
long previousMillis = 0;
long interval = 10;

void setupBlinkOnLED() {
  pinMode(ledPin, OUTPUT);      
}

void loopBlinkOnLED()
{

  long currentMillis = millis();
 
  if(currentMillis - previousMillis > interval) {
    // save the last time you blinked the LED 
    previousMillis = currentMillis;   

    if (ledState == LOW)
      ledState = HIGH;
    else
      ledState = LOW;

    digitalWrite(ledPin, ledState);
  }
}

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
    //strcpy( command, "" );
  }
  else if (strcmp(command, "LoadGear") == 0) {
    LoadGear();
  }
}

