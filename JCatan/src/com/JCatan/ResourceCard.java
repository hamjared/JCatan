package com.JCatan;

public class ResourceCard {

	ResourceType resourceType;
	
	/**
	 * @param type
	 */
	public ResourceCard(ResourceType type ) {
		this.resourceType = type;
	}
  
	public ResourceType getResourceType() {
		return resourceType;
	}

}