package com.JCatan;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
	
	Building building;
	List<Road> roads;
	List<Tile> tiles;
	
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

    /**
	 * @param road
	 */
	public void addRoad(Road road) {
	    //TODO Node: addRoad
		
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
	

}
