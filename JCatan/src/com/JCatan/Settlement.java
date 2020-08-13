package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public class Settlement extends Building
{

    private static final int SETTLEMENT_VICTORY_POINTS = 1;

    public Settlement(Player player)
    {
        super(player, SETTLEMENT_VICTORY_POINTS);

    }

	@Override
    public void gatherResources(ResourceType resource)
    {
        // TODO Building: gatherResources
        

    }

    @Override
    public Map<ResourceType, Integer> getCost()
    {
        Map<ResourceType, Integer> cost = new HashMap<>();

        cost.put(ResourceType.WHEAT, 1);
        cost.put(ResourceType.SHEEP, 1);
        cost.put(ResourceType.BRICK, 1);
        cost.put(ResourceType.WOOD, 1);

        return cost;
    }

	@Override
	public void gatherResources(GameController controller, ResourceType resource) {
		// TODO Auto-generated method stub
		
	}
}