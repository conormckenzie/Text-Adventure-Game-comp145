/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp155projectModel;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import comp155projectController.Comp155project;
import comp155projectModel.environment.Location;
import comp155projectModel.environment.OutConnection;
import comp155projectModel.Game;

/**
 *
 * @author conor
 */
public class Player {
    Location currentLocation = new Location();
    Location indexedLocation = new Location(); //indexedLocation is used in enabling the "back" direction functionality
    int numberOfConsecutiveBacks = 0; 
    public int score = 5001;
    boolean usingBackCommand = false;
    ArrayList<Location> locationsTravelled = new ArrayList<>();
    Comp155project controllerLocalAlias = new Comp155project();
    Game gameLocalAlias = new Game(controllerLocalAlias);
    
    final String noGoWarningText = "You cannot move that way. ";
    final String noMoveBackText = "You cannot move back. ";
    final String noPickupText = "You cannot pick up that item. ";
    final String inventoryFullText = "Your inventory is full. " + noPickupText;
    final String noItemText = "There is nothing to pick up. ";
    final String itemPickedUpText = "You picked up the ";
    final String noDropText = "There is already an item here. You cannot drop any more items here. ";
    final String itemDroppedText = "You dropped the ";
    final int inventorySize = 2;
    String[] itemsHeld = new String[inventorySize];
    
    public Player(Comp155project controller, Location startLocation) {
        controllerLocalAlias = controller;
        currentLocation = startLocation;
        gameLocalAlias = controller.game;
        for (int i = 0; i < itemsHeld.length; i++) {itemsHeld[i] = "";}
    }
    
    public void move(ArrayList<Location> locations, String direction, boolean fromBack) {
        if (!fromBack) {numberOfConsecutiveBacks = 0;}
        System.out.println(currentLocation.outConnections);
        for (OutConnection outConnection:currentLocation.outConnections) {
            if (outConnection.directionToOtherLocation.equals(direction)) {
                currentLocation = locations.get(outConnection.otherLocationIndex);
                locationsTravelled.add(currentLocation);
                score--;
                System.out.println("currentLocation: " + currentLocation.locationNumber);
                controllerLocalAlias.displayLocationText(currentLocation, "", "");
                gameLocalAlias.handleGameEvents(locationsTravelled, currentLocation);
                return;
            }
        }
        System.out.println("currentLocation: " + currentLocation.locationNumber);
        controllerLocalAlias.displayLocationText(currentLocation, noGoWarningText, "");
    }
    
    void moveBackwards(ArrayList<Location> locations) {
        System.out.println("arrived at moveBackwards");
        numberOfConsecutiveBacks++;
        try {
            System.out.println("indexedLocation: " + indexedLocation.locationNumber);
            indexedLocation = locationsTravelled.get(locationsTravelled.size()-2*numberOfConsecutiveBacks);
            System.out.println("indexedLocation: " + indexedLocation.locationNumber);
            for (OutConnection outConnection:currentLocation.outConnections) {
                if (outConnection.otherLocationIndex == indexedLocation.locationNumber) {
                    move(locations, outConnection.directionToOtherLocation, true);
                return;}
            }
            controllerLocalAlias.displayText("\t" + noMoveBackText);
        }
        catch (IndexOutOfBoundsException e) {
            controllerLocalAlias.displayText("\t" + noMoveBackText);
            System.out.println("exception caught");}
    }
    
    void pickUpItem() {
        System.out.println("Item = " + currentLocation.item);
        System.out.println("ItemIsMoveable = " + currentLocation.itemIsMovable);
        if (!currentLocation.item.equals("")) {
            if (currentLocation.itemIsMovable) {
                if (itemsHeld[0].equals("")) {
                    itemsHeld[0]=gameLocalAlias.getHeldItemName(currentLocation.item);
                    currentLocation.item = "";
                    controllerLocalAlias.displayText("\t" + itemPickedUpText + itemsHeld[0] + ". ");
                }
                else if (itemsHeld[1].equals("")) {
                    itemsHeld[1]=gameLocalAlias.getHeldItemName(currentLocation.item);
                    currentLocation.item = "";
                    controllerLocalAlias.displayText("\t" + itemPickedUpText + itemsHeld[1] + ". ");
                }
                else {controllerLocalAlias.displayText("\t" + inventoryFullText);}
            }
            else {controllerLocalAlias.displayText("\t" + noPickupText);}
        }
        else {controllerLocalAlias.displayText("\t" + noItemText);}
    }
    
    void dropItem() {
        if (currentLocation.item.equals("")) {
            controllerLocalAlias.displayText("\t" + itemDroppedText + itemsHeld[0] + ". ");
            currentLocation.item = gameLocalAlias.getDroppedItemName(itemsHeld[0]);
            for (int i = 0; i < itemsHeld.length - 1; i++) {itemsHeld[i] = itemsHeld[i+1];}
            itemsHeld[itemsHeld.length-1] = "";
        }
        else {controllerLocalAlias.displayText("\t" + noDropText);}
    }
    
    public void interpretCommand(ArrayList<Location> locations, String command) {
        for (String direction:controllerLocalAlias.directions) {
            if (direction.equals(command)) {
                move(locations, direction, false); 
                System.out.println("moved in direction " + direction); 
                return;}
        }
        if (command.equals(controllerLocalAlias.BACK)) {moveBackwards(locations); return;}
        if (command.equals(controllerLocalAlias.PICK_UP)) {pickUpItem(); return;}
        if (command.equals(controllerLocalAlias.DROP)) {dropItem(); return;}
    }
}
