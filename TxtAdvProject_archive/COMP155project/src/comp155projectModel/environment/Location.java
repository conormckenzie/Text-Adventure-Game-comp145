/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp155projectModel.environment;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import comp155projectModel.environment.OutConnection;

/**
 *
 * @author conor
 */
public class Location {
    public int locationNumber;
    public String surroundings = "", item = "";
    public boolean itemIsMovable;
    public int Xcoord, Ycoord;
    //boolean isChanged;
    public ArrayList<OutConnection> outConnections = new ArrayList<OutConnection>();
    double visibilityLevel; //visibilityLevel changes the view, making this particular space more or less visible (foggy).
    
    void addConnectedLocation(int locationIndex, String directionToOtherLocation) {
        OutConnection connection1 = new OutConnection();
        connection1.otherLocationIndex = locationIndex;
        connection1.directionToOtherLocation = directionToOtherLocation;
        connection1.outConnectionNumber = outConnections.size();
        outConnections.add(connection1);
    }

}

