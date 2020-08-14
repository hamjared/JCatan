package com.JCatan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class RandomBoardFactory extends BoardFactory {
	
	Stack<ResourceType> randomResourceStack;
	Stack<Integer> randomNumberStack;
	Map<Integer, List<Tile>> diceToTiles;

	public RandomBoardFactory() {
		super();
		randomResourceStack = new Stack<>();
		fillRandomResourceStack();
		randomNumberStack = new Stack<>();
		
		fillRandomNumberStack();
		Collections.shuffle(randomResourceStack);
		Collections.shuffle(randomNumberStack);
		diceToTiles = new HashMap<>();
		initializeDiceToTiles();
		
	}

	private void initializeDiceToTiles() {
		for(int i = 2; i < 13; i++) {
			diceToTiles.put(i, new ArrayList<Tile>());
		}
		
	}

	private void fillRandomNumberStack() {
		for(int i = 3; i < 12; i++) {
			if(i == 7) {
				continue;
			}
			for (int j = 0 ; j < 2; j++) {
				randomNumberStack.add(i);
				
			}
		}
		
		randomNumberStack.add(2);
		randomNumberStack.add(12);
		
		
	}

	private void fillRandomResourceStack() {
		for(int i = 0 ; i < 3; i++) {
			randomResourceStack.push(ResourceType.ORE);
		}
		for(int i = 0 ; i < 4; i++) {
			randomResourceStack.push(ResourceType.WOOD);
		}
		for(int i = 0 ; i < 4; i++) {
			randomResourceStack.push(ResourceType.SHEEP);
		}
		for(int i = 0 ; i < 3; i++) {
			randomResourceStack.push(ResourceType.BRICK);
		}
		for(int i = 0 ; i < 4; i++) {
			randomResourceStack.push(ResourceType.WHEAT);
		}
		randomResourceStack.push(ResourceType.DESERT);
		
		
		
	}

	@Override
	public Map<Integer, List<Tile>> createDiceRollToTiles() {
		return diceToTiles;
	}

	@Override
	public BoardGraph getBoard() {
		return board;
	}

	@Override
	public List<Tile> getTiles() {
		for(Tile tile: tiles) {
			tile.setResourceType(randomResourceStack.pop());
			if(tile.getResourceType() == ResourceType.DESERT) {
				tile.setNumber(0);
			}
			else {
				diceToTiles.get(randomNumberStack.peek()).add(tile);
				tile.setNumber(randomNumberStack.pop());
				
			}

		}

		return tiles;

	}

}
