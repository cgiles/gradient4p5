import gradient4p5.gradient.*;
import gradient4p5.gradient.colorgradient.*;
//create my two gradient object
Gradient myGradientBW, myGradientHSB;
void setup() {
  size(400, 200);

  noStroke();
  //init my both gradient
  myGradientBW=new Gradient(this);
  myGradientHSB=new Gradient(this);
  // generate a bw gradient
  myGradientBW.initBW();
  // generate  a 11 point hsb gradient
  myGradientHSB.initHSB();
  println("click on the gradients, and see the rgb value in the console");
}
void draw() {
  for (int i=0; i<width; i+=2) {
    float scaledPos=map(i, 0, width, 0.0, 1.0);
    fill(myGradientBW.getGradientShade(scaledPos));
    rect(i, 0, 2, height/2);
    fill(myGradientHSB.getGradientShade(scaledPos));
    rect(i, height/2, 2, height/2);
  }
}

void mouseReleased() {
  String rgbS;
  color aColor;
  float scaledPos=map(mouseX, 0, width, 0.0, 1.0);
  if (mouseY<height/2) {

    aColor=myGradientBW.getGradientShade(scaledPos);
  } else {
    aColor=myGradientHSB.getGradientShade(scaledPos);
  }
  rgbS="R :"+str(red(aColor))+" G :"+str(green(aColor))+" B :"+str(blue(aColor));
  println(rgbS);
}