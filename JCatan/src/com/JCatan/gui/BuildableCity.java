package com.JCatan.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import com.JCatan.City;
import com.JCatan.InsufficientResourceCardException;
import com.JCatan.Node;
import com.JCatan.ResourceCard;
import com.JCatan.ResourceType;
import com.JCatan.Road;

public class BuildableCity {
	Shape circle;
	Graphics2D g2;
	Node node;
	
	public BuildableCity(double x, double y,  Graphics2D g2, Node node) {
		this.g2 = g2;
		this.node = node;
		drawRoad(x,y,g2);
	}

	private void drawRoad(double x, double y, Graphics2D g22) {
		this.circle = new Ellipse2D.Double(x - 25, y - 25, 50, 50);
		g2.draw(circle);
		
	}

	public Shape getCircle() {
		return circle;
	}

	public void onclick() {
		System.out.println("Clicked to Build City: " + node.getNodeIndex());
		try {
			GameGUI.controller.getCurPlayer().buildCity(node, GameGUI.controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
