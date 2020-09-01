package com.JCatan.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import com.JCatan.InsufficientResourceCardException;
import com.JCatan.Node;
import com.JCatan.ResourceCard;
import com.JCatan.ResourceType;
import com.JCatan.server.Message;
import com.JCatan.server.MessageBuilder;

public class BuildableNode {

	Shape circle;
	Graphics2D g2;
	Node node;

	public BuildableNode(double x, double y, Graphics2D g2, Node node) {
		this.g2 = g2;
		this.node = node;
		createShape(x, y);
	}

	private void createShape(double x, double y) {
		this.circle = new Ellipse2D.Double(x - 10, y - 10, 20, 20);
		g2.draw(circle);

	}

	public Shape getCircle() {
		return circle;
	}

	public void onclick() {

		try {

			GameGUI.controller.getCurPlayer().buildSettlement(GameGUI.controller.getGamePhase(), node,
					GameGUI.controller);
			System.out.println("From building players onClick");
			System.out.println(GameGUI.controller.getBoard());
			System.out.println("------------------------------");
			Message msg = new MessageBuilder().action(Message.Action.BuildSettlement)
					.player(GameGUI.controller.getCurPlayer()).buildOnNode(node).build();
			GameGUI.gameClient.sendMessage(msg);
			GameGUI.ResourcePanel.repaint();
			GameGUI.Player1Panel.repaint();
			GameGUI.Player2Panel.repaint();
			GameGUI.Player3Panel.repaint();
			GameGUI.Player4Panel.repaint();
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
