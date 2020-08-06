package com.JCatan;

import java.util.List;
import java.util.Map;

public class Board {
	
	BoardGraph board;
	Map<Integer, List<Tile>> diceRollToTiles;
	List<Tile> tiles;
	
	
	public List<Tile> getTiles()
    {
        return tiles;
    }

    /**
	 * @param bf
	 */
	public Board(BoardFactory bf) {
		board = bf.getBoard();
		diceRollToTiles = bf.createDiceRollToTiles();
		tiles = bf.getTiles();
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
	public void diceRolled(int diceRoll) {
	    List<Tile> tiles = diceRollToTiles.get(diceRoll);
	    for(Tile tile: tiles) {
	        List<Building> buildings = tile.getBuildings();
	        buildings.forEach(b -> b.gatherResources(tile.getResourceType()));
	    }
	    
	}
	
	/**
	 * @param diceRoll
	 */
	public void dishOutResources(int diceRoll) {
	    
		
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

}
