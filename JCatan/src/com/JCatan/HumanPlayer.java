package com.JCatan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HumanPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		if(node1.getNodeIndex() > node2.getNodeIndex()) {
			Node temp = node1;
			node1 = node2;
			node2 = temp;
		}
		if (phase == GamePhase.SETUP) {
			// Setup Phase Build
			Road road = new Road(node1, node2, this);
			road.setHasBeenPlayed(true);
			this.playedRoads.add(road);
			this.getRoads().remove(0);
			node1.addRoad(road);
			node2.addRoad(road);
		} 
		else if (this.getRoadBuilderRoads() > 0){
			Road road = new Road(node1, node2, this);
			road.setHasBeenPlayed(true);
			this.playedRoads.add(road);
			this.getRoads().remove(0);
			node1.addRoad(road);
			node2.addRoad(road);
			roadBuilderRoads--;
		}
		else {
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
					default:
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
//							System.out.println("Insufficient Resources");
						} else {
//							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case WOOD:
						if (numWood < (int) pair.getValue()) {
//							System.out.println("Insufficient Resources");
						} else {
//							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
					default:
						break;
					}
					it.remove(); // avoids a ConcurrentModificationException
				}

				if (resourceCheck == 2) {
					road.setHasBeenPlayed(true);
					this.playedRoads.add(road);
					this.getRoads().remove(0);
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
			Settlement settlement = this.getNextSettlement();
			settlement.setHasBeenPlayed(true);
			this.playedBuildings.add(settlement);
			this.getBuildings().remove(settlement); 
			node.setBuilding(settlement);
			settlement.setNode(node);
			
			if (this.getBuildings().size() == 7) {
				for(Tile t: controller.getBoard().getTiles()) {
					if(t.getBuildings().contains(settlement)) {
						if(t.getResourceType() != ResourceType.DESERT) {
							settlement.gatherResources(controller, t.getResourceType());
							settlement.setId(1);
						}	
					}
				}
			} else {
				settlement.setId(0);
			}
		} else {
			// Game Phase Build
			boolean settlementCheck = false; 
			for (Building b : this.getBuildings()) {
				if (b.getClass().equals(Settlement.class)) {
					settlementCheck = true;
				}
			}
			if (settlementCheck == true) {
				Settlement settlement = this.getNextSettlement();
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
					default:
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
//							System.out.println("Insufficient Resources");
						} else {
//							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case WOOD:
						if (numWood < (int) pair.getValue()) {
//							System.out.println("Insufficient Resources");
						} else {
//							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case SHEEP:
						if (numSheep < (int) pair.getValue()) {
//							System.out.println("Insufficient Resources");
						} else {
//							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
						break;
					case WHEAT:
						if (numWheat < (int) pair.getValue()) {
//							System.out.println("Insufficient Resources");
						} else {
//							System.out.println("Im in the resourceCheck");
							resourceCheck++;
							list.add((ResourceType) pair.getKey());
						}
					default:
						break;
					}
					it.remove(); // avoids a ConcurrentModificationException
				}
				if (resourceCheck == 4) {
					settlement.setHasBeenPlayed(true);
					this.playedBuildings.add(settlement);
					this.getBuildings().remove(settlement);
					node.setBuilding(settlement);
					settlement.setNode(node);
					settlement.setId(0);
					for (ResourceType type : list) {
						this.removeResource(type);
						ResourceCard card = new ResourceCard(type);
						controller.getBank().giveResourceCard(card);
					}
				}
			}
		}
	}

	private Settlement getNextSettlement() {
		for(Building building: buildings) {
			if(! (building instanceof Settlement)) {
				continue;
			}
			if(building.hasBeenPlayed == false) {
				return (Settlement) building;
			}
		}
		return null;
	}

	@Override
	public void receiveTrade(Trade trade) {
		List<ResourceCard> requesting = null;
		List<ResourceCard> offering = null;
		
		if(trade.getOfferingPlayer().equals(this)) {
			requesting = trade.getRequestingCards();
			offering = trade.getOfferingCards();
		} else {
			 requesting = trade.getOfferingCards();
			 offering = trade.getRequestingCards();
		}
		
		for (int i = 0; i < offering.size(); i++) {
			resources.remove(offering.get(i));
		}
		
		for(ResourceCard card : requesting) {
			resources.add(card);
		}
		System.out.print(this.name + " Has received trade!");
	}
	
	@Override
	public void tradePhase() {
		
	}
	
    @Override
    public void proposeTrade(Trade trade){
    	//Remember the trade is the middle man here.  Trade helps with GUI side...
    	try {
    		if(trade instanceof DomesticTrade) {
    			System.out.print("Proposing trade!");
    		} else if(trade instanceof MaritimeTrade) {
    			
    		}
    	} catch(Exception e) {
    		
    	}
	}


	@Override
	public void buildCity(Node node, GameController controller) throws InsufficientResourceCardException {
		if (node.getBuilding() == null) {
			System.out.println("This node does not have a settlement so you cannot build a city here.");
		} else {
			boolean cityCheck = false;
			for (Building b : this.getBuildings()) {
				if (b.getClass().equals(City.class)) {
					cityCheck = true; 
				}
			}
			if (cityCheck == true) {
				City city = new City(this, node);
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
					default:
						break;
					}
				}
				Map<ResourceType, Integer> mp = city.getCost();
				List<ResourceType> list = new ArrayList<>();
				Iterator<Entry<ResourceType, Integer>> it = mp.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<ResourceType, Integer> pair = (Map.Entry<ResourceType, Integer>) it.next();
					ResourceType typeCheck = (ResourceType) pair.getKey();
					for (int i = 0; i < (int) pair.getValue(); i++) {

						switch (typeCheck) {
						case WHEAT:
							if (numWheat < (int) pair.getValue()) {
//								System.out.println("Insufficient Resources");
							} else {
//								System.out.println("Im in the resourceCheck");
								resourceCheck++;
								list.add((ResourceType) pair.getKey());
							}
							break;
						case ORE:
							if (numOre < (int) pair.getValue()) {
								System.out.println("Insufficient Resources");
							} else {
//								System.out.println("Im in the resourceCheck");
								resourceCheck++;
								list.add((ResourceType) pair.getKey());
							}
						default:
							break;
						}
					}
					it.remove(); // avoids a ConcurrentModificationException
				}
				
				if (resourceCheck == 5) {
					city.setHasBeenPlayed(true);
					Settlement settlement = (Settlement) node.getBuilding();
					settlement.setHasBeenPlayed(false);
					this.playedBuildings.remove(settlement);
					this.playedBuildings.add(city);
					for(int i = 0; i < this.getBuildings().size(); i++) {
						if (this.getBuildings().get(i) instanceof City) {
							this.getBuildings().remove(i);
							break;
						}
					}
					this.giveBuilding(settlement);					
					node.setBuilding(city);
					city.setNode(node);
					for(Building b: this.getBuildings()) {
						if(b instanceof City) {
							System.out.println("I have a city");
						}
					}
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
	public void endTurn() {
		// TODO Player: endTurn
	}


	@Override
	public int rollDice() {
		diceRoll = dice.rollDice();
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
	public void sevenRolled(Player activePlayer, Bank bank) {
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
		for(int i = 0; i < cardsToDrop; i++) {
			bank.giveResourceCard(activePlayer.getResources().get(i));
			activePlayer.getResources().remove(i);
			
		}

	}
}