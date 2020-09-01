package com.JCatan;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Robber {
	Tile targetTile;

	public Robber() {
		targetTile = null;
	}

	public void move(Tile tile) {
		if(targetTile != null)
			targetTile.setHasRobber(false);
		
		targetTile = tile;
		targetTile.setHasRobber(true);
	}

	public void rob(Player currentPlayer) {
		if (targetTile != null && currentPlayer != null) {
			// Get Neighbor tiles...
			List<Player> players = targetTile.getNodes().stream().filter(n -> n.getBuilding() != null)
					.map(n -> n.getBuilding().getPlayer()).distinct().filter(p -> p != currentPlayer)
					.collect(Collectors.toList());

			// No one to steal...
			if (players.isEmpty()) {
				return;
			}

			/*
			 * List<Tile> tiles = targetTile.getNodes().stream().flatMap(n ->
			 * n.getTiles().stream()).distinct() .filter(t ->
			 * !t.equals(targetTile)).collect(Collectors.toList());
			 * 
			 * List<Player> players = tiles.stream().flatMap(x ->
			 * x.getNodes().stream()).distinct() .filter(n -> n.getBuilding() != null).map(n
			 * -> n.getBuilding().getPlayer()) .filter(p -> p !=
			 * currentPlayer).distinct().collect(Collectors.toList());
			 */

			// For now just Randomly select a player then randomly select resource
			Random random = new Random();
			for (Player victim : players) {
				if (!victim.getResources().isEmpty()) {
					ResourceCard card = victim.getResources().get(random.nextInt(victim.getResources().size()));
					victim.removeResource(card.getResourceType());
					List<ResourceCard> cards = currentPlayer.getResources();
					cards.add(card);
					currentPlayer.setResources(cards);
					break;
				}
			}
		}
	}
}