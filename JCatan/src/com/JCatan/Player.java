package com.JCatan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Player {
	List<ResourceCard> resources;
	List<DevelopmentCard> devCards;
	List<Building> buildings;
	List<Road> roads;
	int victoryPoints;
	int diceRoll;
	String name;
	Random randomGenerator;

	Player(String name) {
		resources = new ArrayList<ResourceCard>();
		devCards = new ArrayList<DevelopmentCard>();
		victoryPoints = 0;
		buildings = new ArrayList<Building>();
		roads = new ArrayList<Road>();
		this.name = name;
		initializeBuildingsAndRoads();
	}

	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildCity(Node node) throws InsufficientResourceCardException;

	/**
	 * 
	 */
	public abstract void buildPhase(Node node1, Node node2);

	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildRoad(GamePhase phase, Node node1, Node node2) throws InsufficientResourceCardException;

	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildSettlement(GamePhase phase, Node node) throws InsufficientResourceCardException;

	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buyDevelopmentCard() throws InsufficientResourceCardException;

	/**
	 * 
	 */
	public int calcVictoryPoints() {
		victoryPoints = 0;
		victoryPoints += buildings.stream().filter(b -> b.hasBeenPlayed()).reduce(0,
				(subtotal, b) -> subtotal + b.getVictoryPoints(), Integer::sum);

		victoryPoints += devCards.stream().reduce(0, (subtotal, dc) -> subtotal + dc.getVictoryPoints(), Integer::sum);

		return victoryPoints;

	}

	public void initializeBuildingsAndRoads() {
		for (int i = 0; i < 5; i++) {
			Settlement settlement = new Settlement(this);
			this.giveBuilding(settlement);
		}
		for (int i = 0; i < 4; i++) {
			City city = new City(this);
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

	public List<ResourceCard> getResources() {
		return resources;
	}

	/**
	 * @param card
	 * @throws InvalidDevCardUseException
	 */
	public abstract void playDevelopmentCard(DevelopmentCard card) throws InvalidDevCardUseException;

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
	public abstract void setup(Node node1, Node node2);

	public abstract void sevenRolled(Player activePlayer);

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

}
