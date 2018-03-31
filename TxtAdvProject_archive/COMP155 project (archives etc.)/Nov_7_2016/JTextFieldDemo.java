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
public class JTextFieldDemo extends JFrame implements ActionListener{
    JTextField consoleInputField; 
    JTextArea consoleOutputArea, consoleLog;
	String displayText = "";
	TextHandler handler = null;
        
        int[] ioFieldLength = {100, 20, 70};        //elements represent length of console, respectively, length of 
        int consoleOutputHeight = 10;
        private final static String newline = "\n";
	//Constructor
	public JTextFieldDemo() {
		super("TextField Test Demo");
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		consoleInputField = new JTextField(ioFieldLength[0]);
		consoleOutputArea = new JTextArea(consoleOutputHeight, ioFieldLength[1]); {
                    consoleOutputArea.setBackground(Color.LIGHT_GRAY);
                    consoleOutputArea.setEditable(false); }
                consoleLog = new JTextArea(consoleOutputHeight, ioFieldLength[2]);
            	container.add(consoleOutputArea);
		container.add(consoleInputField);
                container.add(consoleLog);
		handler = new TextHandler();
		consoleInputField.addActionListener(handler);
		consoleOutputArea.addActionListener(handler);
		setSize(1250, 700);
		setVisible(true);
	}
	//Inner Class TextHandler handles text in the 
	private class TextHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == consoleInputField) {
				displayText = "text1 : " + e.getActionCommand();
			} else if (e.getSource() == consoleOutputArea) {
				displayText = "text3 : " + e.getActionCommand();
			}
			JOptionPane.showMessageDialog(null, displayText);
		}
	}
        
        public void actionPerformed(ActionEvent evt) {
            String text = consoleInputField.getText();
            consoleInputField.append(text + newline);
            consoleInputField.selectAll();
 
            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
}
