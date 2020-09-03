package com.JCatan;

import java.util.List;

public class MonopolyDevelopmentCard extends DevelopmentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException {
		ResourceType rt = devCardAction.getStealResourceType();
		List<Player> players = devCardAction.getGamePlayers();

		Player curPlayer = devCardAction.getCurPlayer();

		for (Player p : players) {
			if (curPlayer.equals(p)) {
				continue;
			}
			Player stealFromPlayer = p;
			int numCards = (int) stealFromPlayer.getResources().stream().filter(rc -> rc.getResourceType() == rt)
					.count();
			for (int i = 0; i < numCards; i++) {
				curPlayer.getResources().add(new ResourceCard(rt));
			}

			stealFromPlayer.getResources().removeIf(rc -> rc.getResourceType() == rt);
		}
	}

	public String toString() {
		return "Monopoly";
	}

}
