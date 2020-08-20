package com.JCatan.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;

import com.JCatan.City;
import com.JCatan.GamePhase;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.PortNode;
import com.JCatan.ResourceType;
import com.JCatan.Road;
import com.JCatan.Settlement;
import com.JCatan.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;

public class BoardPanel extends JPanel {

	private List<Hexagon> hexagons;
	private List<Node> nodes;
	private List<BuildableNode> buildableNodes;
	private List<Road> roads;
	private List<BuildableRoad> buildableRoads;
	private List<Node> cities;
	private List<BuildableCity> buildableCities;
	private boolean drawRoads = false;
	private boolean drawSettlements = false;
	private boolean drawCities = false;
	private Map<Tile, Hexagon> tileToHexagon;
	private List<SelectableRobberTile> robberTiles;
	private RobberShape robber;
	private boolean initialized = false;

	public BoardPanel() {
		super();
		buildableNodes = new ArrayList<>();
		buildableRoads = new ArrayList<>();
		buildableCities = new ArrayList<>();
		tileToHexagon = new HashMap<>();
		robberTiles = new ArrayList<>();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				for (BuildableNode circle : buildableNodes) {
					if (circle.getCircle().contains(x, y)) {
						circle.onclick();
						drawSettlements = false;
						GameGUI.endButton.setEnabled(true);
						repaint();
						break;
					}

				}

				for (BuildableRoad circle : buildableRoads) {
					if (circle.getCircle().contains(x, y)) {
						circle.onclick();
						drawRoads = false;
						if(GameGUI.controller.getCurPlayer().getRoadBuilderRoads() > 0) {
							buildRoad();
						}
						GameGUI.endButton.setEnabled(true);
						repaint();
						break;
					}

				}

				for (BuildableCity circle : buildableCities) {
					if (circle.getCircle().contains(x, y)) {
						circle.onclick();
						drawCities = false;
						GameGUI.endButton.setEnabled(true);
						repaint();
						break;
					}
				}

				if (GameGUI.controller.getBoard().isRobberMoving()) {
					for (SelectableRobberTile circle : robberTiles) {
						if (circle.getCircle().contains(x, y)) {
							circle.onClick(robber);
							GameGUI.controller.setGamePhase(GamePhase.GAMEMAIN);
							GameGUI.endButton.setText("End Turn");
							getParent().repaint();
							break;
						}
					}
				}
			}
		});
		setBackground(Color.BLUE);
		setBounds(0, 0, 1441, 867);
		hexagons = new ArrayList<>();
		robber = new RobberShape(40,40);
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

		long start = System.nanoTime();
		drawHexGridAdvanced(g2d, 5, 85);
		if(!initialized) {
			Hexagon hex = hexagons.stream().filter(h -> h.getTile().getResourceType() == ResourceType.DESERT).findFirst().get();
			robber.setPoint(hex.getCenter().x - 15, hex.getCenter().y - 20);
			initialized = true;
		}
		long end = System.nanoTime();
		System.out.println("Time to draw hex grid: " + (end - start) / (10e9));

		start = System.nanoTime();
		drawPorts(g2d);
		end = System.nanoTime();
		System.out.println("Time to draw Ports: " + (end - start) / (10e9));

		start = System.nanoTime();
		drawBuildingNodes(g2d);
		end = System.nanoTime();
		System.out.println("Time to draw Buildable Settlements: " + (end - start) / (10e9));

		start = System.nanoTime();
		drawBuildableRoads(g2d);
		end = System.nanoTime();
		System.out.println("Time to draw Buildable ROads: " + (end - start) / (10e9));

		start = System.nanoTime();
		drawBuildableCities(g2d);
		end = System.nanoTime();
		System.out.println("Time to draw Buildable Cities: " + (end - start) / (10e9));

		start = System.nanoTime();
		drawPieces(g2d);
		end = System.nanoTime();
		System.out.println("Time to draw Board pieces: " + (end - start) / (10e9));

		drawValidRobberSpots(g2d, 40.0d);
		System.out.println("---------------END OF PAINT------------------");

	}

	private void drawPieces(Graphics2D g2d) {
		SettlementShape ss = new SettlementShape(0, 0, 40, 40);
		CityShape cs = new CityShape(40, 40);
		long start = System.nanoTime();
		for (Node node : GameGUI.controller.getBoard().getBoard().getNodeList()) {
			if (node.getBuilding() != null) {
				Player player = node.getBuilding().getPlayer();
				if (node.getBuilding() instanceof City) {

					for (Hexagon hex : hexagons) {
						if (hex.getTile().getNodes().contains(node)) {
							int vertex = hex.getTile().getNodes().indexOf(node);
							vertex = vertex - 1;
							if (vertex < 0) {
								vertex = 5;
							}
							Point p = hex.getVertex(vertex);
							AffineTransform prev = g2d.getTransform();
							Color prevColor = g2d.getColor();
							g2d.setColor(node.getBuilding().getPlayer().getColor());
							g2d.translate(p.getX() - 20, p.getY() - 20);
							g2d.fill(cs);
							g2d.setColor(Color.BLACK);
							g2d.draw(cs);
							g2d.setTransform(prev);
							g2d.setColor(prevColor);
						}
					}

				} else if (node.getBuilding() instanceof Settlement) {

					for (Hexagon hex : hexagons) {
						if (hex.getTile().getNodes().contains(node)) {
							int vertex = hex.getTile().getNodes().indexOf(node);
							vertex = vertex - 1;
							if (vertex < 0) {
								vertex = 5;
							}
							Point p = hex.getVertex(vertex);

							AffineTransform prev = g2d.getTransform();
							Color prevColor = g2d.getColor();
							g2d.setColor(node.getBuilding().getPlayer().getColor());
							g2d.translate(p.getX() - 20, p.getY() - 20);
							g2d.fill(ss);
							g2d.setColor(Color.BLACK);
							g2d.draw(ss);
							g2d.setTransform(prev);
							g2d.setColor(prevColor);
						}
					}
				}
			}

		}
		long end = System.nanoTime();
		System.out.println("Time to draw Cities and Settlements: " + (end - start) / 10e9);

		start = System.nanoTime();
		drawRoads(g2d);
		
		AffineTransform prev = g2d.getTransform();
		Color prevColor = g2d.getColor();
		g2d.setColor(Color.BLACK);
		g2d.translate(robber.getPoint().getX(), robber.getPoint().getY());
		g2d.draw(robber);
		g2d.fill(robber);
		g2d.setTransform(prev);
		g2d.setColor(prevColor);
		end = System.nanoTime();
		System.out.println("Time to draw Roads: " + (end - start) / 10e9);

	}

	private void drawRoads(Graphics2D g2d) {
		Node node1;
		Node node2;
		Hexagon hexagon;
		Hexagon hex2;

		for (Player player : GameGUI.controller.getPlayers()) {
			for (Road road : player.getPlayedRoads()) {
				node1 = road.getNode1();
				node2 = road.getNode2();

				hexagon = tileToHexagon.get(node1.getTiles().get(0));
				hex2 = tileToHexagon.get(node2.getTiles().get(0));

				int vertex1 = hexagon.getTile().getNodes().indexOf(node1);
				vertex1 = vertex1 - 1;
				if (vertex1 < 0) {
					vertex1 = 5;
				}
				int vertex2 = hex2.getTile().getNodes().indexOf(node2);
				vertex2 = vertex2 - 1;
				if (vertex2 < 0) {
					vertex2 = 5;
				}

				Point p1 = hexagon.getVertex(vertex1);
				Point p2 = hex2.getVertex(vertex2);

				Shape c = new Ellipse2D.Double((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2, 30, 30);
				AffineTransform prev = g2d.getTransform();
				Color prevColor = g2d.getColor();
				g2d.setColor(road.getPlayer().getColor());
				g2d.translate(-15, -15);
				g2d.fill(c);
				g2d.setColor(Color.BLACK);
				g2d.draw(c);
				g2d.setTransform(prev);
				g2d.setColor(prevColor);
			}
		}
	}

	private void drawBuildableCities(Graphics2D g2) {
		if (cities == null) {
			return;
		}
		buildableCities.clear();
		if (!drawCities) {
			return;
		}

		for (Node node : cities) {
			for (Hexagon hexagon : hexagons) {
				if (hexagon.getTile().getNodes().contains(node)) {
					int vertex = hexagon.getTile().getNodes().indexOf(node);
					vertex = vertex - 1;
					if (vertex < 0) {
						vertex = 5;
					}
					Point p = hexagon.getVertex(vertex);
					buildableCities.add(new BuildableCity(p.getX(), p.getY(), g2, node));

				}
			}
		}

	}

	private void drawBuildableRoads(Graphics2D g2) {
		if (roads == null) {
			return;
		}
		buildableRoads.clear();
		if (!drawRoads) {
			return;
		}

		Node node1;
		Node node2;
		Hexagon hexagon;
		Hexagon hex2;

		for (Road road : roads) {

			node1 = road.getNode1();
			node2 = road.getNode2();

			hexagon = tileToHexagon.get(node1.getTiles().get(0));
			hex2 = tileToHexagon.get(node2.getTiles().get(0));

			int vertex1 = hexagon.getTile().getNodes().indexOf(node1);
			vertex1 = vertex1 - 1;
			if (vertex1 < 0) {
				vertex1 = 5;
			}
			int vertex2 = hex2.getTile().getNodes().indexOf(node2);
			vertex2 = vertex2 - 1;
			if (vertex2 < 0) {
				vertex2 = 5;
			}

			Point p1 = hexagon.getVertex(vertex1);
			Point p2 = hex2.getVertex(vertex2);

			buildableRoads.add(new BuildableRoad((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2, g2, road));

		}
	}

	private void drawBuildingNodes(Graphics2D g2) {
		if (nodes == null) {
			return;
		}
		buildableNodes.clear();
		if (!drawSettlements) {
			return;
		}
		for (Node node : nodes) {
			for (Hexagon hexagon : hexagons) {
				if (hexagon.getTile().getNodes().contains(node)) {
					int vertex = hexagon.getTile().getNodes().indexOf(node);
					vertex = vertex - 1;
					if (vertex < 0) {
						vertex = 5;
					}
					Point p = hexagon.getVertex(vertex);
					buildableNodes.add(new BuildableNode(p.getX(), p.getY(), g2, node));
				}
			}
		}

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
		tileToHexagon.put(tile, hex);

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

	public void buildCity() {
		if (GameGUI.controller.getCurPlayer() == null) {
			return;
		}
		cities = GameGUI.controller.getBoard().getBuildableCities(GameGUI.controller.getCurPlayer());
		drawCities = true;
		drawRoads = false;
		drawSettlements = false;
		this.repaint();

	}

	public void buildSettlement() {
		if (GameGUI.controller.getCurPlayer() == null) {
			return;
		}
		nodes = GameGUI.controller.getBoard().getBuildableNodes(GameGUI.controller.getCurPlayer(),
				GameGUI.controller.getGamePhase());
		drawCities = false;
		drawRoads = false;
		drawSettlements = true;
		repaint();
		revalidate();
	}

	public void buildRoad() {
		if (GameGUI.controller.getCurPlayer() == null) {
			return;
		}
		roads = GameGUI.controller.getBoard().getBuildableRoads(GameGUI.controller.getCurPlayer(), GameGUI.controller.getGamePhase());
		drawCities = false;
		drawRoads = true;
		drawSettlements = false;
		repaint();
		revalidate();
	}

	public void drawValidRobberSpots(Graphics2D g, double diameter) {
		if (GameGUI.controller.getCurPlayer() == null) {
			return;
		}
		
		robberTiles.clear();
		
		if (GameGUI.controller.getBoard().isRobberMoving()) {
			for (Hexagon hex : hexagons) {
				Point center = hex.getCenter();
				SelectableRobberTile tile = new SelectableRobberTile(center.getX() - (diameter / 2), center.getY() - (diameter / 2), diameter, hex.getTile());
				robberTiles.add(tile);
				tile.drawRobberPosition(g);
			}
		}
		revalidate();
	}

	public void setDrawRoads(boolean drawRoads) {
		this.drawRoads = drawRoads;
	}

	public void setDrawSettlements(boolean drawSettlements) {
		this.drawSettlements = drawSettlements;
	}

	public void setDrawCities(boolean drawCities) {
		this.drawCities = drawCities;
	}
}