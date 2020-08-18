package com.JCatan.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import com.JCatan.Tile;

public class SelectableRobberTile {
	Shape circle;
	Point p;
	Tile tile;
	public SelectableRobberTile(double x, double y, double size, Tile tile) {
		this.circle = new Ellipse2D.Double(x,y,size,size);
		p = new Point((int)x,(int)y);
		this.tile = tile;
	}
	
	public void drawRobberPosition(Graphics2D g2) {
		g2.draw(circle);
	}
	
	public Shape getCircle() {
		return circle;
	}
	
	public void onClick(RobberShape robber) {
		robber.setPoint(p);
		GameGUI.controller.getRobber().move(tile);
	}
}