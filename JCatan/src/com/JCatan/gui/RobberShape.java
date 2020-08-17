package com.JCatan.gui;

import java.awt.Point;
import java.awt.geom.Path2D;

public class RobberShape extends Path2D.Double {
private static final long serialVersionUID = -2383215309053719082L;
	Point p;
	
	public RobberShape() {
		moveTo(1,5);
		lineTo(1,2);
		lineTo(0,2);
		lineTo(2,0);
		lineTo(4,2);
		lineTo(3,2);
		lineTo(3,3);
		lineTo(5,3);
		lineTo(5,5);
		lineTo(1,5);
		p = new Point();
	}
	
	public void setPoint(int x, int y) {
		p = new Point(x, y);
	}
	
	public void setPoint(Point p) {
		this.p = p;
	}
	
	public Point getPoint() {
		return p;
	}
	
	public RobberShape(double width, double height) {
		moveTo(width/4, height);
		lineTo(width/4, height/2);
		lineTo(0, height/2);
		lineTo(width/3, 0);
		lineTo(7*width/9, height/2);
		lineTo(width/2, height/2);
		lineTo(width/2, 7*height/9);
		lineTo(width, 7*height/9);
		lineTo(width, height);
		lineTo(width/4,height);
		p = new Point();
	}
}
