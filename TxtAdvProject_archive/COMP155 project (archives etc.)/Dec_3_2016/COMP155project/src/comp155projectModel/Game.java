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
    final String COMMAND_SAVE = "save";         //these commands may not contain the blank-space character
    //final String COMMAND_UPDATE = "update";
    public final String[] COMMANDS = {COMMAND_SAVE, /*COMMAND_UPDATE*/};
    
    Comp155project controllerLocalAlias = new Comp155project();
    
    public Game(Comp155project controller) {
        controllerLocalAlias = controller;
        
    }
    
    public void interpretCommand(String command) {
        String commandPart1;
        try {commandPart1 = command.substring(0, command.indexOf('\u0020'));}
        catch (StringIndexOutOfBoundsException noSpace) {commandPart1 = command;}
        boolean commandFound = false;
        for (String COMMAND:COMMANDS) {
            if (commandPart1 == COMMAND) {
                commandFound = false;
                return;
            }
            System.out.println("ERROR: " + command +
                    " is not a recognzied game command, but was passed to game.interpretCommand()");
            //if (commandFound) {
        }
        if (commandFound) {System.out.println("Error: commandFound == true but for loop in "
                + "Game.interpretCommand() continued anyway.");}
        else {
            System.out.println("ERROR: " + command +
                    " is not a recognzied game command, but was passed to Game.interpretCommand()");
        }
    }
    
    void sendToController(String sendText, Comp155project controllerLocalAlias) {
        controllerLocalAlias.textFromModel(sendText);
    }
    
    void createObject(/**/) {
        
    }
    
    void removeObject(/**/) {
        
    }
    
    void addPlayer(/**/) {
        
    }
    
    void removePlayer(/**/) {
        
    }
    
    public void mapUpdateNotification(ArrayList<Location> changedLocations) {
        
    }
}
