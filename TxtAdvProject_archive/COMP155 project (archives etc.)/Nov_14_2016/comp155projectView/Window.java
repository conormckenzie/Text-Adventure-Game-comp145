/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp155projectView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import comp155projectController.Comp155project;
//import static javafx.scene.paint.Color.color;
/**
 *
 * @author conor
 */
public class Window extends JFrame{
    final String COMMAND_VIEW = "view";         //these commands may not contain the blank-space character
    public final String[] COMMANDS = {COMMAND_VIEW};
    
    int outputAreaHeight = 25;
    Comp155project controllerLocalAlias = new Comp155project();
    
    Color activeTextBackgroundColor = Color.LIGHT_GRAY;
    Color consoleOutputBackgroundColor = Color.DARK_GRAY;
    Color outputBorderColor = Color.RED;
    Color inputBorderColor = Color.BLUE;
    
    //GridLayout experimentLayout = new GridLayout(0,2);
    
    
    Container container = getContentPane();
    JTextField consoleInputField, //testField,
            consoleOutputField[] = new JTextField[outputAreaHeight], 
            activeTextField[] = new JTextField[outputAreaHeight];
    JPanel //consoleInputPanel = new JPanel(), 
            consoleOutputPanel = new JPanel(), 
            activeTextPanel = new JPanel();
    String displayText = "";
    TextHandler handler = null;
    int[] ioFieldLength = {75, 25, 75};        
        /*elements represent length of consoleInputField, consoleOutputField[n], 
        and activeTextField[n], respectively.*/
	//Constructor
    public Window(Comp155project controller) {
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
         
                
        final JPanel compsToExperiment = new JPanel();
        //compsToExperiment.setLayout(experimentLayout);
                
        container.setBackground(new Color(64,32,64));
        container.setLayout(new FlowLayout());
        //GridBagConstraints c = new GridBagConstraints();
        
        consoleInputField = new JTextField(ioFieldLength[0]);
        //testField = new JTextField(ioFieldLength[0]);
        consoleInputField.setBorder(BorderFactory.createLineBorder(inputBorderColor));
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
            //consoleOutputPanel[i] = new JPanel();
            //activeTextPanel[i] = new JPanel();
            consoleOutputPanel.add(consoleOutputField[i]);
            activeTextPanel.add(activeTextField[i]);
                //container.add(consoleOutputPanel[i]);
                //container.add(activeTextPanel[i]);
                //container.add(consoleOutputField[i]);
                //container.add(activeTextField[i]);
                
        }
        
        container.add(consoleOutputPanel);
        container.add(activeTextPanel);
        container.add(consoleInputField);
        //container.add(testField);
        
        /*container.add(consoleInputPanel);
        container.add(consoleOutputPanel);
        container.add(activeTextPanel);*/
        handler = new TextHandler();
        consoleInputField.addActionListener(handler);
        
        Dimension frameSize = new Dimension(1300, outputAreaHeight*23+70);
        this.setSize(frameSize);
        this.setMinimumSize(frameSize);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        }
    
        void sendToController(String sendText, Comp155project controllerLocalAlias){
            controllerLocalAlias.textFromWindow(sendText);
        }
        
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
                default: consoleInputField.setText("ERROR: " + command +
                        " is not a view command, but was passed to window.interpretCommand()");
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
