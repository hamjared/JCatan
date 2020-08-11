package com.JCatan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);

	}

	@Override
	public void buildPhase(Node node1, Node node2, GameController controller) {
		// Check for listener about player wanting to building road/building

		try {
			buildRoad(GamePhase.GAME, node1, node2, controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			buildSettlement(GamePhase.GAME, node1, controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			buildCity(node1, controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void buildRoad(GamePhase phase, Node node1, Node node2, GameController controller) throws InsufficientResourceCardException {
		if (phase == GamePhase.SETUP) {
			// Setup Phase Build
			Road road = new Road(node1, node2, this);
			road.setHasBeenPlayed(true);
			this.getRoads().remove(road);
			node1.addRoad(road);
			node2.addRoad(road);
		} else {
			// Game Phase Build
			if (this.getRoads().isEmpty() == false) {

				Road road = new Road(node1, node2, this);
				List<ResourceCard> resources = this.getResources();
				int numWheat = 0;
				int numWood = 0;
				int numOre = 0;
				int numSheep = 0;
				int numBrick = 0;
				int resourceCheck = 0;

				for (ResourceCard r : resources) {
					ResourceType type = r.getResourceType();
					switch (type) {
					case WHEAT:
						numWheat++;
						break;
					case WOOD:
						numWood++;
						break;
					case ORE:
						numOre++;
						break;
					case SHEEP:
						numSheep++;
						break;
					case BRICK:
						numBrick++;
						break;
					}
				}

				Map<ResourceType, Integer> mp = road.getCost();
				List<ResourceType> list = new ArrayList<>();
				Iterator it = mp.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					ResourceType typeCheck = (ResourceType) pair.getKey();
					switch (typeCheck) {
					case BRICK:
						if (numBrick < (int) pair.getValue()) {
							System.out.println("Insufficient Resources");
						} else {
							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case WOOD:
						if (numWood < (int) pair.getValue()) {
							System.out.println("Insufficient Resources");
						} else {
							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
					}
					it.remove(); // avoids a ConcurrentModificationException
				}

				if (resourceCheck == 2) {
					road.setHasBeenPlayed(true);
					this.getRoads().remove(road);
					node1.addRoad(road);
					node2.addRoad(road);
					for (ResourceType type : list) {
						this.removeResource(type);
						ResourceCard card = new ResourceCard(type);
						controller.getBank().giveResourceCard(card);
					}
				}

			}
		}
	}

	@Override
	public void buildSettlement(GamePhase phase, Node node, GameController controller) throws InsufficientResourceCardException {
		if (phase == GamePhase.SETUP) {
			// SetUp Phase Build
			Settlement settlement = new Settlement(this);
			settlement.setHasBeenPlayed(true);
			this.getBuildings().remove(settlement);
			node.setBuilding(settlement);
		} else {
			// Game Phase Build
			boolean settlementCheck = false;
			for (Building b : this.getBuildings()) {
				if (b.getClass().equals(Settlement.class)) {
					settlementCheck = true;
				}
			}
			if (settlementCheck == true) {

				Settlement settlement = new Settlement(this);
				List<ResourceCard> resources = this.getResources();
				int numWheat = 0;
				int numWood = 0;
				int numOre = 0;
				int numSheep = 0;
				int numBrick = 0;
				int resourceCheck = 0;

				for (ResourceCard r : resources) {
					ResourceType type = r.getResourceType();
					switch (type) {
					case WHEAT:
						numWheat++;
						break;
					case WOOD:
						numWood++;
						break;
					case ORE:
						numOre++;
						break;
					case SHEEP:
						numSheep++;
						break;
					case BRICK:
						numBrick++;
						break;
					}
				}
				Map<ResourceType, Integer> mp = settlement.getCost();
				List<ResourceType> list = new ArrayList<>();
				Iterator it = mp.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					ResourceType typeCheck = (ResourceType) pair.getKey();
					switch (typeCheck) {
					case BRICK:
						if (numBrick < (int) pair.getValue()) {
							System.out.println("Insufficient Resources");
						} else {
							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case WOOD:
						if (numWood < (int) pair.getValue()) {
							System.out.println("Insufficient Resources");
						} else {
							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case SHEEP:
						if (numSheep < (int) pair.getValue()) {
							System.out.println("Insufficient Resources");
						} else {
							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case WHEAT:
						if (numWheat < (int) pair.getValue()) {
							System.out.println("Insufficient Resources");
						} else {
							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
					}
					it.remove(); // avoids a ConcurrentModificationException
				}

				if (resourceCheck == 4) {
					settlement.setHasBeenPlayed(true);
					this.getBuildings().remove(settlement);
					node.setBuilding(settlement);
					for (ResourceType type : list) {
						this.removeResource(type);
						ResourceCard card = new ResourceCard(type);
						controller.getBank().giveResourceCard(card);
					}
				}
			}
		}
	}

	@Override
	public void buildCity(Node node, GameController controller) throws InsufficientResourceCardException {
		if (node.getBuilding() == null) {
			System.out.println("This node does not have a settlement so you cannot build a city here.");
		} else {
			boolean cityCheck = false;
			for (Building b : this.getBuildings()) {
				if (b.getClass().equals(Settlement.class)) {
					cityCheck = true;
				}
			}
			if (cityCheck == true) {

				City city = new City(this);
				List<ResourceCard> resources = this.getResources();
				int numWheat = 0;
				int numWood = 0;
				int numOre = 0;
				int numSheep = 0;
				int numBrick = 0;
				int resourceCheck = 0;

				for (ResourceCard r : resources) {
					ResourceType type = r.getResourceType();
					switch (type) {
					case WHEAT:
						numWheat++;
						break;
					case WOOD:
						numWood++;
						break;
					case ORE:
						numOre++;
						break;
					case SHEEP:
						numSheep++;
						break;
					case BRICK:
						numBrick++;
						break;
					}
				}
				Map<ResourceType, Integer> mp = city.getCost();
				List<ResourceType> list = new ArrayList<>();
				Iterator it = mp.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					ResourceType typeCheck = (ResourceType) pair.getKey();
					for (int i = 0; i < (int) pair.getValue(); i++) {

						switch (typeCheck) {
						case WHEAT:
							if (numWheat < (int) pair.getValue()) {
								System.out.println("Insufficient Resources");
							} else {
								System.out.println("Im in the resourceCheck");
								resourceCheck++;
								list.add((ResourceType) pair.getKey());
							}
							break;
						case ORE:
							if (numOre < (int) pair.getValue()) {
								System.out.println("Insufficient Resources");
							} else {
								System.out.println("Im in the resourceCheck");
								resourceCheck++;
								list.add((ResourceType) pair.getKey());
							}
						}
					}
					it.remove(); // avoids a ConcurrentModificationException
				}

				if (resourceCheck == 5) {
					city.setHasBeenPlayed(true);
					this.getBuildings().remove(city);
					Settlement settlement = new Settlement(this);
					this.giveBuilding(settlement);
					node.setBuilding(city);
					for (ResourceType type : list) {
						this.removeResource(type);
						ResourceCard card = new ResourceCard(type);
						controller.getBank().giveResourceCard(card);
					}
				}
			}
		}
	}

	@Override
	public void buyDevelopmentCard() throws InsufficientResourceCardException {
		// TODO Player: buyDevelopmentCard

	}

	@Override
	public void endTurn() {
		// TODO Player: endTurn

	}

	@Override
	public void playDevelopmentCard(DevelopmentCard card) throws InvalidDevCardUseException {
		if (!devCards.contains(card)) {
			throw new InvalidDevCardUseException();
		}
		card.performAction();
		this.devCards.remove(card);

	}

	@Override
	public void proposeTrade(Trade trade) {
		// TODO Player: proposeTrade

	}

	@Override
	public void receiveTrade(Trade trade) {
		// TODO Player: receiveTrade

	}

	@Override
	public int rollDice() {
		diceRoll = new Dice().rollDice();
		System.out.println("Dice roll: " + diceRoll);
		return diceRoll;
	}

	@Override
	public void setup(Node node1, Node node2, GameController controller) {
		// Player needs to put down 1 Settlement and 1 Road
		try {
			buildSettlement(GamePhase.SETUP, node1, controller);
		} catch (InsufficientResourceCardException e) {
			e.printStackTrace();
		}
		try {
			buildRoad(GamePhase.SETUP, node1, node2, controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void sevenRolled(Player activePlayer) {
		List<ResourceCard> resources = activePlayer.getResources();
		int handSize = resources.size();
		int cardsToDrop = 0;
		if(handSize > 7) {
			if (handSize %2 == 0) {
				//Player chooses half their cards to drop
				cardsToDrop = handSize / 2;
			} else {
				//Player rounds down and drops half their cards
				cardsToDrop = (handSize - 1) / 2;
			}
		}

	}

	@Override
	public void tradePhase() {
		// TODO Player: tradePhase

	}

}
