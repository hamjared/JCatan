package com.JCatan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SpecialTrade extends Trade {

	private static final long serialVersionUID = -1546670767267731913L;
	Bank bank;
	PortNode node;

	public SpecialTrade(Player offeringPlayer, Bank bank, List<ResourceCard> offeringCards,
			List<ResourceCard> requestingCards) {
		super(offeringPlayer, offeringCards, requestingCards);
		this.bank = bank;
	}

	public Bank getBank() {
		return bank;
	}
	
	@Override
	public void offer(Player player) {
		player.receiveTrade(this);
		bank.receiveTrade(this);
	}

	private Map<ResourceType, Integer> convertListToMap(List<ResourceCard> list) {
		return list.stream().collect(
				Collectors.toMap(r -> r.getResourceType(), r -> 1, (existing, addition) -> existing + addition));
	}

	@Override
	public void validateTrade() throws InvalidTradeException {
		// Check if player has valid amount to trade...
		if (offeringCards.isEmpty() || requestingCards.isEmpty())
			throw new InvalidTradeException("Empty trading cards!");

		Map<ResourceType, Integer> offeringMap = convertListToMap(offeringCards);
		Map<ResourceType, Integer> requestingMap = convertListToMap(requestingCards);

		if (offeringMap.keySet().size() != requestingMap.keySet().size())
			throw new InvalidTradeException("Invalid amount of resources being traded.");

		List<PortNode> ports = new ArrayList<>();
		for (Building b : offeringPlayer.getPlayedBuildings()) {
			if (b.getNode() instanceof PortNode) {
				ports.add((PortNode) b.getNode());
			}
		}

		boolean check = false;
		for (PortNode p : ports) {
			if (p.getPort().getResourceType() == offeringMap.keySet().toArray()[0]) {
				check = true;
			}
			if (offeringCards.size() == 3 && p.getPort().getPortType().equals(Port.PortType.GENERIC)) {
				check = true;
			}
		}
		
		if (check == false) {
			throw new InvalidTradeException("Invalid trade!");
		}

		if (!isTradeValid(offeringMap, requestingMap)) {
			throw new InvalidTradeException("Invalid trade of resources!");
		}

	}

	private boolean isTradeValid(Map<ResourceType, Integer> offeringMap, Map<ResourceType, Integer> requestingMap) {
		// Sort Descending of each map to match the ratios...
		LinkedHashMap<ResourceType, Integer> descendingOffer = new LinkedHashMap<>();
		LinkedHashMap<ResourceType, Integer> descendingRequest = new LinkedHashMap<>();
		
		offeringMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> descendingOffer.put(x.getKey(), x.getValue()));
		requestingMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> descendingRequest.put(x.getKey(), x.getValue()));

		Iterator<Map.Entry<ResourceType, Integer>> iteratorOffer = offeringMap.entrySet().iterator();
		Iterator<Map.Entry<ResourceType, Integer>> iteratorRequest = requestingMap.entrySet().iterator();

		while (iteratorOffer.hasNext() && iteratorRequest.hasNext()) {
			Entry<ResourceType, Integer> offer = iteratorOffer.next();
			Entry<ResourceType, Integer> request = iteratorRequest.next();

			if (!offeringPlayer.hasEnoughResource(offer.getKey(), offer.getValue())) {
				return false;
			}

			// Does the banker have enough to potentially give to player?
			if (request.getValue() > bank.getNumberOfResourceCardsRemaining(request.getKey())) {
				return false;
			}
		}
		return true;
	}

}
