package edu.ung.phys;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.io.BufferedReader;

/**
 * @author naharrison
 */
public class MotionTracker extends PApplet {

  public static void main(String[] args) {
 	  PApplet.main("edu.ung.phys.MotionTracker");
  }


  public float markerX, markerY;
  public boolean isRunning;
  public ArrayList<Float> trailX, trailY;
  public ArrayList<Double> xdata, ydata;
  public PImage boogie;
  public float axisStart, axisEnd;
  public int nAxisDiv, deltat, elapsedFrames, frRate, pxPerFrame;
  
  
  public void settings() {
    size(1200, 640);
  }
  
  
  public void setup() {
    frRate = 20;
    frameRate(frRate);
    textSize(14);
    elapsedFrames = 0;
    pxPerFrame = 2;
    markerX = 0;
    markerY = height/2;
    isRunning = false;
    trailX = new ArrayList<>();
    trailY = new ArrayList<>();
    boogie = loadImage("boogie.png");
    axisStart = (float) 0.25;
    axisEnd = (float) 0.75;
    nAxisDiv = 10;
    deltat = (int) ((width/frRate)/pxPerFrame);
    BufferedReader dataReader = createReader("XYdata.txt");
    Txt2Data txt2d = new Txt2Data(dataReader);
    xdata = txt2d.x;
    ydata = txt2d.y;
  }
  
  
  public void draw() {
    background(200);

    makeButtons();
    makeCanvas();
    makeSystem();

    if(isRunning && markerX < width) {
      markerX += pxPerFrame;
      trailX.add(markerX);
      trailY.add(markerY);
      elapsedFrames++;
    }

  }


  private void makeButtons() {
    fill(0, 255, 0);
    rect(10, height-30, 20, 20);
    if(mouseX > 10 && mouseX < 30 && mouseY > height-30 && mouseY < height-10 && mousePressed) {
      delay(2000);
      isRunning = true;
    }
    fill(0, 0, 255);
    rect(40, height-30, 20, 20);
    if(mouseX > 40 && mouseX < 60 && mouseY > height-30 && mouseY < height-10 && mousePressed) {
      isRunning = false;
      markerX = 0;
      trailX.clear();
      trailY.clear();
      elapsedFrames = 0;
    }
  }


  private void makeCanvas() {
    pushMatrix();
    translate((float) (0.05*width), 20);
    scale((float) 0.9, (float) 0.7);
    fill(240);
    rect(0, 0, width, height);
    fill(255, 0, 0);
    ellipse(markerX, markerY, 10, 10);
    for(int k = 1; k < trailX.size(); k++) {
      fill(255, 0, 0);
      ellipse(trailX.get(k), trailY.get(k), 5, 5);
    }
    fill(0);
    for(int k = 0; k <= nAxisDiv; k++) {
      text(String.format("%d", k), -20, height - k*(height/nAxisDiv));
    }
    for(int k = 0; k <= deltat; k++) {
      text(String.format("%d", k), k*(width/deltat), height+20);
    }
    for(int k = 0; k < xdata.size()-1; k++) {
      line((float) (width*xdata.get(k)/deltat), (float) (height - height*ydata.get(k)/nAxisDiv), (float) (width*xdata.get(k+1)/deltat), (float) (height - height*ydata.get(k+1)/nAxisDiv));
    }
    popMatrix();
  }


  private void makeSystem() {
    strokeWeight(4);
    line((float) (axisStart*width), height - 30, (float) (axisEnd*width), height - 30);
    strokeWeight(1);
    fill(0);
    for(int k = 0; k <= nAxisDiv; k++) {
      text(String.format("%d", k), (float) (axisStart*width + k*(width*(axisEnd-axisStart)/nAxisDiv)), height-10);
    }

    float constrainedX = mouseX < axisStart*width ? (float)(axisStart*width) : mouseX > axisEnd*width ? (float)(axisEnd*width) : mouseX;
    markerY = (float) (height - height*(constrainedX - axisStart*width)/((axisEnd-axisStart)*width));
    image(boogie, constrainedX, height-147, 65, 114);

    text("Time:", width/10, 9*height/10 - 30);
    text(String.format("%d", elapsedFrames/frRate) , width/10 + 50, 9*height/10 - 30);
  }


}
