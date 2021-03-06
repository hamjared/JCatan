package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import com.JCatan.City;
import com.JCatan.DevelopmentCard;
import com.JCatan.Player;
import com.JCatan.Settlement;
import com.JCatan.VictoryPointDevelopmentCard;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerPanel extends JPanel
{
	Player player;
	private JLabel numResourceCardLabel;
	private JLabel numDevelopCardsLabel;
	private JLabel numRoadsLabel;
	private JLabel numSettlementsLabel;
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public JLabel getNumSettlementsLabel() {
		return numSettlementsLabel;
	}

	public void setNumSettlementsLabel(JLabel numSettlementsLabel) {
		this.numSettlementsLabel = numSettlementsLabel;
	}

	private JLabel numCitiesLabel;
	private JLabel victoryPointsLabel;
	private JLabel victoryPointsLabelCard;
	private JLabel longestRoadLabel;
	private JLabel R;
	private JLabel lblNewLabel_1;
	private JLabel numberOfKnights;
	
    public PlayerPanel(int x, int y, int width, int height, Player player)
    {
        super();
        setBackground(Color.ORANGE); 
        setBounds(x, y, width, height);
        setLayout(null);
        this.player = player;
        
        JLabel playerNameLabel = new JLabel(player.getName());
        playerNameLabel.setBounds(10, 11, 46, 14);
        add(playerNameLabel);
        
        numResourceCardLabel = new JLabel("12");
        numResourceCardLabel.setBounds(92, 101, 28, 14);
        add(numResourceCardLabel);
        
        numDevelopCardsLabel = new JLabel("12");
        numDevelopCardsLabel.setBounds(152, 101, 28, 14);
        add(numDevelopCardsLabel);
        
        numRoadsLabel = new JLabel("12");
        numRoadsLabel.setBounds(274, 110, 46, 14);
        add(numRoadsLabel);
        
        numSettlementsLabel = new JLabel("12");
        numSettlementsLabel.setBounds(335, 110, 20, 14);
        add(numSettlementsLabel);
        
        numCitiesLabel = new JLabel("12");
        numCitiesLabel.setBounds(407, 110, 46, 14);
        add(numCitiesLabel);
        
        JLabel lblNewLabel = new JLabel("VP");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(258, 0, 57, 61);
        add(lblNewLabel);
        
        JLabel lblNewLabel2 = new JLabel("VPC");
        lblNewLabel2.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel2.setBounds(190, 0, 57, 61);
        add(lblNewLabel2);
        
        victoryPointsLabel = new JLabel("16");
        victoryPointsLabel.setBounds(302, 27, 46, 14);
        add(victoryPointsLabel);
        
        victoryPointsLabelCard = new JLabel("0");
        victoryPointsLabelCard.setBounds(240, 27, 46, 14);
        add(victoryPointsLabelCard);
        
        JLabel LR = new JLabel("LR");
        LR.setFont(new Font("Tahoma", Font.BOLD, 20));
        LR.setBounds(335, 13, 46, 34);
        add(LR);
        
        longestRoadLabel = new JLabel("16");
        longestRoadLabel.setBounds(372, 27, 20, 14);
        add(longestRoadLabel);
        
        lblNewLabel_1 = new JLabel("K");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setBounds(391, 23, 46, 14);
        add(lblNewLabel_1);
        
        numberOfKnights = new JLabel("0");
        numberOfKnights.setBounds(417, 27, 46, 14);
        add(numberOfKnights);
        
        setLabels();
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D) g;
    	
    	drawShape(new Rectangle2D.Double(0,0, 10,30), 0, 275, 75, 1, g2, player.getColor());
    	drawShape(new SettlementShape(0,0, 30,30), 0, 325, 75, 1, g2, player.getColor());
    	drawShape(new CityShape(30,30), 0, 400,75, 1, g2, player.getColor());
    	
    	drawShape(new Rectangle2D.Double(0,0, 35,50), 180, 110, 90, 1, g2, Color.decode("#d2b48c")); 
    	drawShape(new Rectangle2D.Double(0,0, 35,50), 180, 175, 90, 1, g2, player.getColor() );
    	
    	drawImage("images/dcs__back.png", 180, 175, 90, 35, 50, g2 );
    	
    	setLabels();
    }
    
    private void drawImage(String filename, int rotateDegrees , int x, int y, int width, int height, Graphics2D g2) {
    	BufferedImage image = null;
    	try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		AffineTransform prev = g2.getTransform();
		g2.translate(x, y);
		g2.rotate(Math.toRadians(rotateDegrees));
		
		g2.drawImage(image, 0, 0, width, height, null);
		g2.setTransform(prev);
	}

	private void setLabels() {
		this.numResourceCardLabel.setText("" + player.getResources().size());
		int playerDevCardCounter = 0;
		for (DevelopmentCard card: player.getDevCards()) {
			if (!card.isHasBeenPlayed()) {
				playerDevCardCounter++;
			}
		}
		this.numDevelopCardsLabel.setText("" + playerDevCardCounter);
		this.numRoadsLabel.setText("" + player.getRoads().size());
		this.numSettlementsLabel.setText("" + player.getBuildings().stream().filter(b -> b instanceof Settlement).count());
		this.numCitiesLabel.setText("" + player.getBuildings().stream().filter(b -> b instanceof City).count());
		this.victoryPointsLabel.setText("" + player.calcVictoryPoints());
		int lr = player.calcLongestRoad();
		this.longestRoadLabel.setText("" + lr);
		int knights = player.getNumberOfKnightsPlayed();
		this.numberOfKnights.setText("" + knights);
		int victoryPointsFromCards = 0;
		for(DevelopmentCard card : player.getDevCards()) {
			if(card instanceof VictoryPointDevelopmentCard) {
				if(card.isHasBeenPlayed()) {
					victoryPointsFromCards++;
				}
			}
		}
		this.victoryPointsLabelCard.setText("" + victoryPointsFromCards);
	}

	private void drawShape(Shape shape, int rotateDegrees, int translate_x, int translate_y, int scale, Graphics2D g2, Color color) {
    	AffineTransform prev = g2.getTransform();
    	Color prevColor = g2.getColor();
    	g2.translate(translate_x, translate_y);
    	g2.scale(scale, scale);
    	g2.rotate(Math.toRadians(rotateDegrees));
    	g2.setColor(color);
    	g2.setBackground(color);
    	g2.fill(shape);
    	g2.setColor(Color.BLACK);
    	g2.draw(shape);
    	g2.setTransform(prev);
    	g2.setColor(prevColor);
    }
}