package it.uniroma1.mdp.uno.model.players;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;

/**
 * Rappresenta un giocatore bot. Il bot delega tutte le scelte di gioco a un
 * oggetto {@link BotStrategy} iniettato al momento della sua creazione. Questo
 * rende il comportamento del bot facilmente intercambiabile.
 * 
 * @author Osema Fadhel
 */
public class BotPlayer extends Player {
	private BotStrategy strategy;

	/**
	 * Crea un nuovo giocatore Bot con il nome specificato e la strategia di gioco
	 * scelta.
	 * 
	 * @param name     il nome del bot
	 * @param strategy la strategia di gioco del bot
	 */
	public BotPlayer(String name, BotStrategy strategy) {
		super(name);
		this.strategy = strategy;
	}

	@Override
	public boolean isBot() {
		return true;
	}

	/**
	 * Sceglie quale carta giocare, in base alla strategia del bot.
	 * 
	 * Il bot utilizza la sua strategia per decidere quale carta giocare.
	 * 
	 * @param topCard      la carta in cima al mazzo degli scarti
	 * @param currentColor il colore attuale
	 * 
	 * @return la {@link Card} scelta dal bot da giocare, o null se il bot deve
	 *         pescare.
	 */
	public Card chooseCardPlay(Card topCard, CardColor currentColor) {
		return strategy.chooseCard(this.hand, topCard, currentColor);
	}

	/**
	 * Sceglie quale colore dichiarare quando gioca una carta Wild, in base alla
	 * strategia del bot
	 * 
	 * @return il {@link CardColor} scelto dal bot.
	 */
	public CardColor chooseWildColor(CardColor currentColor) {
		return strategy.chooseColor(this.hand, currentColor);
	}

	/**
	 * Sceglie se giocare la carta pescata, in base alla strategia del bot
	 *
	 * @return true se il bot decide di giocarla, false altrimenti.
	 */
	public boolean shouldPlayDrawnCard(Card topCard, CardColor currentColor) {
		return strategy.shouldPlayDrawnCard(this.hand, topCard, currentColor);
	}

	/**
	 * Sceglie se sfidare o meno una carta WILD DRAW FOUR, in base alla strategia
	 * del bot
	 * 
	 * @return true se il bot decide di sfidare, false altrimenti.
	 */
	public boolean shouldChallenge(int targetHand) {
		return strategy.shouldChallenge(this.hand, targetHand);
	}

	/**
	 * Sceglie se contestare o meno un avversario che non ha dichiarato UNO, in base
	 * alla strategia
	 * 
	 * @return true se il bot decide di contestare, false altrimenti.
	 */
	public boolean shouldContestUno() {
		return strategy.shouldContestUno();
	}
}
