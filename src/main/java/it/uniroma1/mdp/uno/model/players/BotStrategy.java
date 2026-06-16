package it.uniroma1.mdp.uno.model.players;

import java.util.List;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;

/**
 * L'interfaccia BotStrategy definisce il comportamento di un bot nel gioco. *
 * Implementa la logica per scegliere quale carta giocare e quale colore
 * dichiarare quando gioca una carta Wild.
 * 
 * Segue il design pattern Strategy, permettendo di avere diverse strategie di
 * gioco per i bot.
 */
public interface BotStrategy {
	/*
	 * Il metodo chooseCard implementa la logica per scegliere quale carta giocare
	 * in base alla mano del bot, alla carta in cima al mazzo e al colore attuale.
	 */
	Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor);

	CardColor chooseColor(List<Card> hand);
}
