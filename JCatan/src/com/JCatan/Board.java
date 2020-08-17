package com.JCatan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.JCatan.gui.GameGUI;

public class Board {

	BoardGraph board;
	Map<Integer, List<Tile>> diceRollToTiles;
	List<Tile> tiles;

	public List<Tile> getTiles() {
		return tiles;
	}

	/**
	 * @param bf
	 */
	public Board(BoardFactory bf) {
		board = bf.getBoard();
		tiles = bf.getTiles();
		diceRollToTiles = bf.createDiceRollToTiles();

	}

	/**
	 * @param player
	 * @param nodeIndex
	 */
	public void buildCity(Player player, int nodeIndex) {

	}

	/**
	 * @param player
	 * @param nodeFromIndex
	 * @param nodeToIndex
	 */
	public void buildRoad(Player player, int nodeFromIndex, int nodeToIndex) {

	}

	/**
	 * @param player
	 * @param nodeIndex
	 */
	public void buildSettlement(Player player, int nodeIndex) {

	}

	/**
	 * @param diceRoll
	 */
	public void dishOutResources(GameController controller, int diceRoll) {
		List<Tile> tiles = diceRollToTiles.get(diceRoll);
		for (Tile tile : tiles) {
			List<Building> buildings = tile.getBuildings();
			for (Building b : buildings) {
				if (b != null) {
					b.gatherResources(controller, tile.getResourceType());
				}
			}
		}

	}

	/**
	 * @return
	 */
	public Player largestArmy() {
		return null;
	}

	/**
	 * @return
	 */
	public Player longestRoad() {
		return null;

	}

	public BoardGraph getBoard() {
		return board;
	}

	public void setBoard(BoardGraph board) {
		this.board = board;
	}

	public List<Node> getBuildableNodes(Player curPlayer, GamePhase gamePhase) {
		List<Node> playableNodes = new ArrayList<>();
		int curNodeIndex = 0;
		for (List<Node> nodes : board.getNodes()) {
			Node curNode = board.getNodeList().get(curNodeIndex);
			if (curNode.getBuilding() != null) {
				curNodeIndex++;
				continue;
			}
			if (gamePhase.equals(GamePhase.SETUP)) {
				int i = 0;
				for (i = 0; i < nodes.size(); i++) {
					if (nodes.get(i).getBuilding() != null) {
						break;
					}
				}
				if (i == (nodes.size())) {
					playableNodes.add(curNode);
				}
				curNodeIndex++;
				continue;
			}
			for (Road road : curNode.getRoads()) {
				if (road.getPlayer().equals(curPlayer)) {
					int i = 0;
					for (i = 0; i < nodes.size(); i++) {
						if (nodes.get(i).getBuilding() != null) {
							break;
						}
					}
					if (i == (nodes.size())) {
						playableNodes.add(curNode);
					}
				}

			}

			curNodeIndex++;
		}
		System.out.println(playableNodes.size());
		return playableNodes;
	}

	public List<Road> getBuildableRoads(Player curPlayer) {
		List<Road> roads = new ArrayList<>();
		int curNodeIndex = 0;
		for (List<Node> nodes : board.getNodes()) {
			for (Node node : nodes) {
				Road road = new Road(node, board.getNodeList().get(curNodeIndex), curPlayer);

				boolean playerCanPlayRoad = false;
				if (road.getNode1().getBuilding() != null) {
					if (road.getNode1().getBuilding().getPlayer().equals(curPlayer)) {
						playerCanPlayRoad = true;
					}

					if (road.getNode1().getBuilding().getPlayer().equals(curPlayer)) {
						for (Road r : road.getNode1().getRoads()) {
							if (r != null) {
								if (r.getPlayer().equals(curPlayer)) {
									playerCanPlayRoad = true;
								}
							}
						}
					}

				}
				if (road.getNode2().getBuilding() != null) {
					if (road.getNode2().getBuilding().getPlayer().equals(curPlayer)) {
						playerCanPlayRoad = true;
					}

					if (road.getNode2().getBuilding().getPlayer().equals(curPlayer)) {
						for (Road r : road.getNode2().getRoads()) {
							if (r != null) {
								if (r.getPlayer().equals(curPlayer)) {
									playerCanPlayRoad = true;
								}
							}
						}
					}

				}

				for (Road r : node.getRoads()) {
					if (r.getPlayer().equals(curPlayer)) {
						if (r.getNode1().getBuilding() != null) {
							if (r.getNode1().getBuilding().getPlayer().equals(curPlayer)) {
								if (r.getNode1().equals(road.getNode1())) {
									playerCanPlayRoad = true;
								}
								if (r.getNode1().equals(road.getNode2())) {
									playerCanPlayRoad = true;
								}
							}
						} else {
							if (r.getNode1().equals(road.getNode1())) {
								playerCanPlayRoad = true;
							}
							if (r.getNode1().equals(road.getNode2())) {
								playerCanPlayRoad = true;
							}
						}
						if (r.getNode2().getBuilding() != null) {
							if (r.getNode2().getBuilding().getPlayer().equals(curPlayer)) {
								if (r.getNode2().equals(road.getNode1())) {
									playerCanPlayRoad = true;
								}
								if (r.getNode2().equals(road.getNode2())) {
									playerCanPlayRoad = true;
								}
							}
						} else {
							if (r.getNode2().equals(road.getNode1())) {
								playerCanPlayRoad = true;
							}
							if (r.getNode2().equals(road.getNode2())) {
								playerCanPlayRoad = true;
							}
						}
					}
				}

				for (Road r : board.getNodeList().get(curNodeIndex).getRoads()) {
					if (r.getPlayer().equals(curPlayer)) {
						if (r.getNode1().getBuilding() != null) {
							if (r.getNode1().getBuilding().getPlayer().equals(curPlayer)) {
								if (r.getNode1().equals(road.getNode1())) {
									playerCanPlayRoad = true;
								}
								if (r.getNode1().equals(road.getNode2())) {
									playerCanPlayRoad = true;
								}
							}
						} else {
							if (r.getNode1().equals(road.getNode1())) {
								playerCanPlayRoad = true;
							}
							if (r.getNode1().equals(road.getNode2())) {
								playerCanPlayRoad = true;
							}
						}
						if (r.getNode2().getBuilding() != null) {
							if (r.getNode2().getBuilding().getPlayer().equals(curPlayer)) {
								if (r.getNode2().equals(road.getNode1())) {
									playerCanPlayRoad = true;
								}
								if (r.getNode2().equals(road.getNode2())) {
									playerCanPlayRoad = true;
								}
							}
						} else {
							if (r.getNode2().equals(road.getNode1())) {
								playerCanPlayRoad = true;
							}
							if (r.getNode2().equals(road.getNode2())) {
								playerCanPlayRoad = true;
							}
						}
					}
				}

				if (!roads.contains(road) && playerCanPlayRoad) {
					roads.add(road);
				}

			}

			curNodeIndex++;
		}

		return roads;
	}

	public List<Node> getBuildableCities(Player curPlayer) {
		List<Node> nodes = new ArrayList<>();
		for (Node node : board.getNodeList()) {
			if (node.getBuilding() != null) {
				if (node.getBuilding().getPlayer().equals(curPlayer)) {
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	public boolean isRobberMoving() {
		return GamePhase.ROBBERMOVE == GameGUI.controller.gamePhase;
	}
}