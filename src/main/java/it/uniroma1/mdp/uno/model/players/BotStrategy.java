package it.uniroma1.mdp.uno.model.players;

import java.util.List;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;

public interface BotStrategy {
	/*
	 * Il metodo chooseCard implementa la logica per scegliere quale carta giocare
	 * in base alla mano del bot, alla carta in cima al mazzo e al colore attuale.
	 */
	Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor);

	CardColor chooseColor(List<Card> hand);
}
