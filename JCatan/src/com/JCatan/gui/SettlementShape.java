package com.JCatan.gui;

import java.awt.geom.Path2D;

public class SettlementShape extends Path2D.Double{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1647183225138303945L;

	public SettlementShape() {
	       moveTo(1,4);
	       lineTo(1,2);
	       lineTo(0,2);
	       lineTo(2,0);
	       lineTo(4,2);
	       lineTo(3,2);
	       lineTo(3,4);
	       lineTo(1,4);
    }
	
	public SettlementShape(double x, double y, double width, double height) {
	       moveTo(width/4, height);
	       lineTo(width/4, height/2);
	       lineTo(0,height/2);
	       lineTo(width/2, 0);
	       lineTo(width, height/2);
	       lineTo(3*width/4, height/2);
	       lineTo(3*width/4, height);
	       lineTo(width/4, height);

 }
}
