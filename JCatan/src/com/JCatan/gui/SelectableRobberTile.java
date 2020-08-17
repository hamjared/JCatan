package com.JCatan.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class SelectableRobberTile {
	Shape circle;
	
	public SelectableRobberTile(double x, double y, double size) {
		this.circle = new Ellipse2D.Double(x,y,size,size);
	}
	
	public void drawRobberPosition(Graphics2D g2) {
		g2.draw(circle);
	}
	
	public Shape getCircle() {
		return circle;
	}
	
	public void onClick() {
		System.out.println("Robber tile clicked");
	}
}