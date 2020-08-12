package com.JCatan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


}
