package com.JCatan;

import java.io.Serializable;

public class ResourceCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public boolean equals(Object obj) {
		if(!(obj instanceof ResourceCard)) {
			return false;
		}
		
		ResourceCard rc = (ResourceCard) obj;
		if(rc.getResourceType() == this.resourceType) {
			return true;
		}
		return false;
	}

}