/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp155projectView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import static javafx.scene.paint.Color.color;
/**
 *
 * @author conor
 */
public class JTextFieldDemo extends JFrame{
    int outputAreaHeight = 20;
    
    Color activeTextBackgroundColor = Color.LIGHT_GRAY;
    Color consoleOutputBackgroundColor = Color.BLACK;
    
    //GridLayout experimentLayout = new GridLayout(0,2);
    
    
    JTextField consoleInputField, 
            consoleOutputField[] = new JTextField[outputAreaHeight], 
            activeTextField[] = new JTextField[outputAreaHeight];
    JPanel consoleInputPanel = new JPanel(), 
            consoleOutputPanel = new JPanel(), 
            activeTextPanel = new JPanel();
    String displayText = "";
    TextHandler handler = null;
    int[] ioFieldLength = {75, 20, 70};        
        /*elements represent length of consoleInputField, consoleOutputField[n], 
        and activeTextField[n], respectively.*/
	//Constructor
    public JTextFieldDemo() {
        super("TextField Test Demo");
        this.setResizable(false);
         consoleOutputPanel.setPreferredSize(new Dimension((int)(ioFieldLength[1]*12.2+20), 
                 outputAreaHeight*31+10));
         consoleOutputPanel.setBackground(consoleOutputBackgroundColor);
         activeTextPanel.setPreferredSize(new Dimension((int)(ioFieldLength[2]*12.2)+20,
                 outputAreaHeight*31+10));
         activeTextPanel.setBackground(activeTextBackgroundColor);
                
        final JPanel compsToExperiment = new JPanel();
        //compsToExperiment.setLayout(experimentLayout);
                
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        //GridBagConstraints c = new GridBagConstraints();
        
        consoleInputField = new JTextField(ioFieldLength[0]);
        for (int i = 0; i < outputAreaHeight; i++)
        {
            consoleOutputField[i] = new JTextField("", ioFieldLength[1]); {
                consoleOutputField[i].setBackground(consoleOutputBackgroundColor);
                consoleOutputField[i].setEditable(false); 
                 }
            activeTextField[i] = new JTextField("", ioFieldLength[2]); {
                activeTextField[i].setBackground(activeTextBackgroundColor);
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
        
        /*container.add(consoleInputPanel);
        container.add(consoleOutputPanel);
        container.add(activeTextPanel);*/
        handler = new TextHandler();
        consoleInputField.addActionListener(handler);
        
        this.setSize(1250, 700);
        
        this.setVisible(true);
    }
	//Inner Class TextHandler -- replace with keyboard listener
    private class TextHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == consoleInputField) {
                displayText = "text1 : " + e.getActionCommand();
            } else if (e.getSource() == consoleOutputField) {
                displayText = "text3 : " + e.getActionCommand();
            }
            JOptionPane.showMessageDialog(null, displayText);
	}
    }
}
