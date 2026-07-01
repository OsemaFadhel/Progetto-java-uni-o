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

	public boolean isBot() {
		return true;
	}

	/**
	 * Sceglie quale carta giocare.
	 * 
	 * Il bot utilizza la sua strategia per decidere quale carta giocare.
	 * 
	 * @param topCard      la carta in cima al mazzo degli scarti
	 * @param currentColor il colore attuale
	 * 
	 * @return la {@link Card} scelta dal bot da giocare, o null se il bot deve
	 *         pescare.
	 */
	public Card makePlay(Card topCard, CardColor currentColor) {
		return strategy.chooseCard(this.hand, topCard, currentColor);
	}

	/**
	 * Sceglie quale colore dichiarare quando gioca una carta Wild.
	 * 
	 * @return il {@link CardColor} scelto dal bot.
	 */
	public CardColor chooseWildColor() {
		return strategy.chooseColor(this.hand);
	}

	/* deve essere abstract? controllare se deve anche essere private o protected anche in tutti gli altri metodi */
	public boolean shouldChallenge() {
	
	}
}
