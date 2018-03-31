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
import comp155projectModel.Player;

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
    
    public Game game;    
    Window window;
    public Environment environment;
    ArrayList<Location> changedLocationsContainer = new ArrayList<>();
    private Player player;
    public final String DIRECTION_LEFT = "west", LEFT = DIRECTION_LEFT;    
    public final String DIRECTION_RIGHT = "east", RIGHT = DIRECTION_RIGHT;
    public final String DIRECTION_UP = "north", UP = DIRECTION_UP;
    public final String DIRECTION_DOWN = "south", DOWN = DIRECTION_DOWN;
    public final String DIRECTION_BACK = "back", BACK = DIRECTION_BACK;
    public final String[] directions = {DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_UP, DIRECTION_DOWN};
    public final String RESPONSE_PICK_UP = "pickup", PICK_UP = RESPONSE_PICK_UP;
    public final String RESPONSE_DROP = "XXXdropXXX", DROP = RESPONSE_DROP;
    
    public boolean gameIsOn = true;
    public boolean gameIsWon = false;
    
    private void start() {
        int chosenEnvironmentNumber = 0;
        this.game = new Game(this);
        this.window = new Window(this);
        this.environment = new Environment(this);
        this.changedLocationsContainer = new ArrayList<>();
        environment.createFixedEnvironment(0);
        this.player = new Player(this, environment.locations.get(0));
        window.showInitialInstructions();

    }
    
    public Game getGame() {
        return this.game;
    }
    
    public String[] getEnvironmentAreas() {
        return environment.AREAS;
    }
    
    public int getPlayerScore() {
        return player.score;
    }
    
    public void textFromWindow(String text) {
        String textRefined;
        try {textRefined = text.substring(0, text.indexOf('\u0020'));}
        catch (StringIndexOutOfBoundsException noSpace) {textRefined = text;}
        window.addToConsoleLog(text);
        
        for (String command:window.COMMANDS) {
            if (textRefined.equals(command)) {
                window.interpretCommand(text);
                return;
            }
        }
        for (String command:game.COMMANDS){
            if (textRefined.equals(command)) {
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
    
    public void displayLocationText(Location location, String preText, String postText) {
        window.displayContent(location, preText, postText);
    }
    
    public void displayText(String text) {
        window.displayContent(text);
    }
    
    public void sendLocationContainer(int changedLocationNumber) {
        environment.updateChangedLocations(changedLocationNumber, this, changedLocationsContainer);
    }
    
    public void printLocationsDiagnostic() {
        System.out.println("Locations: " + environment.locations);
        for (Location location:environment.locations) {
            System.out.println("Location " + location.locationNumber + ": " + location);
            System.out.println("\tcontent: " + location.surroundings);
            System.out.println("\toutConnections: " + location.outConnections);
            for (OutConnection outConnection:location.outConnections) {
                System.out.println("\tOutConnection " + outConnection.outConnectionNumber + ": " + outConnection);
                System.out.println("\t\tdirectionToOtherLocation: " + outConnection.directionToOtherLocation);
                System.out.println("\t\totherLocationIndex: " + outConnection.otherLocationIndex);
            }
        }
    }
    
    public void sendCommandToPlayer(String command) {
        player.interpretCommand(environment.locations, command);
    }
    
    public void saveGame() throws IOException {
        System.out.println("Save entered");
        FileWriter fileWriter = new FileWriter("savegame.dat", false);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        System.out.println("consoleOutputList: " + window.consoleOutputList);
        for (String command:window.consoleOutputList) {
            printWriter.printf(command + ";");
        }
        printWriter.close();
    }
    
    public void incremementPlayerScore(int bonus)
    {
        player.score += bonus;
    }
    
    public void gameOver() {
        gameIsOn = false;
        window.displayContent(/*game.GAME_OVER + */"You " + (gameIsWon?"found ":"did not find ") + 
                "your way back to the party. Your score was " + player.score + ".");
        window.disableInput();
    }
}
