package com.JCatan.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;

import javax.swing.JPanel;

import com.JCatan.GameController;
import com.JCatan.InsufficientResourceCardException;
import com.JCatan.Node;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildingPanel extends JPanel {

	Rectangle2D.Double rect;
	SettlementShape settlement;
	CityShape city;
	Graphics2D g2;
	List<Shape> shapes;
	Color c;
	BufferedImage img;
	List<ActionListener> actionListeners;

	public BuildingPanel(GameController controller) {
		setBackground(Color.PINK);
		setBounds(872, 867, 442, 134);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				buyDevCard();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
			}
		});

		rect = new Rectangle2D.Double(0, 0, 10, 30);
		settlement = new SettlementShape(0, 0, 30, 30);
		city = new CityShape(30, 30);

		shapes = new ArrayList<>();
		shapes.add(rect);
		shapes.add(settlement);
		shapes.add(city);

		// opacityCheck(controller);

		

		panel.setBounds(339, 11, 93, 112);
		add(panel);
		panel.setLayout(null);

		try {
			img = ImageIO.read(new File("images/dcs__back.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		drawShape(rect, 0, 30, 20, 3, g2, Color.BLACK);
		drawShape(settlement, 0, 100, 20, 3, g2, Color.BLACK);
		drawShape(city, 0, 220, 20, 3, g2, Color.BLACK);

		g.drawImage(img, 339, 11, 93, 112, null);
	}

	private void drawShape(Shape shape, int rotateDegrees, int translate_x, int translate_y, int scale, Graphics2D g2,
			Color color) {
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

	protected void buyDevCard() {
		try {
			GameGUI.controller.getCurPlayer().buyDevelopmentCard(GameGUI.controller);
			GameGUI.controller.getChat().addToChat(GameGUI.controller.getCurPlayer().getName() + " Bought a dev card");
			this.getParent().repaint();
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			GameGUI.controller.getChat().addToChat("Cannot buy development card - insufficient resources");
			this.getParent().repaint();
		}

	}

	public void addActionListener(ActionListener al) {
		actionListeners.add(al);
	}

}
