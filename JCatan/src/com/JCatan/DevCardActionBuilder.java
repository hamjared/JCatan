package com.JCatan;

public class DevCardActionBuilder {
	
	DevCardAction devCardAction;
	
	public DevCardActionBuilder() {
		this.devCardAction = new DevCardAction();
	}
	
	public DevCardActionBuilder curPlayer(Player curPlayer) {
		this.devCardAction.setCurPlayer(curPlayer);
		return this;
	}
	
	public DevCardActionBuilder stealPlayer(Player stealPlayer) {
		this.devCardAction.setStealFromPlayer(stealPlayer);
		return this;
	}
	
	public DevCardActionBuilder stealResourceType(ResourceType type) {
		this.devCardAction.setStealResourceType(type);
		return this;
	}
	
	public DevCardAction build() {
		return this.devCardAction;
	}

	public DevCardActionBuilder bank(Bank bank) {
		this.devCardAction.setBank(bank);
		return this;
	}

}
