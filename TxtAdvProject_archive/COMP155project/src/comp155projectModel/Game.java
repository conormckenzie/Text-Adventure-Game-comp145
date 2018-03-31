/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp155projectModel;

import comp155projectController.Comp155project;
import comp155projectModel.environment.Environment;
import comp155projectModel.environment.Location;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 *
 * @author conor
 */
public class Game {
    Comp155project controllerLocalAlias = new Comp155project();
    public int day = 1;
    
    public final int WIN_BONUS = 50;
    
    public final String INITIAL_INSTRUCTIONS1 = "You were invited to a Chirstmas party at a friend's mansion, but have gotten yourself lost. Nobody else is in this part of the mansion.";
    public final String INITIAL_INSTRUCTIONS2 = "     After backtracking your steps, you find the door you entered through is locked. There is laughter on the other side.";
    public final String INITIAL_INSTRUCTIONS3 = "     It seems you are the victim of a practical joke. You must find another way back to the party. There are presents for you along the way.";
    public final String INITIAL_INSTRUCTIONS4 = "     Additionally, this area of the mansion seems to be made to be like a funhouse; you realize doors may close behind you without warning";
    public final String INITIAL_INSTRUCTIONS5 = "     and each location leads to other locations by strange and twisted paths.";
    public final String INITIAL_INSTRUCTIONS6 = "     Type \"north\" to begin.";
    public final String[] INITIAL_INSTRUCTIONS = {INITIAL_INSTRUCTIONS1, INITIAL_INSTRUCTIONS2, INITIAL_INSTRUCTIONS3, INITIAL_INSTRUCTIONS4, INITIAL_INSTRUCTIONS5, INITIAL_INSTRUCTIONS6};

    public final String GAME_OVER = "You have reached an ending. ";
    public final String ARMOR = "sweater";
    public final String ARMOR_WHEN_FOUND = "Christmas sweater";
    public final String LEVER = "statue of Santa Claus";
    public final String SWORD = "game";
    public final String SWORD_WHEN_FOUND = "video game, in its case, titled \"Christmas Adventure\"";
    public final String SWORDSLOT = "radio, playing Christmas music";
    public final String EMBLEM_OF_COURAGE = "amber";
    public final String EMBLEM_OF_COURAGE_WHEN_FOUND = "soft and warm amber ornament, with your name on it";
    public final String EMBLEM_OF_GREED1 = "necklace";
    public final String EMBLEM_OF_GREED1_WHEN_FOUND = "necklace with a grand jewel as a centerpiece, ever so bright and magnificent, with your name on it";
    public final String EMBLEM_OF_GREED2 = "ring";
    public final String EMBLEM_OF_GREED2_WHEN_FOUND = "jade ring, polished and lustrous, with your name on it";
    public final String EMBLEM_OF_GREED3 = "diamond";
    public final String EMBLEM_OF_GREED3_WHEN_FOUND = "beautiful diamond, with your name on it";
    public final String BED = "child's crib";
    public final String DUMBELLS = "an animated reindeer doll";
    public final String GOLD = "chocolate";
    public final String GOLD_WHEN_FOUND = "chocolate bar, sitting on a table for anyone to take";
    public final String GATE_CLOSED = "glass table adorned with tinsel";
    public final String GATE_COPEN = "glass table adorned with tinsel";
    
    public final String DEATH_BY_TAR = "You fell asleep.";
    public final String WIN_TEXT = "Congratulations! You have found your way back to the party. You can continue playing if you like.";
    public final String WIN_TEXT2 = "If you would like to end the game now, go to the bed which is north of you.";
    
    final String COMMAND_SAVE = "save";         //these commands may not contain the blank-space character
    final String COMMAND_RESTORE = "load", COMMAND_LOAD = COMMAND_RESTORE;
    //final String COMMAND_UPDATE = "update";
    public final String[] COMMANDS = {COMMAND_SAVE, COMMAND_LOAD,
        controllerLocalAlias.LEFT, controllerLocalAlias.RIGHT, 
        controllerLocalAlias.UP, controllerLocalAlias.DOWN, controllerLocalAlias.BACK,
        controllerLocalAlias.PICK_UP, controllerLocalAlias.DROP};
    

    
    public Game(Comp155project controller) {
        controllerLocalAlias = controller;
        //environmentLocalAlias = controller.environment;
        
    }
    
    public void interpretCommand(String command) {
        String commandPart1;
        try {commandPart1 = command.substring(0, command.indexOf('\u0020'));}
        catch (StringIndexOutOfBoundsException noSpace) {commandPart1 = command;}
        boolean commandFound = false;
        for (int i = 0; i < COMMANDS.length; i++) {
            if (commandPart1.equals(COMMANDS[i])) {
                commandFound = true;
                switchCommand(i);
                return;
            }
        }
        if (commandFound) {System.out.println("Error: commandFound == true but for loop in "
                + "Game.interpretCommand() continued anyway.");}
        else {
            System.out.println("ERROR: " + command +
                    " is not a recognzied game command, but was passed to Game.interpretCommand()");
        }
    }
    
