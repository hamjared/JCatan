package com.JCatan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TraditionalBoardFactory extends BoardFactory
{

    @Override
    public Map<Integer, List<Tile>> createDiceRollToTiles()
    {
        Map<Integer, List<Tile>> diceToTiles = new HashMap<>();
        
        Tile[][] array = {
            
            {}, //0
            {}, //1
            {tiles.get(1)}, //2
            {tiles.get(10), tiles.get(13)}, //3
            {tiles.get(5), tiles.get(14)}, //4
            {tiles.get(15), tiles.get(16)}, //5
            {tiles.get(4), tiles.get(17)}, //6
            {}, //7
            {tiles.get(11), tiles.get(12)}, //8
            {tiles.get(2), tiles.get(7)}, //9
            {tiles.get(0), tiles.get(6)}, //10
            {tiles.get(8), tiles.get(18)}, //11
            {tiles.get(3)}, //12

        };
        
        for(int i = 0; i < array.length; i ++) {
            diceToTiles.put(i, Arrays.asList(array[i]));
        }
        
        Iterator it = diceToTiles.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            List<Tile> tileList = (List<Tile>) pair.getValue();
            tileList.forEach(tile ->tile.setNumber((int) pair.getKey()));
        }
        
        return diceToTiles;
    }

    @Override
    public BoardGraph getBoard()
    {
        // TODO BoardFactory: getBoard
        return board;
    }

    @Override
    public List<Tile> getTiles()
    {
        tiles.get(0).setResourceType(ResourceType.ORE);
        tiles.get(1).setResourceType(ResourceType.SHEEP);
        tiles.get(2).setResourceType(ResourceType.WOOD);
        tiles.get(3).setResourceType(ResourceType.WHEAT);
        tiles.get(4).setResourceType(ResourceType.BRICK);
        tiles.get(5).setResourceType(ResourceType.SHEEP);
        tiles.get(6).setResourceType(ResourceType.BRICK);
        tiles.get(7).setResourceType(ResourceType.WHEAT);
        tiles.get(8).setResourceType(ResourceType.WOOD);
        tiles.get(9).setResourceType(ResourceType.DESERT);
        tiles.get(10).setResourceType(ResourceType.WOOD);
        tiles.get(11).setResourceType(ResourceType.ORE);
        tiles.get(12).setResourceType(ResourceType.WOOD);
        tiles.get(13).setResourceType(ResourceType.ORE);
        tiles.get(14).setResourceType(ResourceType.WHEAT);
        tiles.get(15).setResourceType(ResourceType.SHEEP);
        tiles.get(16).setResourceType(ResourceType.BRICK);
        tiles.get(17).setResourceType(ResourceType.WHEAT);
        tiles.get(18).setResourceType(ResourceType.SHEEP);
        return tiles;
    }

   
}
