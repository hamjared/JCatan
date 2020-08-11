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
        numDevelopCardsLabel.setBounds(122, 101, 28, 14);
        add(numDevelopCardsLabel);
        
        
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	Graphics2D g2 = (Graphics2D) g;
    	
    	drawShape(new Rectangle2D.Double(0,0, 10,30), 0, 275, 75, 1, g2);
    	drawShape(new SettlementShape(0,0, 30,30), 0, 325, 75, 1, g2);
    	drawShape(new CityShape(30,30), 0, 400,75, 1, g2);

    	
    	
    	
    	
    	
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
