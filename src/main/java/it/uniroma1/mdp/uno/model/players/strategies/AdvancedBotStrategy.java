package it.uniroma1.mdp.uno.model.players.strategies;
import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.BotStrategy;
import java.util.List;

public class AdvancedBotStrategy implements BotStrategy {
	@Override
	public Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor) {
		return null; // TODO
	}
	@Override
	public CardColor chooseColor(List<Card> hand) {
		return CardColor.RED; // TODO
	}
}