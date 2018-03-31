/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp155projectView;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import comp155projectController.Comp155project;
import comp155projectModel.Game;
import comp155projectModel.environment.Environment;
import comp155projectModel.environment.Location;
import comp155projectModel.environment.OutConnection;

//import static javafx.scene.paint.Color.color;
/**
 *
 * @author conor
 */
public class Window extends JFrame{
    
    //Declaration of Window commands and parameters; these commands may not contain the blank-space character
    final String COMMAND_VIEW = "view";
    public final String[] COMMANDS = {COMMAND_VIEW};
    final String COMMAND_PARAM_MAP = "map";
    public int outputNumber = 0;
    
    public ArrayList<String> consoleOutputList = new ArrayList<>(); 
    Comp155project controllerLocalAlias = new Comp155project();
    Game gameLocalAlias = new Game(controllerLocalAlias);
    
    //Declaration of colours used in display
    Color activeTextBackgroundColor = Color.LIGHT_GRAY;
    Color consoleOutputBackgroundColor = Color.DARK_GRAY;
    Color outputBorderColor = Color.RED;
    Color inputBorderColor = Color.BLUE;
    
    //Declaration of parameters for GUI creation.
            /*elements of ioFieldLength represent length of consoleInputField, consoleOutputField[n], 
            and activeTextField[n], respectively.*/
    int[] ioFieldLength = {75, 10, 75};
    int outputAreaHeight = 25;
    int spacingBetweenLocations = 3;
    int horizontalLocationView = ioFieldLength[2]/spacingBetweenLocations;
    int verticalLocationView = outputAreaHeight;
    
    //Declaration of paramaters for displaying surroundings in activeTextField[]
    int centerlocation;
    Dimension centerLocationCoord = new Dimension();
    ArrayList<Location> displayedLocations = new ArrayList<>();
    
    //Declaration of necessary Java Swing components, except those being created a variable number of times.
    Container container = getContentPane();
    JTextField consoleInputField,
            consoleOutputField[] = new JTextField[outputAreaHeight], 
            activeTextField[] = new JTextField[outputAreaHeight];
    JPanel consoleOutputPanel = new JPanel(), 
            activeTextPanel = new JPanel();
    String displayText = "";
    TextHandler handler = null;
    
    Location viewableLocations[][];
    
