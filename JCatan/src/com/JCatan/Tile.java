package com.JCatan;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Tile
{

    Color color;

    ResourceType resourceType;
    boolean hasRobber;
    int number;
    List<Node> nodes;
    
    public void setResourceType(ResourceType resourceType)
    {
        this.resourceType = resourceType;
        switch(resourceType) {
        case WHEAT :
            this.color = Color.decode("#FFD700");
            break;
        case WOOD:
            this.color = Color.decode("#003300");
            break;
        case BRICK:
            this.color = Color.decode("#663300");
            break;
        case SHEEP:
            this.color = Color.decode("#99FF33");
            break;
        case ORE:
            this.color = Color.decode("#4C0099");
            break;
        case DESERT:
            this.color = Color.decode("#DEB887");
            break;
            
        }
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
    
    
    public Tile(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public int getNumber()
    {
        return number;
    }

    public List<Node> getNodes() {
		return nodes;
	}

	public Tile(ResourceType resourceType, int number) {
        this.resourceType = resourceType;
        this.number = number;
        switch(resourceType) {
        case WHEAT :
            this.color = Color.YELLOW;
            break;
        case WOOD:
            this.color = Color.GREEN;
            break;
        case BRICK:
            this.color = Color.RED;
            break;
        case SHEEP:
            this.color = Color.WHITE;
            break;
        case ORE:
            this.color = Color.DARK_GRAY;
            break;
        case DESERT:
            this.color = Color.lightGray;
            break;
            
        }
    }

    public List<Building> getBuildings()
    {
        List<Building> buildings = new ArrayList<>();

        for (Node node : nodes)
        {
            if (node != null)
            {
                buildings.add(node.getBuilding());
            }
        }

        return buildings;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public ResourceType getResourceType()
    {
        return resourceType;
    }

}
