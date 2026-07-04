package it.uniroma1.mdp.uno.model.players.strategies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Comparator;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.BotStrategy;

/**
 * 
 * Bot con strategia Greedy, cerca di giocare la carta con il valore più alto possibile.
 * Sceglie un colore basato sul colore più presente nella mano quando gioca una carta Wild.
 * Gioca sempre la carta pescata se è giocabile.
 * Sceglie casualmente se sfidare o meno un avversario che ha giocato una carta Wild Draw Four.
 * 
 * @author Osema Fadhel
 */
public class GreedyBotStrategy implements BotStrategy {
	private final Random random = new Random();

	@Override
	public Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor) {
		Card playable = hand.stream().filter(c -> c.isPlayable(topCard, currentColor))
				.max(Comparator.comparingInt(Card::getPoints)).orElse(null);

		return playable;
	}

	@Override
	public CardColor chooseColor(List<Card> hand, CardColor currentColor) {
        CardColor[] colors = { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE };
        CardColor mostFrequentColor = hand.stream().filter(c -> c.getColor() != CardColor.WILD)
                .collect(Collectors.groupingBy(Card::getColor, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(entry -> entry.getValue()))
                .map(entry -> entry.getKey())
                .orElse(colors[random.nextInt(4)]);

		return mostFrequentColor;
	}

	@Override
	public boolean shouldPlayDrawnCard(List<Card> hand, Card topCard, CardColor currentColor) {
		if (hand.get(hand.size() - 1).isPlayable(topCard, currentColor)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldChallenge(List<Card> hand, int targetHand) {
		return random.nextBoolean();
	}

    @Override
    public boolean shouldContestUno() {
        return true;
    }
}