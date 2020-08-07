package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public class Road implements ICost
{

    Node node1;
    Node node2;
    Player player;
    protected boolean hasBeenPlayed;

    /**
     * @param node1
     * @param node2
     * @param player
     */
    public Road(Node node1, Node node2, Player player)
    {
        this.node1 = node1;
        this.node2 = node2;
        this.player = player;
        this.hasBeenPlayed = false;
    }
    

    @Override
    public Map<ResourceType, Integer> getCost()
    {
        Map<ResourceType, Integer> cost = new HashMap<>();

        cost.put(ResourceType.WOOD, 1);
        cost.put(ResourceType.BRICK, 1);

        return cost;
    }

	public void setHasBeenPlayed(boolean hasBeenPlayed) {
		this.hasBeenPlayed = hasBeenPlayed;
	}
    
    
}
