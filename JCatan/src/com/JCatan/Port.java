package com.JCatan;

public class Port {
	PortType portType;
	ResourceType resourceType;
	public enum PortType{
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
