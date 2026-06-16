package it.uniroma1.mdp.uno.model.players;

import java.util.ArrayList;
import java.util.List;

import it.uniroma1.mdp.uno.model.cards.Card;

public abstract class Player {
	protected String name;
	protected List<Card> hand;
	protected int points;

	public Player(String name) {
		this.name = name;
		this.hand = new ArrayList<>();
		this.points = 0;
	}

	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand;
	}

	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	/**
	 * Aggiunge una carta alla mano del giocatore.
	 * 
	 * @param card la carta da aggiungere
	 */
	public void addCard(Card card) {
		this.hand.add(card);
	}

	/**
	 * Rimuove una carta dalla mano del giocatore.
	 * 
	 * @param card la carta da rimuovere
	 */
	public void removeCard(Card card) {
		this.hand.remove(card);
	}

	/**
	 * Restituisce il numero di carte in mano (utile per la View)
	 * 
	 * @return il numero di carte in mano
	 */
	public int getHandSize() {
		return this.hand.size();
	}

	/**
	 * Verifica se il giocatore ha UNO (ovvero se ha una sola carta in mano).
	 * 
	 * @return true se il giocatore ha UNO, false altrimenti
	 */
	public boolean hasUno() {
		return this.hand.size() == 1;
	}

	/**
	 * Svuota la mano del giocatore (ad esempio, alla fine di una partita).
	 */
	public void clearHand() {
		this.hand.clear();
	}

	/**
	 * Indica se questo giocatore è un bot o un giocatore umano.
	 * 
	 * @return true se è un bot, false se è un giocatore umano
	 */
	public abstract boolean isBot();
}
