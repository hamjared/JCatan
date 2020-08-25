package com.JCatan;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public abstract class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<ResourceCard> resources;
	List<DevelopmentCard> devCards;
	List<Road> roads;
	List<Building> buildings;
	List<Building> playedBuildings;
	List<Road> playedRoads;
	int victoryPoints;
	int diceRoll;
	int roadBuilderRoads;
	String name;
	Color color;
	Random randomGenerator;
    boolean hasLongestRoad;
    boolean hasLargestArmy;
  
	public List<Road> getPlayedRoads() {
		return playedRoads;
	}

	public String getName() {
		return name;
	}

    public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	Player(String name) {
		roadBuilderRoads = 0;
		resources = new ArrayList<ResourceCard>();
		devCards = new ArrayList<DevelopmentCard>();
		victoryPoints = 0;
		buildings = new ArrayList<Building>();
		playedBuildings = new ArrayList<>();
		playedRoads = new ArrayList<Road>();
		this.roads = new ArrayList<>();
		this.name = name;
		initializeBuildingsAndRoads();
		hasLongestRoad = false;
		hasLargestArmy = false;
		color = Color.BLACK;
	}

	public List<ResourceCard> getResources(){
		return resources;
	}
	
    public Map<ResourceType, Integer> getUniqueResourcesCount(){
   	 Map<ResourceType, Integer> temp = resources.stream().collect(
   			 										Collectors.toMap(t->((ResourceCard)t).getResourceType(),
   			 										v->1, (existing, addition)-> existing + addition));
   	 return temp;
   }
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildCity(Node node, GameController controller) throws InsufficientResourceCardException;

	/**
	 * 
	 */
	public abstract void buildPhase(Node node1, Node node2, GameController contoller);

	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildRoad(GamePhase phase, Node node1, Node node2, GameController controller) throws InsufficientResourceCardException;

	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildSettlement(GamePhase phase, Node node, GameController controller) throws InsufficientResourceCardException;

	/**
	 * @throws InsufficientResourceCardException
	 */
	public void buyDevelopmentCard(GameController controller) throws InsufficientResourceCardException{
		Bank bank = controller.getBank();
		Map<ResourceType, Integer> cost = bank.getDevCardCost();
		if(!resourceCheck(cost)) {
			throw new InsufficientResourceCardException();
		}
		
		removeResourceCards(cost);
		 
		try {
			DevelopmentCard card = bank.takeDevelopmentCard();
			this.devCards.add(card);
			card.setHasBeenPlayed(false);
			
		} catch (OutOfDevelopmentCardsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeResourceCards(Map<ResourceType, Integer> cost) {
		for(Map.Entry mapElement : cost.entrySet()) {
			ResourceType resource = (ResourceType) mapElement.getKey();
			Integer numResourcesRequired = (Integer) mapElement.getValue();
			for ( int i = 0 ; i < numResourcesRequired; i++) {
				this.resources.remove(new ResourceCard(resource));
			}
		}
		
	}

	@SuppressWarnings("rawtypes")
	public boolean resourceCheck(Map<ResourceType, Integer> cost) {
		for(Map.Entry mapElement : cost.entrySet()) {
			ResourceType resource = (ResourceType) mapElement.getKey();
			Integer numResourcesRequired = (Integer) mapElement.getValue();
			long numResources = this.resources.parallelStream().filter(r -> r.getResourceType() == resource).count();
			if(numResources < numResourcesRequired) {
				return false;
			}
			
		}
		return true;
	}
	
	/**
	 * 
	 */
	public int calcVictoryPoints()
    {
        victoryPoints = 0;
        victoryPoints += playedBuildings.stream()
                .filter(b -> b.hasBeenPlayed())
                .reduce(0, (subtotal, b) -> subtotal + b.getVictoryPoints(),
                        Integer::sum);

        victoryPoints += devCards.stream().filter(c -> c.isHasBeenPlayed()).reduce(0,
                (subtotal, dc) -> subtotal + dc.getVictoryPoints(),
                Integer::sum);
        
        if(hasLongestRoad) {
            victoryPoints += 2;
        }
        
        if(hasLargestArmy) {
            victoryPoints += 2;
        }

        return victoryPoints;

    }


	public int getNumberOfKnightsPlayed() {
    	int numKnights = 0;
    	for (DevelopmentCard devCard : devCards) {
    		if(devCard instanceof KnightDevelopmentCard && devCard.isHasBeenPlayed()) {
    			numKnights ++;
    		}
    	}
    	
    	return numKnights;
    }
	

	public void initializeBuildingsAndRoads() {
		for (int i = 0; i < 5; i++) {
			Settlement settlement = new Settlement(this, null);
			this.giveBuilding(settlement);
		}
		for (int i = 0; i < 4; i++) {
			City city = new City(this, null);
			this.giveBuilding(city);
		}
		for (int i = 0; i < 15; i++) {
			Road road = new Road(null, null, this);
			this.giveRoad(road);
		}
	}

	public void removeResource(ResourceType type) {
		for (ResourceCard c : this.getResources()) {
			if (c.getResourceType().equals(type)) {
				this.getResources().remove(c);
				break;
			}
		}
	}

	public void giveBuilding(Building building) {
		this.buildings.add(building);
	}

	public void giveRoad(Road road) {
		this.roads.add(road);
	}

	/**
	 * 
	 */
	public abstract void endTurn();
	
	/**
	 * @return
	 */
	public int getDiceRoll() {
		return diceRoll;
	}

	/**
	 * @param card
	 * @throws InvalidDevCardUseException
	 */
	public void playDevelopmentCard(DevelopmentCard card, DevCardAction devCardAction) throws InvalidDevCardUseException{
		if(! card.isCanBePlayed()) {
			throw new InvalidDevCardUseException();
		}
		card.performAction(devCardAction);
		card.setHasBeenPlayed(true);
	}

	/**
	 * @param trade
	 */
	public abstract void proposeTrade(Trade trade);

	/**
	 * @param trade
	 */
	public abstract void receiveTrade(Trade trade);

	/**
	 * @return
	 */
	public abstract int rollDice();

	/**
	 *  
	 */
	public abstract void setup(Node node1, Node node2, GameController controller);

	public abstract void sevenRolled(Player activePlayer, Bank bank);

	public boolean sevenRolledSteal(Player player) {
		List<ResourceCard> resources = player.getResources();
		if(resources.size() <= 0) {
			return false;
		} else {
			int index = randomGenerator.nextInt(resources.size());
			ResourceCard card = resources.get(index);
			this.getResources().add(card);
			for (ResourceCard r : resources) {
				if (r.equals(card)) {
					player.getResources().remove(card);
					return true;
				}

			}
		}
		return false;	

	}

	public List<DevelopmentCard> getDevCards() { 
		return devCards;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return name + ": " + diceRoll;
	}

	/**
	 * 
	 */
	public abstract void tradePhase();
	
	public boolean hasEnoughResource(ResourceType resource, int amount) {
		return amount <= resources.stream().filter(re -> re.getResourceType() == resource).count();
	}

	public boolean isHasLongestRoad() {
		return hasLongestRoad;
	}

	public boolean isHasLargestArmy() {
		return hasLargestArmy;
	}

	public void giveDevelopmentCard(DevelopmentCard devCard) {
		devCards.add(devCard);
	}


	public List<Building> getBuildings() {
		return buildings;
	}

	public List<Road> getRoads() {
		return roads;
	}

	public void setResources(List<ResourceCard> resources) {
		this.resources = resources;
	}

	public int calcLongestRoad() {
    	return new LongestRoadCalculator(playedRoads, this).calcLongestRoad();
    }
    

	public void setHasLongestRoad(boolean hasLongestRoad) {
		this.hasLongestRoad = hasLongestRoad;
	}

	public void setHasLargestArmy(boolean hasLargestArmy) {
		this.hasLargestArmy = hasLargestArmy;
	}

	public List<Building> getPlayedBuildings() {
		return playedBuildings;
	}

	public int getRoadBuilderRoads() {
		return roadBuilderRoads;
	}
	
	public void setRoadBuilderRoads(int r) {
		roadBuilderRoads = r;
	}
	
	
}