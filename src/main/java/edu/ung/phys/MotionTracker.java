package edu.ung.phys;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

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
  public PImage boogie;
  
  
  public void settings() {
    size(1200, 640);
  }
  
  
  public void setup() {
    frameRate(20);
    markerX = 0;
    markerY = height/2;
    isRunning = false;
    trailX = new ArrayList<>();
    trailY = new ArrayList<>();
    boogie = loadImage("boogie.png");
  }
  
  
  public void draw() {
    background(200);

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
    }

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
    popMatrix();

    strokeWeight(4);
    line((float) (0.25*width), height - 30, (float) (0.75*width), height - 30);
    strokeWeight(1);

    float constrainedX = mouseX < 0.25*width ? (float)(0.25*width) : mouseX > 0.75*width ? (float)(0.75*width) : mouseX;
    markerY = (float) (height - height*(constrainedX - 0.25*width)/(0.5*width));
    image(boogie, constrainedX, height-147, 65, 114);

    if(isRunning && markerX < width - 20) {
      markerX += 2;
      trailX.add(markerX);
      trailY.add(markerY);
    }

  }


}
