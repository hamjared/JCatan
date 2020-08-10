package com.JCatan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardGraph {
	List<List<Node>> nodes;
	List<Node> nodeList;
	
	public BoardGraph(List<List<Node>> nodes, List<Node> nodeList) {
	    this.nodes = nodes;
	    this.nodeList = nodeList;
	}
	// methods like checkConnections, ....

    public List<List<Node>> getNodes()
    {
        return nodes;
    }

    public List<Node> getNodeList()
    {
        return nodeList;
    }


}
