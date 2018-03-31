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

import comp155projectController.Comp155project;
import comp155projectModel.Game;
/**
 *
 * @author conor
 */
public class Environment {
    public ArrayList<Location> locations = new ArrayList<>();
    boolean isRandom;
    final String AREA1 = "view";
    public final String[] AREAS = {AREA1};
    public final String DIRECTION_LEFT = "left", LEFT = DIRECTION_LEFT;
    public final String DIRECTION_RIGHT = "right", RIGHT = DIRECTION_RIGHT;
    public final String DIRECTION_UP = "up", UP = DIRECTION_UP;
    public final String DIRECTION_DOWN = "down", DOWN = DIRECTION_DOWN;
    public final String[] directions = {DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_UP, DIRECTION_DOWN};
    Random rand = new Random();
    
    Comp155project controllerLocalAlias = new Comp155project();
    
    public Environment(Comp155project controller) {
        controllerLocalAlias = controller;
        this.locations = new ArrayList<>();
    }

    public void createRandomEnvironment(int minSize, int maxSize, String[] areaNames) {
        if (isRandom) {
            String defaultDirection = DIRECTION_LEFT;
            int actualSize = rand.nextInt(maxSize-minSize+1) + minSize;
            //int actualSize = 3;
            for (int remainingLocations = actualSize; remainingLocations > 0; remainingLocations--) {
                String buildInDirection = directions[rand.nextInt(directions.length)];
                int buildFromLocationNumber;
                if (directions.length!=0) {buildFromLocationNumber = rand.nextInt(directions.length);}
                else {buildFromLocationNumber = -1;}
                boolean twoWay = true;
                int stringLength = rand.nextInt(10);
                String locationContent = null;
                char nextLetter = (char)rand.nextInt(256);
                for (int i = 0; i < stringLength; i++) {
                    locationContent = locationContent.concat(Character.toString(nextLetter));
                }
                addNewLocation(actualSize, buildInDirection, buildFromLocationNumber, twoWay, locationContent);
            }
        } else {   
            System.out.println("Error: isRandom is false, but createRandomEnvironment was called.");
        }
    }
    
    public void createFixedEnvironment(int environmentNumber) {
        String defaultDirection = LEFT;
        int actualSize = 0;
        switch (environmentNumber) {
            case 0: {
                //modify addNewLocation so that it rejects having more than 1 outconnection in each direction
                //modify addNewLocation so that it assumes the role of addNewConnection where appropriate,
                //  and explicitly takes a buildToLocation argument. 
                addNewLocation(actualSize, null, -1, true, "L0");
                controllerLocalAlias.initializeView();
                addNewLocation(actualSize, UP, 0, true, "L1");
                addNewLocation(actualSize, UP, 1, false, "L2");
                addNewLocation(actualSize, RIGHT, 0, true, "L3");
                addNewLocation(actualSize, RIGHT, 3, true, "L4");
                addNewLocation(actualSize, UP, 4, true, "L5");
                addNewLocation(actualSize, UP, 5, true, "L6");
                addNewLocation(actualSize, RIGHT, 2, true, "L7");
                addNewConnection(6, RIGHT, 7, true);
                addNewLocation(actualSize, RIGHT, 4, true, "L8");
                addNewConnection(8, LEFT, 0, true);
                break;}
            default: {System.out.println("environmentNumber " + environmentNumber + " does not exist.");
                actualSize = 3;
                break;}
        }
        
    }
    
    void addNewLocation(Integer environmentSize, String buildInDirection, 
            int buildFromLocationNumber, boolean twoWay, String content) {
        Location newLocation = new Location();
        System.out.println(newLocation.locationNumber = locations.size());
        locations.add(newLocation);
        environmentSize++;
        locations.get(newLocation.locationNumber).content = content;
        preUpdateChangedLocations(newLocation.locationNumber, controllerLocalAlias);
        //System.out.println("changedLocations: " + changedLocations);
        
        if ((buildInDirection!=null)&&(buildFromLocationNumber>=0)) {
            locations.get(buildFromLocationNumber).addConnectedLocation(
                    newLocation.locationNumber, (buildInDirection));
            if (twoWay) {locations.get(newLocation.locationNumber).addConnectedLocation(
                    buildFromLocationNumber, getOppositeLocation(buildInDirection));}
        }
        
        //The following code connects each location to a numerically adjacent location.
//        try {
//            locations.get(newLocation.locationNumber).addConnectedLocation(newLocation.locationNumber-1, "west");
//            locations.get(newLocation.locationNumber-1).addConnectedLocation(newLocation.locationNumber, "east");
//            //change the next line of code to update only if location is not already in changedLocations
//            //updateChangedLocations(newLocation.locationNumber-1, controllerLocalAlias);
//            //locations.get(newLocation.locationNumber);
//        }
//        catch (IndexOutOfBoundsException outOfBounds) {/*cannot connect to 
//            location #(newLocation.locationNumber-1 because locationNumber = 0
//            and so newLocation is the first location*/}

        //System.out.println("actualSize: " + environmentSize);
        //controllerLocalAlias.printLocationsDiagnostic();
        
    }
    
    void removeLocation(/**/) {
        
    }
    
    void addNewConnection(int connectToLocationNumber, String buildInDirection, 
            int connectFromLocationNumber, boolean twoWay) {
        locations.get(connectFromLocationNumber).addConnectedLocation(
                connectToLocationNumber, (buildInDirection));
        if (twoWay) {locations.get(connectToLocationNumber).addConnectedLocation(
                connectFromLocationNumber, getOppositeLocation(buildInDirection));}
        //System.out.println("New Connection added: " + connectFromLocationNumber + ", " + 
        //        buildInDirection + ", " + connectToLocationNumber + ", " + twoWay);
        //controllerLocalAlias.printLocationsDiagnostic();
    }
    
    public void preUpdateChangedLocations(int changedLocationNumber, Comp155project controllerLocalAlias) {
        controllerLocalAlias.sendLocationContainer(changedLocationNumber);
    }
    
    public void updateChangedLocations(int changedLocationNumber, 
            Comp155project controllerLocalAlias, ArrayList<Location> locationsToSend) {
        //changedLocations.add(locations.get(changedLocationNumber));
        locationsToSend.add(locations.get(changedLocationNumber));
        //changedLocations = locations.get(changedLocationNumber);
        //System.out.println("changedLocation: " + changedLocations);
        //System.out.println("locationsToSend: " + locationsToSend);
        //this.controllerLocalAlias.intrusiveMapUpdateNotification();
        controllerLocalAlias.mapUpdateNotification();
        //locations.get(changedLocationNumber).isChanged = true;
    }
    
    String getOppositeLocation(String directionLocation) {
        int directionLocationNumber = getDirectionNumber(directionLocation);
        if (directionLocationNumber%2 ==0) {
            return (directions[directionLocationNumber+1]);
        } else {
            return (directions[directionLocationNumber-1]);
        }
    }
    
    int getDirectionNumber(String directionLocation) {
        for (int i = 0; i < directions.length; i++) {
            if (directionLocation.equals(directions[i])) {return i;} }
        System.out.print("Failed to get DirectionNumber for Direction " + directionLocation);
        return -1;
    }
    
    void mapUpdateNotification() {
        
    }
}
