package it.uniroma1.mdp.uno.model.players.strategies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.BotStrategy;

/**
 * 
 * RandomBotStrategy
 * 
 * @author Osema Fadhel
 */
public class RandomBotStrategy implements BotStrategy {
	private final Random random = new Random();

	@Override
	public Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor) {
		List<Card> playable = hand.stream().filter(c -> c.isPlayable(topCard, currentColor))
				.collect(Collectors.toList());
		return playable.isEmpty() ? null : playable.get(random.nextInt(playable.size()));
	}

	@Override
	public CardColor chooseColor(List<Card> hand, CardColor currentColor) {
		CardColor[] colors = { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE };
		return colors[random.nextInt(4)];
	}

	@Override
	public boolean shouldPlayDrawnCard(List<Card> hand, Card topCard, CardColor currentColor) {
		if (hand.get(hand.size() - 1).isPlayable(topCard, currentColor)) {
			return random.nextBoolean();
		}
		return false;
	}

	@Override
	public boolean shouldChallenge(List<Card> hand, int targetHand) {
		return random.nextBoolean();
	}
}