    //Constructor
    public Window(Comp155project controller) {
        //Create JPanels & container and set parameters for their creation
        super("Christmas Adventure");
        controllerLocalAlias = controller;
        gameLocalAlias = controller.game;
        consoleOutputPanel.setPreferredSize(new Dimension((int)(ioFieldLength[1]*12.2+20), 
                outputAreaHeight*23+10));
        consoleOutputPanel.setBackground(consoleOutputBackgroundColor);
        consoleOutputPanel.setBorder(BorderFactory.createLineBorder(outputBorderColor));
        activeTextPanel.setPreferredSize(new Dimension((int)(ioFieldLength[2]*12.2)+20,
                outputAreaHeight*23+10));
        activeTextPanel.setBackground(activeTextBackgroundColor);
        activeTextPanel.setBorder(BorderFactory.createLineBorder(outputBorderColor));
        
        container.setBackground(new Color(64,32,64));
        container.setLayout(new FlowLayout());
        
        //Create consoleInputField
        consoleInputField = new JTextField(ioFieldLength[0]);
        consoleInputField.setBorder(BorderFactory.createLineBorder(inputBorderColor));
        
        //Create consoleOutputFields and activeTextFields
        for (int i = 0; i < outputAreaHeight; i++)
        {
            consoleOutputField[i] = new JTextField("", ioFieldLength[1]); {
                //consoleOutputField[i].setBackground(consoleOutputBackgroundColor);
                consoleOutputField[i].setEditable(false); 
                consoleOutputField[i].setBorder(BorderFactory.createLineBorder(consoleOutputBackgroundColor)); }
            activeTextField[i] = new JTextField("", ioFieldLength[2]); {
                //activeTextField[i].setBackground(activeTextBackgroundColor);
                activeTextField[i].setEditable(false);
                activeTextField[i].setBorder(BorderFactory.createLineBorder(activeTextBackgroundColor));}
            
            consoleOutputPanel.add(consoleOutputField[i]);
            activeTextPanel.add(activeTextField[i]);
                
        }
        
        //Adds text field components to JFrame container.
        container.add(consoleOutputPanel);
        container.add(activeTextPanel);
        container.add(consoleInputField);
        
        //Enables input field to listen for lines of text.
        handler = new TextHandler();
        consoleInputField.addActionListener(handler);
        
        //Set visual parameters for JFrame
        Dimension frameSize = new Dimension(1125, outputAreaHeight*23+70);
        this.setSize(frameSize);
        this.setMinimumSize(frameSize);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
        
    //Sends text from the input field to the controller
    void sendToController(String sendText, Comp155project controllerLocalAlias){
        controllerLocalAlias.textFromWindow(sendText);
        consoleInputField.setText("");
    }
        
    //Adds text to console log (output fields)
    public void addToConsoleLog(String textToAdd) {
        for (int i = 0; i < outputAreaHeight - 1; i++) {
            //include provision for buffering the ouput of consoleOuputField[0]
            consoleOutputField[i].setText(consoleOutputField[i+1].getText());
        }
        consoleOutputField[outputAreaHeight - 1].setText(textToAdd);
    }
        
    public void interpretCommand(String command) {
        String commandPart1 = command.substring(0, command.indexOf('\u0020'));
        switch (commandPart1) {
            case COMMAND_VIEW: break;
            default: System.out.println("ERROR: " + command +
                    " is not a recognzied window command, but was passed to window.interpretCommand()");
        }
    }
        
    public void displayContent(Location location, String preText, String postText) {
        boolean isOutputNumberDisplayed = false;
        String directionsAvailableString = "";
        activeTextField[0].setText("\t\t\t\tScore: " + Integer.toString(controllerLocalAlias.getPlayerScore()));
        for (OutConnection outConnection:location.outConnections) {
            try {
                location.outConnections.get(outConnection.outConnectionNumber+1); //tests if outConnection has a next element
                directionsAvailableString = directionsAvailableString.concat(outConnection.directionToOtherLocation);
                directionsAvailableString = directionsAvailableString.concat(", ");
            } catch (IndexOutOfBoundsException e) {
                if (!(directionsAvailableString.equals("") || directionsAvailableString.equals(null))) {
                    directionsAvailableString = directionsAvailableString.concat("and ");}
                else {directionsAvailableString = directionsAvailableString.concat("only ");}
                directionsAvailableString = directionsAvailableString.concat(outConnection.directionToOtherLocation);
                directionsAvailableString = directionsAvailableString.concat(".");
            }
        }
        if (!(preText.equals("") || preText.equals(null))) {
            displayContent(outputNumber + ":\t" + preText);
            isOutputNumberDisplayed = true;}
        if (isOutputNumberDisplayed) {displayContent("\tYou are " + location.surroundings + ". You can go " + directionsAvailableString);}
        else {displayContent(outputNumber + ":\tYou are " + location.surroundings + ". You can go " + directionsAvailableString);}
        if (!location.item.equals("")) {displayContent("\tIn front of you, there is a " + location.item + ".");}
        if (gameLocalAlias.isItemHoldable(location.item)){displayContent("\tType \"pickup\" to pick it up.");}
//        activeTextField[outputAreaHeight - 1].setText(preText + "\tYou are " + location.surroundings + 
//                ". You can go " + directionsAvailableString + (location.item.equals("")?
//                        "" : " There is a " + location.item + " in front of you.") + postText);
        outputNumber++;
    }
    
    public void displayContent(String text) {
        for (int i = 1; i < outputAreaHeight - 1; i++) {
            activeTextField[i].setText(activeTextField[i+1].getText());
        }
        
        activeTextField[outputAreaHeight - 1].setText(text);
    }
    
    public void showInitialInstructions() {
        for (String instruction:gameLocalAlias.INITIAL_INSTRUCTIONS) {displayContent(instruction);}
    }
    
    public void disableInput() {
        consoleInputField.setEditable(false);
        consoleInputField.setBackground(activeTextBackgroundColor);
    }
    
    //Inner Class TextHandler -- replace with keyboard listener
    private class TextHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == consoleInputField) {
                displayText = e.getActionCommand();
            } 
            //testField.setText(displayText);
            sendToController(displayText, controllerLocalAlias);
            consoleOutputList.add(consoleOutputField[consoleOutputField.length-1].getText());
	}
    }
}
