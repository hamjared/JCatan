package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.JCatan.Dice;
import com.JCatan.GamePhase;
import com.JCatan.ResourceCard;
import com.JCatan.server.Message;
import com.JCatan.server.MessageBuilder;

public class ResourcesPanel extends JPanel {

	ImageIcon brick = new ImageIcon("images/brick.jpg");
	ImageIcon ore = new ImageIcon("images/ore.jpg");
	ImageIcon sheep = new ImageIcon("images/sheep.jpg");
	ImageIcon wheat = new ImageIcon("images/wheat.jpg");
	ImageIcon wood = new ImageIcon("images/wood.jpg");
	Image resourceCard;
	int j = 0;
	List<Rectangle> recs;

	public ResourcesPanel() {
		recs = new ArrayList<>();
		setBounds(0, 867, 750, 134);
		setBackground(Color.decode("#CC9966"));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (GameGUI.controller.getCurPlayer().getDiceRoll() == 7 && GameGUI.myPlayer.getCardsToDrop() > 0) {
					int x = e.getX();
					int y = e.getY();
					int cardSpotCheck = 0;
					for (Rectangle rec : recs) {
						if (rec.contains(x, y)) {
							ResourceCard card = GameGUI.myPlayer.getResources().get(cardSpotCheck);
							onClick(card);
							repaint();
							break;
						}
						cardSpotCheck++;
					}
				}
			}
		});

	}

	public void onClick(ResourceCard card) {
		Message msg = new MessageBuilder().action(Message.Action.DropCardsOnSeven).player(GameGUI.myPlayer).dropCardOnSevenRolled(card).build();
		GameGUI.gameClient.sendMessage(msg);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			recs.clear();
			for (int i = 0; i < GameGUI.myPlayer.getResources().size(); i++) {
				ResourceCard resource = GameGUI.myPlayer.getResources().get(i);
				switch (resource.getResourceType()) {
				case WOOD:
					resourceCard = wood.getImage();
					break;
				case SHEEP:
					resourceCard = sheep.getImage();
					break;
				case ORE:
					resourceCard = ore.getImage();
					break;
				case BRICK:
					resourceCard = brick.getImage();
					break;
				case WHEAT:
					resourceCard = wheat.getImage();
					break;
				default:
					break;
				}
				g.drawImage(resourceCard, j + 5, 18, 55, 95, null);
				Rectangle imageBounds = new Rectangle(j + +5, 18, 55, 95);
				recs.add(imageBounds);
				j += 56;
			}
			j = 0;
		} catch (Exception e) {

		}
	}
}