    private void switchCommand(int commandNumber) {
        switch (commandNumber) {
            case 0: {
                try {controllerLocalAlias.saveGame();} catch (IOException e) {
                    System.out.println("Read failed");}
                break;}
            case 1: {
                break;}
            case 2: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.LEFT);
                break;}
            case 3: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.RIGHT);
                break;}
            case 4: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.UP);
                break;}
            case 5: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.DOWN);
                break;}
            case 6: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.BACK);
                break;}
            case 7: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.PICK_UP);                
                break;}
            case 8: {
                controllerLocalAlias.sendCommandToPlayer(controllerLocalAlias.DROP);                
                break;}
        }
    }
    
    public void handleGameEvents(ArrayList<Location> locationsTravelled, Location currentLocation) {
        try {
            if(locationsTravelled.get(locationsTravelled.size()-1-1).surroundings.equals(
                controllerLocalAlias.environment.AREA_TRAP4)) {
            controllerLocalAlias.displayText("\t" + DEATH_BY_TAR);
            controllerLocalAlias.gameOver();}
            if(locationsTravelled.get(locationsTravelled.size()-1).surroundings.equals(controllerLocalAlias.environment.AREA_FINISH1)) {
                controllerLocalAlias.displayText("\t" + WIN_TEXT);
                controllerLocalAlias.displayText("\t" + WIN_TEXT2);
                controllerLocalAlias.gameIsWon = true;
                controllerLocalAlias.incremementPlayerScore(WIN_BONUS);
            }
        } catch (IndexOutOfBoundsException e) {}
    }
    
    public String getItem(String item) {
        if (item.equals(ARMOR)) {}
        else if (item.equals(LEVER)) {}
        else if (item.equals(SWORD)) {}
        else if (item.equals(SWORDSLOT)) {}
        else if (item.equals(EMBLEM_OF_COURAGE)) {}
        else if (item.equals(EMBLEM_OF_GREED1)) {}
        else if (item.equals(EMBLEM_OF_GREED2)) {}
        else if (item.equals(EMBLEM_OF_GREED3)) {}
        else if (item.equals(BED)) {}
        else if (item.equals(DUMBELLS)) {}
        else if (item.equals(GOLD)) {}
        return item;
    }
    
    public boolean isItemHoldable(String item) {
        if (item.equals(ARMOR) || item.equals(ARMOR_WHEN_FOUND) || 
                item.equals(SWORD) || item.equals(SWORD_WHEN_FOUND) ||
                item.equals(EMBLEM_OF_COURAGE) || item.equals(EMBLEM_OF_COURAGE_WHEN_FOUND) || 
                item.equals(EMBLEM_OF_GREED1) || item.equals(EMBLEM_OF_GREED1_WHEN_FOUND) ||
                item.equals(EMBLEM_OF_GREED2) || item.equals(EMBLEM_OF_GREED2_WHEN_FOUND) ||
                item.equals(EMBLEM_OF_GREED3) || item.equals(EMBLEM_OF_GREED3_WHEN_FOUND) ||
                item.equals(GOLD) || item.equals(GOLD_WHEN_FOUND)) {return true;}
        else {return false;}
    }
    
    public String getHeldItemName(String item) {
        if (item.equals(ARMOR) || item.equals(ARMOR_WHEN_FOUND)) {return ARMOR;}
        else if (item.equals(SWORD) || item.equals(SWORD_WHEN_FOUND)) {return SWORD;}
        else if (item.equals(EMBLEM_OF_COURAGE) || item.equals(EMBLEM_OF_COURAGE_WHEN_FOUND)) {return EMBLEM_OF_COURAGE;}
        else if (item.equals(EMBLEM_OF_GREED1) || item.equals(EMBLEM_OF_GREED1_WHEN_FOUND)) {return EMBLEM_OF_GREED1;}
        else if (item.equals(EMBLEM_OF_GREED2) || item.equals(EMBLEM_OF_GREED2_WHEN_FOUND)) {return EMBLEM_OF_GREED2;}
        else if (item.equals(EMBLEM_OF_GREED3) || item.equals(EMBLEM_OF_GREED3_WHEN_FOUND)) {return EMBLEM_OF_GREED3;}
        else if (item.equals(GOLD) || item.equals(GOLD_WHEN_FOUND)) {return GOLD;}
        else {System.out.println("Error: Item not holdable."); return "NULL_ITEM";}
    }
    
    public String getDroppedItemName(String item) {
        if (item.equals(ARMOR) || item.equals(ARMOR_WHEN_FOUND)) {return ARMOR_WHEN_FOUND;}
        else if (item.equals(SWORD) || item.equals(SWORD_WHEN_FOUND)) {return SWORD_WHEN_FOUND;}
        else if (item.equals(EMBLEM_OF_COURAGE) || item.equals(EMBLEM_OF_COURAGE_WHEN_FOUND)) {return EMBLEM_OF_COURAGE_WHEN_FOUND;}
        else if (item.equals(EMBLEM_OF_GREED1) || item.equals(EMBLEM_OF_GREED1_WHEN_FOUND)) {return EMBLEM_OF_GREED1_WHEN_FOUND;}
        else if (item.equals(EMBLEM_OF_GREED2) || item.equals(EMBLEM_OF_GREED2_WHEN_FOUND)) {return EMBLEM_OF_GREED2_WHEN_FOUND;}
        else if (item.equals(EMBLEM_OF_GREED3) || item.equals(EMBLEM_OF_GREED3_WHEN_FOUND)) {return EMBLEM_OF_GREED3_WHEN_FOUND;}
        else if (item.equals(GOLD) || item.equals(GOLD_WHEN_FOUND)) {return GOLD_WHEN_FOUND;}
        else {System.out.println("Error: Item not holdable."); return "NULL_ITEM";}
    }
    
    void sendToController(String sendText, Comp155project controllerLocalAlias) {
        controllerLocalAlias.textFromModel(sendText);
    }
}
