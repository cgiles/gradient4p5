import gradient4p5.gradient.*;
import gradient4p5.gradient.colorgradient.*;

Gradient myGradient0, myGradient1;
void setup() {
  size(400, 200);

  noStroke();

  myGradient0=new Gradient(this);
  myGradient1=new Gradient(this);
  // generate a bw gradient
  myGradient0.initBW();
  // generate  a 11 point hsb gradient
  myGradient1.initHSB();
}
void draw() {
  for (int i=0; i<width; i+=2) {
    float scaledPos=map(i, 0, width, 0.0, 1.0);
    fill(myGradient0.getGradientShade(scaledPos));
    rect(i, 0, 2, height/2);
    fill(myGradient1.getGradientShade(scaledPos));
    rect(i, height/2, 2, height/2);
  }
}

void mouseReleased() {
  String rgbS;
  color aColor;
  float scaledPos=map(mouseX, 0, width, 0.0, 1.0);
  if (mouseY<height/2) {
    println("true");
    aColor=myGradient0.getGradientShade(scaledPos);
    
  } else {
    aColor=myGradient1.getGradientShade(scaledPos);
    
  }
  rgbS=str(scaledPos)+" R :"+str(red(aColor))+" G :"+str(green(aColor))+" B :"+str(blue(aColor));
  println(rgbS);
}