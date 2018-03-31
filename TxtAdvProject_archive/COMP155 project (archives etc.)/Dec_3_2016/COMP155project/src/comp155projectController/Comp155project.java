/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/***********************************************************
 * Project notes: (1) Make consoleOuptutField into a JTextArea
 * (2) Use Github's Octicons
 */

package comp155projectController;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import comp155projectView.Window;
import comp155projectModel.Game;
import comp155projectModel.environment.Environment;
import comp155projectModel.environment.Location;
import comp155projectModel.environment.OutConnection;

/**
 *
 * @author conor
 */
public class Comp155project {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Comp155project controller = new Comp155project();
        controller.start();
    }
    
    private Game game;    
    private Window window;
    private Environment environment;
    ArrayList<Location> changedLocationsContainer = new ArrayList<>();
    
    private void start() {
        int chosenEnvironmentNumber = 0;
        this.game = new Game(this);
        this.window = new Window(this);
        this.environment = new Environment(this);
        this.changedLocationsContainer = new ArrayList<>();
        //environment.createRandomEnvironment(3,4,environment.AREAS);
        environment.createFixedEnvironment(0);

    }
    
    public void textFromWindow(String text) {
        String textRefined;
        String textInterpretMode;
        String textInterpretModes[] ;
        try {textRefined = text.substring(0, text.indexOf('\u0020'));}
        catch (StringIndexOutOfBoundsException noSpace) {textRefined = text;}
        window.addToConsoleLog(text);
        //window.addToConsoleLog(textRefined);
        
        for (String command:window.COMMANDS) {
            if (textRefined == command) {
                window.interpretCommand(text);
                return;
            }
        }
        for (String command:game.COMMANDS){
            if (textRefined == command) {
                game.interpretCommand(text);
            }
        }
    }
    
    public void textFromModel(String text) {
        String textRefined = text.substring(0, text.indexOf('\u0020'));
        window.addToConsoleLog(text);
        window.addToConsoleLog(textRefined);
        
        for (String command:window.COMMANDS) {
            if (textRefined == command) {
                window.interpretCommand(text);
                return;
            }
        }
        for (String command:game.COMMANDS){
            if (textRefined == command) {
                game.interpretCommand(text);
            }
        }
    }
    
    public void sendLocationContainer(int changedLocationNumber) {
        environment.updateChangedLocations(changedLocationNumber, this, changedLocationsContainer);
        //System.out.println("locationContainer: " + changedLocationsContainer);
    }
    
    public void mapUpdateNotification() {
        //System.out.println("from Comp155project: changedLocationsContainer: " + changedLocationsContainer);
        game.mapUpdateNotification(changedLocationsContainer);
        window.mapUpdateNotification(changedLocationsContainer);
        //System.out.println("Comp155project:changedLocationsContainer: " + changedLocationsContainer);
        changedLocationsContainer.clear();
        //System.out.println("Comp155project:changedLocationsContainer: " + changedLocationsContainer);
    }
    
    public void initializeView() {
        window.initializeView(environment.locations.get(0));
    }
    
    public void printLocationsDiagnostic() {
        System.out.println("Locations: " + environment.locations);
        for (Location location:environment.locations) {
            System.out.println("Location " + location.locationNumber + ": " + location);
            System.out.println("\tcontent: " + location.content);
            System.out.println("\toutConnections: " + location.outConnections);
            for (OutConnection outConnection:location.outConnections) {
                System.out.println("\tOutConnection " + outConnection.outConnectionNumber + ": " + outConnection);
                System.out.println("\t\tdirectionToOtherLocation: " + outConnection.directionToOtherLocation);
                System.out.println("\t\totherLocationIndex: " + outConnection.otherLocationIndex);
            }
        }
    }
    /* ***this method is deprecated        
    public void mapUpdateNotification(ArrayList<Location> changedLocations) {
        //try {
        game.mapUpdateNotification(changedLocations);
        window.mapUpdateNotification(changedLocations);
        System.out.println("Comp155project:changedLocations: " + changedLocations);
        System.out.println("Comp155project:changedLocationsContainer: " + changedLocationsContainer);
        changedLocationsContainer.clear();
        System.out.println("Comp155project:changedLocationsContainer: " + changedLocationsContainer);
        //} catch (NullPointerException e) {System.out.println("window error"); }
        
    }
    */
    
    /* ***this method is deprecated
    public void intrusiveMapUpdateNotification() {
        //game.mapUpdateNotification(environment.changedLocations);
        System.out.println("from Comp155project: changedLocation: ");
        //window.mapUpdateNotification(environment.changedLocations);
    }
    */
}
