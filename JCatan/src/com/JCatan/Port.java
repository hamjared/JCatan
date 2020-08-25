package com.JCatan;

import java.io.Serializable;

public class Port implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PortType portType;
	ResourceType resourceType;
	public static enum PortType{
	    GENERIC,
	    SPECIAL
	}
	
	/**
	 * @param tradeType
	 */
	public Port(PortType portType, ResourceType resourceType) {
		this.portType = portType;
		this.resourceType = resourceType;
	}

	public PortType getPortType() {
		return portType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}
	
	
}
