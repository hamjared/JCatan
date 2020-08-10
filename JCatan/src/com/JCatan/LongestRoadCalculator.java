package com.JCatan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LongestRoadCalculator
{

    
    private BoardGraph board;
    private Map<Player, Integer> playerToLongestRoad ;
    private Set<Road> foundRoads;
    private Set<Player> foundPlayers;
    
    public LongestRoadCalculator(BoardGraph board) {
        this.board = board;
        this.playerToLongestRoad = new HashMap<>();
        this.foundRoads = new HashSet<>();
        this.foundPlayers = new HashSet<>();
    }
    
    public Map<Player, Integer> calcLongestRoad(){
        int i = 0;
        for(Node node: board.getNodeList()) {
            System.out.println("Node: " + i);
            for (Road road: node.getRoads()) {
                if(! this.foundRoads.contains(road)) {
                    followRoad(road);
                }
                
            }
            
            i++;
            
        }
        
        
        
        
        
        
        
        return playerToLongestRoad;
    }
    
    private void followRoad(Road road) {
        this.foundRoads.add(road);
        
    }
    
    private void incrementPlayerLongestRoadCou(Road road) {
        
    }
    
    
    public static void main(String[] args) {
        Board board = new Board(new TraditionalBoardFactory());
        for(Node node: board.board.getNodeList()) {
            System.out.println(node);
        }
        System.out.println(board);
    }
}
