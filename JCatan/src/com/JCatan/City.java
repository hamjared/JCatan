package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public class City extends Building
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

        cost.put(ResourceType.WHEAT, 2);
        cost.put(ResourceType.ORE, 3);

        return cost;
    }

}
