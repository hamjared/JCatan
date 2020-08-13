package com.JCatan.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import com.JCatan.InsufficientResourceCardException;
import com.JCatan.ResourceCard;
import com.JCatan.ResourceType;
import com.JCatan.Road;

public class BuildableRoad {

	Shape circle;
	Graphics2D g2;
	Road road;

	public BuildableRoad(double x, double y, Graphics2D g2, Road road) {
		this.g2 = g2;
		this.road = road;
		drawRoad(x, y, g2);
	}

	private void drawRoad(double x, double y, Graphics2D g22) {
		this.circle = new Ellipse2D.Double(x - 10, y - 10, 20, 20);
		g2.draw(circle);

	}

	public Shape getCircle() {
		// TODO BuildableRoad: getCircle
		return circle;
	}

	public void onclick() {
		System.out.println("Road clicked: " + road.getNode1().getNodeIndex() + ", " + road.getNode2().getNodeIndex());
		List<ResourceCard> res = new ArrayList<>();
		res.add(new ResourceCard(ResourceType.WOOD));
		res.add(new ResourceCard(ResourceType.BRICK));
		GameGUI.controller.getCurPlayer().setResources(res);
		try {
			GameGUI.controller.getCurPlayer().buildRoad(GameGUI.controller.getGamePhase(), road.getNode1(),
					road.getNode2(), GameGUI.controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
