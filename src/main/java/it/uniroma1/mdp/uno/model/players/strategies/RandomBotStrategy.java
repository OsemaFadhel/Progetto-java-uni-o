package it.uniroma1.mdp.uno.model.players.strategies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.BotStrategy;

/**
 * 
 * Bot con strategia Casuale, sceglie una carta a caso tra quelle giocabili.
 * Sceglie un colore a caso quando gioca una carta Wild. Sceglie casualmente se
 * giocare la carta pescata o meno. Sceglie casualmente se sfidare o meno un
 * avversario che ha giocato una carta Wild Draw Four.
 * 
 * @author Osema Fadhel
 */
public class RandomBotStrategy implements BotStrategy {
	private final Random random = new Random();

	/**
	 * Sceglie una carta a caso tra quelle giocabili.
	 * 
	 * @param hand
	 * @param topCard
	 * @param currentColor
	 * @return {@link Card}
	 */
	@Override
	public Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor) {
		List<Card> playable = hand.stream().filter(c -> c.isPlayable(topCard, currentColor))
				.collect(Collectors.toList());
		return playable.isEmpty() ? null : playable.get(random.nextInt(playable.size()));
	}

	/**
	 * Sceglie un colore a caso tra rosso, giallo, verde e blu.
	 * 
	 * @param hand
	 * @param currentColor
	 * @return {@link CardColor}
	 */
	@Override
	public CardColor chooseColor(List<Card> hand, CardColor currentColor) {
		CardColor[] colors = { CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE };
		return colors[random.nextInt(4)];
	}

	/**
	 * Sceglie casualmente se giocare la carta pescata o meno.
	 * 
	 * @param hand
	 * @param topCard
	 * @param currentColor
	 * @return true se il bot decide di giocare la carta pescata, false altrimenti
	 */
	@Override
	public boolean shouldPlayDrawnCard(List<Card> hand, Card topCard, CardColor currentColor) {
		if (hand.get(hand.size() - 1).isPlayable(topCard, currentColor)) {
			return random.nextBoolean();
		}
		return false;
	}

	/**
	 * Sceglie casualmente se sfidare o meno un avversario che ha giocato una carta
	 * Wild Draw Four.
	 * 
	 * @param hand
	 * @param targetHand
	 * @return true sfida, false altrimenti
	 */
	@Override
	public boolean shouldChallenge(List<Card> hand, int targetHand) {
		return random.nextBoolean();
	}

	/**
	 * Sceglie casualmente se contestare o meno un avversario che non ha dichiarato
	 * UNO.
	 * 
	 * @return true se il bot decide di contestare, false altrimenti
	 */
	@Override
	public boolean shouldContestUno() {
		return random.nextBoolean();
	}
}