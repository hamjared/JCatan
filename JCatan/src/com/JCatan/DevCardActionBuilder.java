package com.JCatan;

import java.util.List;

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
	
	public DevCardActionBuilder stealResourceType1(ResourceType type) {
		this.devCardAction.setStealResourceType1(type);
		return this;
	}
	
	public DevCardActionBuilder stealResourceType2(ResourceType type) {
		this.devCardAction.setStealResourceType2(type);
		return this;
	}
	
	public DevCardActionBuilder gamePlayers(List<Player> players) {
		this.devCardAction.setGamePlayers(players);
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
