package com.JCatan.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


import com.JCatan.PortNode;
import com.JCatan.ResourceType;
import com.JCatan.Tile;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;

public class BoardPanel extends JPanel {


	private List<Hexagon> hexagons;

	public BoardPanel() {
		super();
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				handleMouseMoved(e.getX(), e.getY());
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX());
				System.out.println(e.getY());
				int x = e.getX();
				int y = e.getY();

				for (Hexagon hex : hexagons) {
					if (hex.contains(x, y)) {
						hex.onClick();
						break;
					}
				}
			}
		});
		setBackground(Color.BLUE);
		setBounds(0, 0, 1441, 867);
		hexagons = new ArrayList<>();
	}

	protected void handleMouseMoved(int x, int y) {

		for (Hexagon hex : hexagons) {
			if (hex.contains(x, y)) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				break;
			} else {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1441;
	private final int HEIGHT = 867;

	private final int W2 = WIDTH / 2;
	private final int H2 = HEIGHT / 2;

	private Font font = new Font("Arial", Font.BOLD, 24);
	FontMetrics metrics;

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
		g2d.setFont(font);
		metrics = g.getFontMetrics();

		drawCircle(g2d, W2, H2, 10000, true, true, 0x4488FF, 0);

		drawHexGridAdvanced(g2d, 5, 85);

		drawPorts(g2d);
	}

	private void drawPorts(Graphics2D g2) {

		Color prevColor = g2.getColor();
		g2.setColor(Color.BLACK);

		g2.draw(new Line2D.Double(498, 134, 517, 88));
		g2.draw(new Line2D.Double(571, 92, 517, 88));
		new PortShape(517, 88, g2, 0).draw();

		g2.draw(new Line2D.Double(720, 93, 777, 90));
		g2.draw(new Line2D.Double(792, 133, 777, 90));
		new PortShape(777, 90, g2, 1).draw();

		g2.draw(new Line2D.Double(940, 220, 996, 197));
		g2.draw(new Line2D.Double(1013, 262, 996, 197));
		new PortShape(996, 197, g2, 10).draw();

		g2.draw(new Line2D.Double(1087, 390, 1127, 431));
		g2.draw(new Line2D.Double(1086, 473, 1127, 431));
		new PortShape(1127, 431, g2, 26).draw();

		g2.draw(new Line2D.Double(1012, 602, 999, 664));
		g2.draw(new Line2D.Double(938, 642, 999, 664));
		new PortShape(999, 664, g2, 42).draw();

		g2.draw(new Line2D.Double(791, 729, 781, 787));
		g2.draw(new Line2D.Double(721, 773, 781, 787));
		new PortShape(781, 787, g2, 49).draw();

		g2.draw(new Line2D.Double(573, 772, 508, 795));
		g2.draw(new Line2D.Double(499, 729, 508, 795));
		new PortShape(508, 795, g2, 51).draw();

		g2.draw(new Line2D.Double(424, 517, 376, 559));
		g2.draw(new Line2D.Double(424, 600, 376, 559));
		new PortShape(376, 559, g2, 38).draw();

		g2.draw(new Line2D.Double(424, 347, 384, 305));
		g2.draw(new Line2D.Double(424, 261, 384, 305));
		new PortShape(384, 305, g2, 16).draw();

		g2.setColor(prevColor);

	}

	private void drawHexGridAdvanced(Graphics g, int n, int r) {
		double ang30 = Math.toRadians(30);
		double xOff = Math.cos(ang30) * r;
		double yOff = Math.sin(ang30) * r;
		int h = n / 2;
		int tileIndex = 0;
		List<Tile> tiles = GameGUI.controller.getBoard().getTiles();

		for (int row = 0; row < 5; row++) {
			int cols = 0;
			switch (row) {
			case (0):
			case (4):
				cols = 3;
				break;
			case (1):
			case (3):
				cols = 4;
				break;
			case (2):
				cols = 5;
				break;

			}
			for (int col = 0; col < cols; col++) {

				if (row < 2) {
					drawHex(g, col - row, -h + row, (int) (W2 + xOff * (-cols + (col * 2 + 1))),
							(int) (H2 - yOff * (n - cols) * 3), r, tiles.get(tileIndex),
							"" + tiles.get(tileIndex).getNumber());
				} else if (row == 2) {
					drawHex(g, col - row, -h + row, (int) (W2 + xOff * (-cols + (col * 2 + 1))),
							(int) (H2 + yOff * (n - cols) * 3), r, tiles.get(tileIndex),
							"" + tiles.get(tileIndex).getNumber());
				} else if (row == 3) {
					drawHex(g, col - row + 1, -h + row, (int) (W2 + xOff * (-cols + (col * 2 + 1))),
							(int) (H2 + yOff * (n - cols) * 3), r, tiles.get(tileIndex),
							"" + tiles.get(tileIndex).getNumber());
				} else if (row == 4) {
					drawHex(g, col - row + 2, -h + row, (int) (W2 + xOff * (-cols + (col * 2 + 1))),
							(int) (H2 + yOff * (n - cols) * 3), r, tiles.get(tileIndex),
							"" + tiles.get(tileIndex).getNumber());
				}

				tileIndex++;
			}

		}

	}

	private void drawHex(Graphics g, int posX, int posY, int x, int y, int r, Tile tile, String text) {
		Hexagon hex = new Hexagon(x, y, r, tile);
		hexagons.add(hex);

		int w = metrics.stringWidth(text);
		int h = metrics.getHeight();

		g.setColor(tile.getColor());
		g.fillPolygon(hex);
		g.setColor(new Color(0x000000));
		g.drawPolygon(hex);
		g.setColor(new Color(0xFFFFFF));
		g.drawString(text, x - w / 2, y + h / 2);
	}

	private String coord(int value) {
		return (value > 0 ? "+" : "") + Integer.toString(value);
	}

	public void drawCircle(Graphics2D g, int x, int y, int diameter, boolean centered, boolean filled, int colorValue,
			int lineThickness) {
		drawOval(g, x, y, diameter, diameter, centered, filled, colorValue, lineThickness);
	}

	public void drawOval(Graphics2D g, int x, int y, int width, int height, boolean centered, boolean filled,
			int colorValue, int lineThickness) {
		// Store before changing.
		Stroke tmpS = g.getStroke();
		Color tmpC = g.getColor();

		g.setColor(new Color(colorValue));
		g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		int x2 = centered ? x - (width / 2) : x;
		int y2 = centered ? y - (height / 2) : y;

		if (filled)
			g.fillOval(x2, y2, width, height);
		else
			g.drawOval(x2, y2, width, height);

		// Set values to previous when done.
		g.setColor(tmpC);
		g.setStroke(tmpS);
	}

}
