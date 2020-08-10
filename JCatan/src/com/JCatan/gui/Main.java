package com.JCatan.gui;

import javax.swing.SwingUtilities;

public class Main
{
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			StartMenuFrame startFrame = new StartMenuFrame();
    			startFrame.setVisible(true);
    		}
    	});
    }
}