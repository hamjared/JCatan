package com.JCatan;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class MaritimeTrade extends Trade {

	private static final long serialVersionUID = -2035915789482825387L;
	private Bank banker;
	private final int defaultMultiple = 4;

	// NOTE: Always available 4:1 trade with banks.
	public MaritimeTrade(Player offeringPlayer, Bank banker, List<ResourceCard> offeringCards,
			List<ResourceCard> requestingCards) {
		super(offeringPlayer, offeringCards, requestingCards);
		this.banker = banker;
	}

	public Bank getBank() {
		return banker;
	}
	
	@Override
	public void offer(Player player) {
		player.receiveTrade(this);
		banker.receiveTrade(this);
	}

	private boolean isTradeValid(Map<ResourceType, Integer> offeringMap, Map<ResourceType, Integer> requestingMap,
			BiPredicate<Entry<ResourceType, Integer>, Entry<ResourceType, Integer>> tester) {
		
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
			if (request.getValue() > banker.getNumberOfResourceCardsRemaining(request.getKey())) {
				return false;
			}

			if (!tester.test(request, offer)) {
				return false;
			}
		}
		return true;
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

		// Note R -> Requesting Cards | O -> Offering Cards
		if (!isTradeValid(offeringMap, requestingMap, (R, O) -> R.getValue() * defaultMultiple == O.getValue())) {
			throw new InvalidTradeException("Invalid trade of resources!");
		}
	}
}