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
    
    
    Comp155project controllerLocalAlias = new Comp155project();
    
    //Declaration of colours used in display
    Color activeTextBackgroundColor = Color.LIGHT_GRAY;
    Color consoleOutputBackgroundColor = Color.DARK_GRAY;
    Color outputBorderColor = Color.RED;
    Color inputBorderColor = Color.BLUE;
    
    //Declaration of parameters for GUI creation.
            /*elements of ioFieldLength represent length of consoleInputField, consoleOutputField[n], 
            and activeTextField[n], respectively.*/
    int[] ioFieldLength = {75, 25, 75};
    int outputAreaHeight = 25;
    int spacingBetweenLocations = 3;
    int horizontalLocationView = ioFieldLength[2]/spacingBetweenLocations;
    int verticalLocationView = outputAreaHeight;
    
    //Declaration of paramaters for displaying content in activeTextField[]
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
        super("TextField Test Demo");
        controllerLocalAlias = controller;
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
        Dimension frameSize = new Dimension(1300, outputAreaHeight*23+70);
        this.setSize(frameSize);
        this.setMinimumSize(frameSize);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
        
    //Sends text from the input field to the controller
    void sendToController(String sendText, Comp155project controllerLocalAlias){
        controllerLocalAlias.textFromWindow(sendText);
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
    
    /*void openCinematics() {
        final double animationTime = 1;
        int red_t = 255, green_t = 255, blue_t = 255;
        for (double t = 0; t < animationTime; t = ) {
            red_t = 
            container.setBackground(new Color(red_t,green_t,blue_t));
        }
    }*/
    
    public void initializeView(Location startLocation) {
        centerlocation = startLocation.locationNumber;
        centerLocationCoord = new Dimension((int)(ioFieldLength[2]/25),outputAreaHeight/2);
        displayedLocations.add(startLocation);
        String textOutput = "", textOutputBase = "", textOutputAtom = " ";
        int textOutputBaseWidth = 10;
        for (int i = 0; i < textOutputBaseWidth; i++) {textOutputBase = textOutputBase.concat(textOutputAtom);}
        for (int i = 0; i < centerLocationCoord.width; i++) {textOutput = textOutput.concat(textOutputBase);}
        //System.out.println("t=" + textOutput + "=t");
       // activeTextField[centerLocationCoord.height].setText(textOutput + 
       //         Integer.toString(centerLocationCoord.width) + textOutputBase + Integer.toString(centerLocationCoord.height));
        displayContent(startLocation, 0, 0);
    }
        
    public void mapUpdateNotification(ArrayList<Location> changedLocations) {
        System.out.println("From window: changedLocations: " + changedLocations);
        System.out.println("Displayed Locations:" + displayedLocations);
        //controllerLocalAlias.printLocationsDiagnostic();
        for (Location changedLocation:changedLocations) {
            //for each changed location, check if location is displayable or displayed by comparing it against displayed locations
            /*for (Location displayedLocation:displayedLocations) {
                if(changedLocation == displayedLocation) {
                    displayContent(changedLocation);
                    continue; //continue to the next changed location
                }
                //for each displayed location, check if it has an outConnection to changedLocation
                for (OutConnection outConnection:displayedLocation.outConnections) {
                    if (outConnection.otherLocationIndex == changedLocation.locationNumber) {
                        displayContent(changedLocation);
                    }
                }
            }*/
                
        //activeTextField[changedLocation.locationNumber].setText(Character.toString(changedLocation.content));
        }
    }
        
//    public void displayContent(Location changedLocation) {
//        //for () {
//            System.out.println(changedLocation);
//            activeTextField[changedLocation.locationNumber].setText(changedLocation.content);
//        //}
//        if (true/*changedLocation is displayable*/) {
//                
//        }
//    }
        
        //This function may not be necessary; develop it later if necessary
    public void displayContent(Location changedLocation, int Xcoord, int Ycoord) {
            activeTextField[changedLocation.locationNumber].setText(changedLocation.content);
            
    }
    //Inner Class TextHandler -- replace with keyboard listener
    private class TextHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == consoleInputField) {
                displayText = e.getActionCommand();
            } 
            //testField.setText(displayText);
            sendToController(displayText, controllerLocalAlias);
	}
    }
}
