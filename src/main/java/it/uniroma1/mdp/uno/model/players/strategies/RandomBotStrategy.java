package it.uniroma1.mdp.uno.model.players.strategies;
import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.BotStrategy;
import java.util.*;
import java.util.stream.Collectors;

public class RandomBotStrategy implements BotStrategy {
	private final Random random = new Random();
	@Override
	public Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor) {
		List<Card> playable = hand.stream()
			.filter(c -> c.isPlayable(topCard, currentColor))
			.collect(Collectors.toList());
		return playable.isEmpty() ? null : playable.get(random.nextInt(playable.size()));
	}
	@Override
	public CardColor chooseColor(List<Card> hand) {
		CardColor[] colors = {CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE};
		return colors[new Random().nextInt(4)];
	}
}