import gradient4p5.gradient.*;

PImage map;
Gradient myGradient;

void setup() {
  size(800, 800);
  frameRate(30);
  map=loadImage("island-height.png");
  myGradient=new Gradient(this);
  myGradient.addShadeToGradient(0.0,color(0,0,25));
  myGradient.addShadeToGradient(0.3,color(0,0,255));
  myGradient.addShadeToGradient(0.31,color(255,255,0));
  myGradient.addShadeToGradient(0.32,color(255,255,0));
  myGradient.addShadeToGradient(0.33,color(0,25,0));
  myGradient.addShadeToGradient(0.5,color(0,255,0));
  myGradient.addShadeToGradient(1.0,color(255,255,255));
  map.loadPixels();
  for(color pix:map.pixels){
   
  float val=red(pix);
  val=map(val,0,255,0.0,1.0);
   println(val);
  pix=myGradient.getGradientShade(val);
  }
  map.updatePixels();
}
void draw() {
  image(map, 0, 0);
}