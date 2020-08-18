package com.JCatan;

public class DevCardAction {
	
	private Player curPlayer;
	private Player stealFromPlayer;
	private ResourceType stealResourceType;
	private Bank bank;
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public Player getCurPlayer() {
		return curPlayer;
	}
	public void setCurPlayer(Player curPlayer) {
		this.curPlayer = curPlayer;
	}
	public Player getStealFromPlayer() {
		return stealFromPlayer;
	}
	public void setStealFromPlayer(Player stealFromPlayer) {
		this.stealFromPlayer = stealFromPlayer;
	}
	public ResourceType getStealResourceType() {
		return stealResourceType;
	}
	public void setStealResourceType(ResourceType stealResourceType) {
		this.stealResourceType = stealResourceType;
	}
	
	
	

}
