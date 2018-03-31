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
import comp155projectView.KeyEventDemo;

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
    
    //private Game game;    
    private Window window;
    
    private void start() {
        //this.game = new Game(this);
        this.window = new Window(this);
    }
    
    public void textFromWindow(String text) {
        String textRefined = text.substring(0, text.indexOf('\u0020'));
        window.addToConsoleLog(text);
        window.addToConsoleLog(textRefined);
        
        for (String command:window.COMMANDS) {
            if (textRefined == command) window.interpretCommand(text);
        }
    }
    
}
