package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public class Settlement extends Building
{

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

}
