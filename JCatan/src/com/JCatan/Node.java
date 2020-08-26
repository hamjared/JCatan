package com.JCatan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Node implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Building building;
	List<Road> roads;
	List<Tile> tiles;
	private int nodeIndex;
	
	public int getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	/**
	 * 
	 */
	public Node() {
		building = null;
		roads = new ArrayList<Road>();
		tiles = new ArrayList<Tile>();
	}
	
	public Building getBuilding()
    {
        return building;
    }
	
	

    public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * @param road
	 */
	public void addRoad(Road road) {
		roads.add(road);
	}
	
	/**
	 * @param road
	 */
	public void removeRoad(Road road) {
	    //TODO Node: removeRoad
		
	}

    public List<Tile> getTiles()
    {
        return tiles;
    }

    public void setTiles(List<Tile> tiles)
    {
        this.tiles = tiles;
    }

	public List<Road> getRoads() {
		return roads;
	}
	
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Node)) {
			return false;
		}
		Node node = (Node) obj;
		return node.getNodeIndex() == this.getNodeIndex();
	}
	

}
