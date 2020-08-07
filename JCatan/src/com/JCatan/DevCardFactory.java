package com.JCatan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DevCardFactory {
    private static final int NUM_KNIGHT_CARDS = 14;
    private static final int NUM_MONOPOLY_CARDS = 2;
    private static final int NUM_ROAD_BUILDING_CARDS = 2;
    private static final int NUM_YEAR_OF_PLENTY_CARDS = 2;
    private static final int NUM_VICTORY_POINT_CARDS = 5;
	
	/**
	 * @return
	 */
	public static Stack<DevelopmentCard> makeDevCards(){
	    Stack<DevelopmentCard> devCards = new Stack<>();
	    
	    for (int i = 0 ; i < NUM_KNIGHT_CARDS; i++) {
	        devCards.add(new KnightDevelopmentCard());
	    }
	    
	    for (int i = 0 ; i < NUM_MONOPOLY_CARDS; i++) {
            devCards.add(new MonopolyDevelopmentCard());
        }
	    
	    for (int i = 0 ; i < NUM_ROAD_BUILDING_CARDS; i++) {
            devCards.add(new RoadBuildingDevelopmentCard());
        }
	    
	    for (int i = 0 ; i < NUM_YEAR_OF_PLENTY_CARDS; i++) {
            devCards.add(new YearOfPlentyDevelopmentCard());
        }
	    
	    for (int i = 0 ; i < NUM_VICTORY_POINT_CARDS; i++) {
            devCards.add(new VictoryPointDevelopmentCard());
        }
	    
	    Collections.shuffle(devCards);
	    
		return devCards;
	}

}
