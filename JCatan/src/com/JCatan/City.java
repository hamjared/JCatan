package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public class City extends Building
{
    
    private static final int CITY_VICTORY_POINTS = 2;
    Node node;

    public City(Player player, Node node)
    {
        super(player, CITY_VICTORY_POINTS, node);

    }

	@Override
    public void gatherResources(GameController controller, ResourceType resource)
    {
		for (int i = 0; i < 2; i++) {
			try {
				ResourceCard card = controller.getBank().takeResourceCard(resource);
				player.getResources().add(card);
			} catch (InsufficientResourceCardException e) {
				e.printStackTrace();
			}
		}
    }

    @Override
    public Map<ResourceType, Integer> getCost()
    {
        Map<ResourceType, Integer> cost = new HashMap<>();

        cost.put(ResourceType.WHEAT, 2);
        cost.put(ResourceType.ORE, 3);

        return cost;
    }

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

    


}
