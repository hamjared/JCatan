package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.JCatan.Player;

import javax.swing.JLabel;
import java.awt.Font;

public class PlayerPanel extends JPanel
{
	Player player;
	
    public PlayerPanel(int x, int y, int width, int height, Player player)
    {
        super();
        setBackground(Color.ORANGE);
        setBounds(x, y, 463, 126);
        setLayout(null);
        this.player = player;
        
        JLabel playerNameLabel = new JLabel(player.getName());
        playerNameLabel.setBounds(10, 11, 46, 14);
        add(playerNameLabel);
        
        JLabel numResourceCardLabel = new JLabel("12");
        numResourceCardLabel.setBounds(84, 101, 28, 14);
        add(numResourceCardLabel);
        
        JLabel numDevelopCardsLabel = new JLabel("12");
        numDevelopCardsLabel.setBounds(143, 101, 28, 14);
        add(numDevelopCardsLabel);
        
        JLabel numRoadsLabel = new JLabel("12");
        numRoadsLabel.setBounds(274, 110, 46, 14);
        add(numRoadsLabel);
        
        JLabel numSettlementsLabel = new JLabel("12");
        numSettlementsLabel.setBounds(335, 110, 20, 14);
        add(numSettlementsLabel);
        
        JLabel numCitiesLabel = new JLabel("12");
        numCitiesLabel.setBounds(407, 110, 46, 14);
        add(numCitiesLabel);
        
        JLabel lblNewLabel = new JLabel("VP");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(261, 0, 57, 61);
        add(lblNewLabel);
        
        JLabel victoryPointsLabel = new JLabel("16");
        victoryPointsLabel.setBounds(302, 27, 46, 14);
        add(victoryPointsLabel);
        
        JLabel longestRoadLabel = new JLabel("LR");
        longestRoadLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        longestRoadLabel.setBounds(348, 11, 46, 34);
        add(longestRoadLabel);
        
        JLabel lblNewLabel_2 = new JLabel("16");
        lblNewLabel_2.setBounds(384, 27, 46, 14);
        add(lblNewLabel_2);
        
        
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	Graphics2D g2 = (Graphics2D) g;
    	
    	drawShape(new Rectangle2D.Double(0,0, 10,30), 0, 275, 75, 1, g2);
    	drawShape(new SettlementShape(0,0, 30,30), 0, 325, 75, 1, g2);
    	drawShape(new CityShape(30,30), 0, 400,75, 1, g2);
    	
    	drawShape(new Rectangle2D.Double(0,0, 35,50), 180, 110, 90, 1, g2); 
    	drawShape(new Rectangle2D.Double(0,0, 35,50), 180, 175, 90, 1, g2);

    	
    	
    	
    	
    	
    }
    
    
    private void drawShape(Shape shape, int rotateDegrees, int translate_x, int translate_y, int scale, Graphics2D g2) {
    	AffineTransform prev = g2.getTransform();
    	Color prevColor = g2.getColor();
    	g2.translate(translate_x, translate_y);
    	g2.scale(scale, scale);
    	g2.rotate(Math.toRadians(rotateDegrees));
    	g2.setColor(player.getColor());
    	g2.setBackground(player.getColor());
    	g2.fill(shape);
    	g2.setColor(Color.BLACK);
    	g2.draw(shape);
    	g2.setTransform(prev);
    	g2.setColor(prevColor);
    	
    }
}
