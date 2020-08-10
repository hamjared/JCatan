package com.JCatan.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PlayerPanel extends JPanel
{
	private static final long serialVersionUID = 583731958079577019L;

	public PlayerPanel(int x, int y, int width, int height)
    {
        super();
        setBackground(Color.ORANGE);
        setBounds(x, y, width, height);
    }
	
	@Override
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	Graphics2D g2d = (Graphics2D) g.create();
    }
}