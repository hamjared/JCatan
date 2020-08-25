package com.JCatan;

import java.io.Serializable;
import java.util.List;

public class BoardGraph implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
