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
    public static final String HINT1 = ". The staircase has shifted, blocking your retreat";
    public static final String HINT2 = "";
    public static final String HINT3 = "";
        
    public static final String AREA_NORMAL1 = "in a holly-bough-decked hallway";
    public static final String AREA_NORMAL2 = "in an especially warped hallway. The doors close behind you";
    public static final String AREA_NORMAL2_ENTRY = AREA_NORMAL2 + ". An escalator moves you to the beginning";
    public static final String AREA_NORMAL3 = "in a hallway";
    public static final String AREA_NORMAL4 = "in a decorated hallway";
    public static final String AREA_OTHER1 = "in a picture gallery";
    public static final String AREA_OTHER4 = "in a dimly-lit hallway";
    public static final String AREA_OTHER5 = "in a grand hallway";
    public static final String AREA_TRAP1 = "in a holly-bough-decked hallway";
    public static final String AREA_TRAP4 = "at a bedroom, and you feel the need to sleep";
    public static final String AREA_STAIRS_TOP1 = "at the top of a staircase";
    public static final String AREA_STAIRS_BOTTOM1 = "at the bottom of a staircase";
    public static final String AREA_STAIRS_MIDDLE1 = "on a staircase";
    public static final String AREA_STAIRS_TOP3 = "at the top of a holly-bough-decked staircase";
    public static final String AREA_STAIRS_BOTTOM3 = "at the bottom of a holly-bough-decked staircase";
    public static final String AREA_STAIRS_MIDDLE3 = "on a holly-bough-decked staircase";
    public static final String AREA_FINISH1 = "in the living room, with a fireplace and the Christmas tree";
    public static final String AREA_HINT1 = AREA_STAIRS_MIDDLE1 + HINT1;
    public static final String AREA_HINT2 = AREA_NORMAL2 + HINT2;
    public static final String AREA_HINT3 = AREA_NORMAL2 + HINT3;

    public static final String[] AREAS = {};
    Random rand = new Random();
    
    Comp155project controllerLocalAlias = new Comp155project();
    Game gameLocalAlias = new Game(controllerLocalAlias);
    
    public Environment(Comp155project controller) {
        controllerLocalAlias = controller;
        gameLocalAlias = controller.getGame();
        this.locations = new ArrayList<>();
    }
    
    public void createFixedEnvironment(int environmentNumber) {
        int actualSize = 0;
        switch (environmentNumber) {
            case 0: {
                //modify addNewLocation so that it rejects having more than 1 outconnection in each direction
                //modify addNewLocation so that it assumes the role of addNewConnection where appropriate,
                //  and explicitly takes a buildToLocation argument. 
                addNewLocation(actualSize, null, -1, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 0, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 1, true, AREA_OTHER1);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 0, true, AREA_NORMAL1, 
                        gameLocalAlias.DUMBELLS);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 3, true, AREA_TRAP1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 4, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 5, true, AREA_OTHER1, 
                        gameLocalAlias.GATE_CLOSED);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 2, true, AREA_OTHER1, 
                        gameLocalAlias.EMBLEM_OF_GREED1_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 4, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, 4, true, AREA_NORMAL1,
                        gameLocalAlias.GOLD_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, 1, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 10, true, AREA_STAIRS_BOTTOM1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 11, true, AREA_STAIRS_MIDDLE1);
                addNewLocation(actualSize, controllerLocalAlias.UP, 12, false, AREA_HINT1);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 13, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, 9, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, 15, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, 13, true, AREA_STAIRS_TOP1);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, 17, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, 18, true, AREA_NORMAL1);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, 19, false, AREA_NORMAL2_ENTRY);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, 20, false, AREA_HINT2);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, 21, false, AREA_HINT3);
                addNewLocation(actualSize, controllerLocalAlias.UP, 22, false, AREA_NORMAL2);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 23, false, AREA_NORMAL2,
                        gameLocalAlias.GOLD_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.UP, 23, false, AREA_NORMAL2);
                addNewLocation(actualSize, controllerLocalAlias.UP, 24, false, AREA_NORMAL2, 
                        gameLocalAlias.ARMOR_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, 16, false, AREA_STAIRS_BOTTOM3);
                int repetitionsOfSpiralStaircase = 3, reps = repetitionsOfSpiralStaircase;
                for (int i = 0; i < reps; i++) { 
                    addNewLocation(actualSize, controllerLocalAlias.UP, 27 + 4*i, true, AREA_STAIRS_MIDDLE3);
                    addNewLocation(actualSize, controllerLocalAlias.LEFT, 28 + 4*i, true, AREA_STAIRS_MIDDLE3);
                    addNewLocation(actualSize, controllerLocalAlias.DOWN, 29 + 4*i, true, AREA_STAIRS_MIDDLE3);
                    addNewLocation(actualSize, controllerLocalAlias.RIGHT, 30 + 4*i, true, AREA_STAIRS_MIDDLE3);
                    addNewConnection(16, controllerLocalAlias.RIGHT, 27+4*i, false);
                }
                int baseCounter = 27+4*reps;
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter, true, AREA_STAIRS_TOP3);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, baseCounter+1, true, AREA_NORMAL3, 
                        gameLocalAlias.EMBLEM_OF_GREED2_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, baseCounter+2, true, AREA_NORMAL3, 
                        gameLocalAlias.LEVER);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, 6, true, AREA_NORMAL3);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+4, true, AREA_NORMAL3);
                addNewLocation(actualSize, controllerLocalAlias.UP, 13, true, AREA_STAIRS_TOP1,
                        gameLocalAlias.GOLD_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+5, false, AREA_OTHER4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+7, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+8, true, AREA_NORMAL4,
                        gameLocalAlias.GOLD_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+9, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+10, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+11, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+12, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+13, true, AREA_NORMAL4, 
                        gameLocalAlias.SWORD);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+7, true, AREA_OTHER4);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, baseCounter+15, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, baseCounter+16, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, baseCounter+17, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+15, true, AREA_OTHER4, 
                        gameLocalAlias.GOLD_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+19, true, AREA_OTHER4);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, baseCounter+20, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.LEFT, baseCounter+21, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+20, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+23, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, baseCounter+24, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, baseCounter+25, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+24, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, baseCounter+27, true, AREA_OTHER4, 
                        gameLocalAlias.EMBLEM_OF_GREED3_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+28, true, AREA_OTHER4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+29, true, AREA_OTHER4);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+30, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+30, true, AREA_OTHER4,
                        gameLocalAlias.GOLD_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+32, false, AREA_TRAP4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+5, true, AREA_OTHER5);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+34, true, AREA_OTHER5);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+35, true, AREA_OTHER5);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+36, true, AREA_OTHER5);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+37, true, AREA_OTHER5);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+38, true, AREA_OTHER5, 
                        gameLocalAlias.SWORDSLOT);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+22, true, AREA_NORMAL4);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+40, true, AREA_OTHER4);
                addNewLocation(actualSize, controllerLocalAlias.RIGHT, baseCounter+41, true, AREA_OTHER4, 
                        gameLocalAlias.EMBLEM_OF_COURAGE_WHEN_FOUND);
                addNewLocation(actualSize, controllerLocalAlias.DOWN, 0, true, AREA_NORMAL1, 
                        gameLocalAlias.BED);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+39, true, AREA_FINISH1);
                addNewLocation(actualSize, controllerLocalAlias.UP, baseCounter+44, false, AREA_TRAP4);
                addNewConnection(6, controllerLocalAlias.RIGHT, 7, true);
                addNewConnection(8, controllerLocalAlias.LEFT, 0, true);
                addNewConnection(14, controllerLocalAlias.RIGHT, 14, false);
                addNewConnection(7, controllerLocalAlias.UP, 16, false);
                addNewConnection(20, controllerLocalAlias.DOWN, 21, false);
                addNewConnection(20, controllerLocalAlias.DOWN, 22, false);
                addNewConnection(20, controllerLocalAlias.LEFT, 22, false);
                addNewConnection(20, controllerLocalAlias.LEFT, 23, false);
                addNewConnection(20, controllerLocalAlias.RIGHT, 24, false);
                addNewConnection(20, controllerLocalAlias.LEFT, 25, false);
                addNewConnection(20, controllerLocalAlias.UP, 25, false);
                addNewConnection(20, controllerLocalAlias.RIGHT, 25, false);
                addNewConnection(7, controllerLocalAlias.UP, 26, false);
                addNewConnection(baseCounter+22, controllerLocalAlias.RIGHT, baseCounter+31, false);
                addNewConnection(baseCounter+33, controllerLocalAlias.RIGHT, baseCounter+33, true);
                addNewConnection(baseCounter+33, controllerLocalAlias.DOWN, baseCounter+33, true);
                addNewConnection(2, controllerLocalAlias.RIGHT, baseCounter+14, false);
                addNewConnection(baseCounter+18, controllerLocalAlias.LEFT, baseCounter+22, false);
                addNewConnection(baseCounter+18, controllerLocalAlias.UP, baseCounter+21, false);
                addNewConnection(baseCounter+18, controllerLocalAlias.LEFT, baseCounter+40, false);
                addNewConnection(baseCounter+18, controllerLocalAlias.RIGHT, baseCounter+40, false);
                addNewConnection(baseCounter+40, controllerLocalAlias.LEFT, baseCounter+41, false);
                addNewConnection(1, controllerLocalAlias.LEFT, baseCounter+44, false);
                addNewConnection(5, controllerLocalAlias.RIGHT, baseCounter+44, false);
                addNewConnection(baseCounter+45, controllerLocalAlias.UP, baseCounter+45, false);
                addNewConnection(baseCounter+45, controllerLocalAlias.LEFT, baseCounter+45, true);
                break;}
            default: {
                System.out.println("environmentNumber " + environmentNumber + " does not exist.");
                actualSize = 3;
                break;}
        }
        
    }
    
    void addNewLocation(Integer environmentSize, String buildInDirection, 
            int buildFromLocationNumber, boolean twoWay, String surroundings) {
        Location newLocation = new Location();
        System.out.println(newLocation.locationNumber = locations.size());
        locations.add(newLocation);
        environmentSize++;
        locations.get(newLocation.locationNumber).surroundings = surroundings;
        preUpdateChangedLocations(newLocation.locationNumber, controllerLocalAlias);
        //System.out.println("changedLocations: " + changedLocations);
        
        if ((buildInDirection!=null)&&(buildFromLocationNumber>=0)) {
            locations.get(buildFromLocationNumber).addConnectedLocation(
                    newLocation.locationNumber, (buildInDirection));
            if (twoWay) {locations.get(newLocation.locationNumber).addConnectedLocation(
                    buildFromLocationNumber, getOppositeDirection(buildInDirection));}
        }
        
    }
    
    void addNewLocation(Integer environmentSize, String buildInDirection, 
            int buildFromLocationNumber, boolean twoWay, String surroundings, String item) {
        addNewLocation(environmentSize, buildInDirection, buildFromLocationNumber, twoWay, surroundings);
        locations.get(locations.size()-1).item = item;
        locations.get(locations.size()-1).itemIsMovable = gameLocalAlias.isItemHoldable(item);
    }
    
    void addNewConnection(int connectToLocationNumber, String buildInDirection, 
            int connectFromLocationNumber, boolean twoWay) {
        locations.get(connectFromLocationNumber).addConnectedLocation(
                connectToLocationNumber, (buildInDirection));
        if (twoWay) {locations.get(connectToLocationNumber).addConnectedLocation(
                connectFromLocationNumber, getOppositeDirection(buildInDirection));}
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
        //controllerLocalAlias.mapUpdateNotification();
        //locations.get(changedLocationNumber).isChanged = true;
    }
    
    String getOppositeDirection(String directionLocation) {
        int directionLocationNumber = getDirectionNumber(directionLocation);
        if (directionLocationNumber%2 ==0) {
            return (controllerLocalAlias.directions[directionLocationNumber+1]);
        } else {
            return (controllerLocalAlias.directions[directionLocationNumber-1]);
        }
    }
    
    int getDirectionNumber(String directionLocation) {
        for (int i = 0; i < controllerLocalAlias.directions.length; i++) {
            if (directionLocation.equals(controllerLocalAlias.directions[i])) {return i;} }
        System.out.print("Failed to get DirectionNumber for Direction " + directionLocation);
        return -1;
    }
    
    public Location locationAt(int Xcoord, int Ycoord) {
        for (Location location:locations) {
            if (location.Xcoord == Xcoord && location.Ycoord == Ycoord) {
                return (location);
            }
        }
        System.out.println("Error: no location with Xcoord " + Xcoord + " and Ycoord " + Ycoord);
        return(null);
    }
}
