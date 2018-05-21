package edu.ung.phys;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Txt2Data {

  public ArrayList<Double> x, y;

  public Txt2Data(BufferedReader reader) {
    x = new ArrayList<>();
    y = new ArrayList<>();
    try {
      String thisLine = reader.readLine();
      while(thisLine != null) {
        String[] thisLineSplit = thisLine.split("\\s+");
        x.add(Double.parseDouble(thisLineSplit[0]));
        y.add(Double.parseDouble(thisLineSplit[1]));
        thisLine = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
