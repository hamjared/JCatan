package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public class City extends Building
{
    
    private static final int CITY_VICTORY_POINTS = 2;

    public City(Player player)
    {
        super(player, CITY_VICTORY_POINTS);

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

        cost.put(ResourceType.WHEAT, 2);
        cost.put(ResourceType.ORE, 3);

        return cost;
    }



}
