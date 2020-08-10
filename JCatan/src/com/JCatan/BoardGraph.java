package com.JCatan;

import java.util.List;

public class BoardGraph {
	List<List<Node>> nodes;
	List<Node> nodeList;
	
	public BoardGraph(List<List<Node>> nodes, List<Node> nodeList) {
	    this.nodes = nodes;
	    this.nodeList = nodeList;
	}

	public void setNodes(List<List<Node>> nodes) {
		this.nodes = nodes;
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
