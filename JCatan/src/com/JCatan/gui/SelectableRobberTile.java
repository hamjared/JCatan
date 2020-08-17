package com.JCatan.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class SelectableRobberTile {
	Shape circle;
	Point p;
	public SelectableRobberTile(double x, double y, double size) {
		this.circle = new Ellipse2D.Double(x,y,size,size);
		p = new Point((int)x,(int)y);
	}
	
	public void drawRobberPosition(Graphics2D g2) {
		g2.draw(circle);
	}
	
	public Shape getCircle() {
		return circle;
	}
	
	public void onClick(RobberShape robber) {
		robber.setPoint(p);
	}
}