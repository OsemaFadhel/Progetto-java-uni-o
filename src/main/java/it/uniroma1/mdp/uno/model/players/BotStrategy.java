package it.uniroma1.mdp.uno.model.players;

import java.util.List;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;

/**
 * L'interfaccia BotStrategy definisce il comportamento di un bot nel gioco.
 * 
 * Segue il design pattern Strategy, permettendo di avere diverse strategie di
 * gioco per i bot.
 * 
 * @author Osema Fadhel
 */
public interface BotStrategy {
	/**
	 * Sceglie quale carta giocare.
	 * 
	 * @param hand         la mano del bot
	 * @param topCard      la carta in cima al mazzo degli scarti
	 * @param currentColor il colore attuale
	 * 
	 * @return la {@link Card} scelta dal bot da giocare, o null se il bot deve
	 *         pescare.
	 */
	Card chooseCard(List<Card> hand, Card topCard, CardColor currentColor);

	/**
	 * Sceglie quale colore dichiarare quando gioca una carta Wild.
	 * 
	 * @param hand la mano del bot
	 * @return il {@link CardColor} scelto dal bot.
	 */
	CardColor chooseColor(List<Card> hand);
}
