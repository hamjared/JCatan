package com.JCatan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LongestRoadCalculator {

	private List<Road> roads;


	public LongestRoadCalculator(List<Road> roads) {

		this.roads = roads;
	}

	public int calcLongestRoad() {
		int longestLength = 0;
		int curLength = 0;
		for (Road road : roads) {

			curLength = followRoad(road);
			if (curLength > longestLength) {
				longestLength = curLength;

			}

		}

		return longestLength;
	}

	private int followRoad(Road road) {
		Node node1 = road.getNode1();
		Node node2 = road.getNode2();
		Set<Road> traveledRoads = new HashSet<Road>();

		int l1 = dfs(node1, traveledRoads, 0);
		int l2 = dfs(node2, traveledRoads, 0);
		return l1 + l2;

	}

	private int dfs(Node node, Set<Road> traveledRoads, int length) {

		List<Road> roads = new ArrayList<>(node.getRoads());
		roads.removeIf(r -> traveledRoads.contains(r));
		if (roads.isEmpty()) {
			return length;
		} else if (roads.size() == 1) {
			Road road = roads.get(0);
			traveledRoads.add(road);
			Node node1 = road.getNode1();
			Node node2 = road.getNode2();
			return dfs(node == node1 ? node2 : node1, traveledRoads, length + 1);
		} else if (roads.size() == 2) {
			Road road = roads.get(0);
			traveledRoads.add(road);
			Node node11 = road.getNode1();
			Node node12 = road.getNode2();
			Road road2 = roads.get(1);
			traveledRoads.add(road2);
			Node node21 = road2.getNode1();
			Node node22 = road2.getNode2();
			return Math.max(dfs(node == node11 ? node12 : node11, traveledRoads, length + 1),
					dfs(node == node21 ? node22 : node21, traveledRoads, length + 1));
		} else if (roads.size() == 3) {
			Road road = roads.get(0);
			traveledRoads.add(road);
			Node node11 = road.getNode1();
			Node node12 = road.getNode2();
			Road road2 = roads.get(1);
			traveledRoads.add(road2);
			Node node21 = road2.getNode1();
			Node node22 = road2.getNode2();
			Road road3 = roads.get(2);
			traveledRoads.add(road3);
			Node node31 = road3.getNode1();
			Node node32 = road3.getNode2();
			return Math.max(
					Math.max(dfs(node == node11 ? node12 : node11, traveledRoads, length + 1),
							dfs(node == node21 ? node22 : node21, traveledRoads, length + 1)),
					dfs(node == node31 ? node32 : node31, traveledRoads, length + 1));
		} else {
			return length;
		}
	}

	public static void main(String[] args) {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		Road road10_14 = new Road(board.board.getNodeList().get(10), board.board.getNodeList().get(14), player);
		board.board.getNodeList().get(14).addRoad(road10_14);
		board.board.getNodeList().get(10).addRoad(road10_14);
		player.addRoadToRoadPlayedList(road10_14);

		Road road14_19 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(14).addRoad(road14_19);
		board.board.getNodeList().get(19).addRoad(road14_19);
		player.addRoadToRoadPlayedList(road14_19);

		Road road14_9 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(9), player);
		board.board.getNodeList().get(14).addRoad(road14_9);
		board.board.getNodeList().get(19).addRoad(road14_9);
		player.addRoadToRoadPlayedList(road14_9);

		Road road19_24 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(19).addRoad(road19_24);
		board.board.getNodeList().get(24).addRoad(road19_24);
		player.addRoadToRoadPlayedList(road19_24);

		Road road24_30 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(30), player);
		board.board.getNodeList().get(30).addRoad(road24_30);
		board.board.getNodeList().get(24).addRoad(road24_30);
		player.addRoadToRoadPlayedList(road24_30);

		Road road18_24 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(18), player);
		board.board.getNodeList().get(18).addRoad(road18_24);
		board.board.getNodeList().get(24).addRoad(road18_24);
		player.addRoadToRoadPlayedList(road18_24);

		Road road19_25 = new Road(board.board.getNodeList().get(19), board.board.getNodeList().get(25), player);
		board.board.getNodeList().get(19).addRoad(road19_25);
		board.board.getNodeList().get(25).addRoad(road19_25);
		player.addRoadToRoadPlayedList(road19_25);

		Road road17_22 = new Road(board.board.getNodeList().get(22), board.board.getNodeList().get(17), player);
		board.board.getNodeList().get(22).addRoad(road17_22);
		board.board.getNodeList().get(17).addRoad(road17_22);
		player.addRoadToRoadPlayedList(road17_22);

		Road road17_23 = new Road(board.board.getNodeList().get(23), board.board.getNodeList().get(17), player);
		board.board.getNodeList().get(23).addRoad(road17_23);
		board.board.getNodeList().get(17).addRoad(road17_23);
		player.addRoadToRoadPlayedList(road17_23);

		Road road18_23 = new Road(board.board.getNodeList().get(23), board.board.getNodeList().get(18), player);
		board.board.getNodeList().get(23).addRoad(road18_23);
		board.board.getNodeList().get(18).addRoad(road18_23);
		player.addRoadToRoadPlayedList(road18_23);

		int lr = player.calcLongestRoad();
		System.out.println(lr);
	}
}
