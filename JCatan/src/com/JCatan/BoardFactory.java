package com.JCatan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class BoardFactory implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int NUM_NODES = 54;
    private static final int[] WHEAT_PORT_NODES =
    { 1, 5 };
    private static final int[] BRICK_PORT_NODES =
    { 33, 38 };
    private static final int[] WOOD_PORT_NODES =
    { 11, 16 };
    private static final int[] ORE_PORT_NODES =
    { 10, 15 };
    private static final int[] SHEEP_PORT_NODES =
    { 42, 46 };
    private static final int[] GENERIC_PORT_NODES =
    { 0, 3, 26, 32, 47, 49, 51, 52 };
    
    protected List<Node> nodes;
    protected List<Tile> tiles;
    protected BoardGraph board;

	/**
	 * @return
	 */
    public BoardFactory() {
        createBoard();
        createTiles();
        assignTilesToNodes();
        
    }
    

    public abstract Map<Integer, List<Tile>> createDiceRollToTiles();
    public abstract BoardGraph getBoard();
    public abstract List<Tile> getTiles();

    
    private void createBoard()
    {
        nodes = createNodes();
        Node[][] array =
        {
                { nodes.get(3), nodes.get(4) }, // 0
                { nodes.get(4), nodes.get(5) }, // 1
                { nodes.get(5), nodes.get(6) }, // 2
                { nodes.get(0), nodes.get(7) }, // 3
                { nodes.get(0), nodes.get(1), nodes.get(8) }, // 4
                { nodes.get(1), nodes.get(2), nodes.get(9) }, // 5
                { nodes.get(2), nodes.get(10) }, // 6
                { nodes.get(3), nodes.get(11), nodes.get(12) }, // 7
                { nodes.get(4), nodes.get(12), nodes.get(13) }, // 8
                { nodes.get(5), nodes.get(13), nodes.get(14) }, // 9
                { nodes.get(6), nodes.get(14), nodes.get(15) }, // 10
                { nodes.get(7), nodes.get(16) }, // 11
                { nodes.get(7), nodes.get(8), nodes.get(17) }, // 12
                { nodes.get(8), nodes.get(9), nodes.get(18) }, // 13
                { nodes.get(9), nodes.get(10), nodes.get(19) }, // 14
                { nodes.get(10), nodes.get(20) }, // 15
                { nodes.get(11), nodes.get(21), nodes.get(22) }, // 16
                { nodes.get(12), nodes.get(22), nodes.get(23) }, // 17
                { nodes.get(13), nodes.get(23), nodes.get(24) }, // 18
                { nodes.get(14), nodes.get(24), nodes.get(25) }, // 19
                { nodes.get(15), nodes.get(25), nodes.get(26) }, // 20
                { nodes.get(16), nodes.get(27) }, // 21
                { nodes.get(16), nodes.get(17), nodes.get(28) }, // 22
                { nodes.get(17), nodes.get(18), nodes.get(29) }, // 23
                { nodes.get(18), nodes.get(19), nodes.get(30) }, // 24
                { nodes.get(19), nodes.get(20), nodes.get(31) }, // 25
                { nodes.get(20), nodes.get(32) }, // 26
                { nodes.get(21), nodes.get(33) }, // 27
                { nodes.get(22), nodes.get(33), nodes.get(34) }, // 28
                { nodes.get(23), nodes.get(34), nodes.get(35) }, // 29
                { nodes.get(24), nodes.get(35), nodes.get(36) }, // 30
                { nodes.get(25), nodes.get(36), nodes.get(37) }, // 31
                { nodes.get(26), nodes.get(37) }, // 32
                { nodes.get(28), nodes.get(38) }, // 33
                { nodes.get(28), nodes.get(29), nodes.get(39) }, // 34
                { nodes.get(29), nodes.get(30), nodes.get(40) }, // 35
                { nodes.get(30), nodes.get(31), nodes.get(41) }, // 36
                { nodes.get(31), nodes.get(32), nodes.get(42) }, // 37
                { nodes.get(33), nodes.get(43) }, // 38
                { nodes.get(34), nodes.get(43), nodes.get(44) }, // 39
                { nodes.get(35), nodes.get(44), nodes.get(45) }, // 40
                { nodes.get(36), nodes.get(45), nodes.get(46) }, // 41
                { nodes.get(37), nodes.get(46) }, // 42
                { nodes.get(38), nodes.get(39), nodes.get(47) }, // 43
                { nodes.get(39), nodes.get(40), nodes.get(48) }, // 44
                { nodes.get(40), nodes.get(41), nodes.get(49) }, // 45
                { nodes.get(41), nodes.get(42), nodes.get(50) }, // 46
                { nodes.get(43), nodes.get(51) }, // 47
                { nodes.get(44), nodes.get(51), nodes.get(52) }, // 48
                { nodes.get(45), nodes.get(52), nodes.get(53) }, // 49
                { nodes.get(46), nodes.get(53) }, // 50
                { nodes.get(47), nodes.get(48) }, // 51
                { nodes.get(48), nodes.get(49) }, // 52
                { nodes.get(49), nodes.get(50) } // 53

        };
        List<List<Node>> boardGraph = new ArrayList<>();
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
        {
            boardGraph.add(Arrays.asList(array[i]));
            nodeList.add(nodes.get(i));
        }
        
        
        

        board =  new BoardGraph(boardGraph, nodeList);

    }

    @SuppressWarnings("unlikely-arg-type")
    private List<Node> createNodes(){
        nodes = new ArrayList<>();
        for (int i = 0; i < NUM_NODES; i++)
        {
            if (contains(WHEAT_PORT_NODES,i))
            {
                nodes.add(new PortNode(
                        new Port(Port.PortType.SPECIAL, ResourceType.WHEAT)));
            }
            else if (contains(BRICK_PORT_NODES,i))
            {
                nodes.add(new PortNode(
                        new Port(Port.PortType.SPECIAL, ResourceType.BRICK)));
            }
            else if (contains(ORE_PORT_NODES,i))
            {
                nodes.add(new PortNode(
                        new Port(Port.PortType.SPECIAL, ResourceType.ORE)));
            }
            else if (contains(WOOD_PORT_NODES,i))
            {
                nodes.add(new PortNode(
                        new Port(Port.PortType.SPECIAL, ResourceType.WOOD)));
            }
            else if (contains(SHEEP_PORT_NODES,i))
            {
                nodes.add(new PortNode(
                        new Port(Port.PortType.SPECIAL, ResourceType.SHEEP)));
            }
            else if (contains(GENERIC_PORT_NODES,i))
            {
                nodes.add(new PortNode(new Port(Port.PortType.GENERIC, null)));
            }
            else {
            	nodes.add(new InteriorNode());
            }

            
            
            nodes.get(i).setNodeIndex(i);
        }
        
        
        
        return nodes;
    }
    
    private boolean contains(int[] array, int number) {
    	boolean contains = false;
    	for (int i = 0; i < array.length; i++) {
    		if(array[i] == number) {
    			contains = true;
    			break;
    		}
    	}
    	return contains;
    }



    private void createTiles()
    {
        tiles = new ArrayList<>();
        Node[][] array =
            {
                {nodes.get(3),nodes.get(0),nodes.get(4),nodes.get(8),nodes.get(12),nodes.get(7)},//0
                {nodes.get(4),nodes.get(1),nodes.get(5),nodes.get(9),nodes.get(13),nodes.get(8)},//1
                {nodes.get(5),nodes.get(2),nodes.get(6),nodes.get(10),nodes.get(14),nodes.get(9)},//2
                {nodes.get(11),nodes.get(7),nodes.get(12),nodes.get(17),nodes.get(22),nodes.get(16)},//3
                {nodes.get(12),nodes.get(8),nodes.get(13),nodes.get(18),nodes.get(23),nodes.get(17)},//4
                {nodes.get(13),nodes.get(9),nodes.get(14),nodes.get(19),nodes.get(24),nodes.get(18)},//5
                {nodes.get(14),nodes.get(10),nodes.get(15),nodes.get(20),nodes.get(25),nodes.get(19)},//6   
                {nodes.get(21), nodes.get(16), nodes.get(22), nodes.get(28), nodes.get(33), nodes.get(27) }, //7
                {nodes.get(22), nodes.get(17), nodes.get(23), nodes.get(29), nodes.get(34), nodes.get(28) }, //8
                {nodes.get(23), nodes.get(18), nodes.get(24), nodes.get(30), nodes.get(35), nodes.get(29) }, //9
                {nodes.get(24), nodes.get(19), nodes.get(25), nodes.get(31), nodes.get(36), nodes.get(30) }, //10
                {nodes.get(25), nodes.get(20), nodes.get(26), nodes.get(32), nodes.get(37), nodes.get(31) }, //11
                {nodes.get(33), nodes.get(28), nodes.get(34), nodes.get(39), nodes.get(43), nodes.get(38)}, //12
                {nodes.get(34), nodes.get(29), nodes.get(35), nodes.get(40), nodes.get(44), nodes.get(39)}, //13
                {nodes.get(35), nodes.get(30), nodes.get(36), nodes.get(41), nodes.get(45), nodes.get(40)}, //14
                {nodes.get(36), nodes.get(31), nodes.get(37), nodes.get(42), nodes.get(46), nodes.get(41)}, //15
                {nodes.get(43), nodes.get(39), nodes.get(44), nodes.get(48), nodes.get(51), nodes.get(47)}, //16
                {nodes.get(44), nodes.get(40), nodes.get(45), nodes.get(49), nodes.get(52), nodes.get(48)}, //17
                {nodes.get(45), nodes.get(41), nodes.get(46), nodes.get(50), nodes.get(53), nodes.get(49)} //18

            };
        
        for (int i = 0 ; i < array.length; i++) {
            tiles.add(new Tile(Arrays.asList(array[i]), i ));
            
        }
        

       
    }
    
    private void assignTilesToNodes() {
        Tile[][] array = {
                {tiles.get(0)},//0
                {tiles.get(1)},//1
                {tiles.get(2)},//2
                {tiles.get(0)},//3
                {tiles.get(0),tiles.get(1)},//4
                {tiles.get(1),tiles.get(2)},//5
                {tiles.get(2)},//6
                {tiles.get(0),tiles.get(3)},//7
                {tiles.get(0),tiles.get(1),tiles.get(4)},//8
                {tiles.get(1),tiles.get(2),tiles.get(5)},//9
                {tiles.get(2),tiles.get(6)},//10
                {tiles.get(3)},//11
                {tiles.get(0),tiles.get(3),tiles.get(4)},//12
                {tiles.get(1),tiles.get(4),tiles.get(5)},//13
                {tiles.get(2),tiles.get(5),tiles.get(6)},//14
                {tiles.get(6)},//15
                {tiles.get(3),tiles.get(7)},//16
                {tiles.get(3),tiles.get(4),tiles.get(8)},//17
                {tiles.get(4),tiles.get(5),tiles.get(9)},//18
                {tiles.get(5),tiles.get(6),tiles.get(10)},//19
                {tiles.get(6),tiles.get(11)},//20
                {tiles.get(7)},//21
                {tiles.get(7), tiles.get(3), tiles.get(8)},//22
                {tiles.get(8), tiles.get(4), tiles.get(9)},//23
                {tiles.get(9), tiles.get(5), tiles.get(10)},//24
                {tiles.get(10), tiles.get(6), tiles.get(11)},//25
                {tiles.get(11)},//26
                {tiles.get(7)},//27
                {tiles.get(7), tiles.get(8), tiles.get(12)},//28
                {tiles.get(8), tiles.get(9), tiles.get(13)},//29
                {tiles.get(9), tiles.get(10), tiles.get(14)},//30
                {tiles.get(10), tiles.get(11), tiles.get(15)},//31
                {tiles.get(11)},//32
                {tiles.get(7), tiles.get(12)}, //33
                {tiles.get(12), tiles.get(13), tiles.get(8)}, //34
                {tiles.get(9), tiles.get(13), tiles.get(14)}, //35
                {tiles.get(10), tiles.get(14), tiles.get(15)}, //36
                {tiles.get(11), tiles.get(15)}, //37
                {tiles.get(12)}, //38
                {tiles.get(12), tiles.get(13), tiles.get(16)}, //39
                {tiles.get(13), tiles.get(14), tiles.get(17)}, //40
                {tiles.get(14), tiles.get(15), tiles.get(18)}, //41
                {tiles.get(15)}, //42
                {tiles.get(12), tiles.get(16)}, //43
                {tiles.get(13), tiles.get(16), tiles.get(17)}, //44
                {tiles.get(14), tiles.get(17), tiles.get(18)}, //45
                {tiles.get(15), tiles.get(18)}, //46
                {tiles.get(16)}, //47
                {tiles.get(16), tiles.get(17)}, //48
                {tiles.get(17), tiles.get(18)}, //49
                {tiles.get(18)}, //50
                {tiles.get(16)}, //51
                {tiles.get(17)}, //52
                {tiles.get(18)}, //53
        };
        
        
        for ( int i = 0 ; i < array.length; i++) {
            nodes.get(i).setTiles(Arrays.asList(array[i]));
        }
        
    }
	
}
