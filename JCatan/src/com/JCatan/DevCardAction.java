package com.JCatan;

import java.io.Serializable;
import java.util.List;

public class DevCardAction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player curPlayer;
	private Player stealFromPlayer;
	private ResourceType stealResourceType1;
	private ResourceType stealResourceType2;
	private Bank bank;
	private List<Player> gamePlayers;
	public List<Player> getGamePlayers() {
		return gamePlayers;
	}
	public void setGamePlayers(List<Player> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}
	public ResourceType getStealResourceType1() {
		return stealResourceType1;
	}
	public void setStealResourceType1(ResourceType stealResourceType1) {
		this.stealResourceType1 = stealResourceType1;
	}
	public ResourceType getStealResourceType2() {
		return stealResourceType2;
	}
	public void setStealResourceType2(ResourceType stealResourceType2) {
		this.stealResourceType2 = stealResourceType2;
	}
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
		return stealResourceType1;
	}
	public void setStealResourceType(ResourceType stealResourceType) {
		this.stealResourceType1 = stealResourceType;
	}
	
	
	

}
