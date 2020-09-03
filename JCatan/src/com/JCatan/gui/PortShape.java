package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import com.JCatan.Port;
import com.JCatan.Port.PortType;
import com.JCatan.PortNode;

public class PortShape {

	Graphics2D g2;
	Ellipse2D portShape;
	Port port;

	public PortShape(int x, int y, Graphics2D g2, int portIndex) {
		PortNode pn = (PortNode) GameGUI.controller.getBoard().getBoard().getNodeList().get(portIndex);
		this.port = pn.getPort();
		this.g2 = g2;
		portShape = new Ellipse2D.Double(x, y, 30, 30);
	}

	public void draw() {
		Color prevColor = g2.getColor();
		setColor();
		AffineTransform prev = g2.getTransform();
		g2.translate(-15, -15);
		g2.fill(portShape);
		g2.setTransform(prev);
		g2.setColor(prevColor);
	}

	private void setColor() {

		if (port.getPortType() == PortType.GENERIC) {
			g2.setColor(Color.decode("#000000"));
		} else {
			switch (port.getResourceType()) {
			case BRICK:
				g2.setColor(Color.decode("#663300"));
				break;
			case WHEAT:
				g2.setColor(Color.decode("#FFD700"));
				break;
			case WOOD:
				g2.setColor(Color.decode("#003300"));
				break;
			case SHEEP:
				g2.setColor(Color.decode("#99FF33"));
				break;
			case ORE:
				g2.setColor(Color.decode("#4C0099"));
				break;
			}
		}

	}

}
