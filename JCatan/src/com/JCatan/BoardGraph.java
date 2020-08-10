package com.JCatan;

import java.util.List;

public class BoardGraph {
	List<List<Node>> nodes;
	
	public BoardGraph(List<List<Node>> nodes) {
	    this.nodes = nodes;
	}

	public List<List<Node>> getNodes() {
		return nodes;
	}

	public void setNodes(List<List<Node>> nodes) {
		this.nodes = nodes;
	}
	
	// methods like checkConnections, ....

}
