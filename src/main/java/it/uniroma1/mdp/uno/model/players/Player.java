package it.uniroma1.mdp.uno.model.players;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.uniroma1.mdp.uno.model.cards.Card;

/**
 * Rappresenta un giocatore del gioco. Un giocatore ha un nome e una mano di
 * carte.
 * 
 * @author Osema Fadhel
 */
public abstract class Player {
	protected String name;
	protected List<Card> hand;
	protected int points;
	protected boolean unoCalled;

	/**
	 * Crea un nuovo giocatore con il nome specificato. Inizialmente, la mano del
	 * giocatore è vuota e i punti sono 0.
	 * 
	 * @param name il nome del giocatore
	 */
	public Player(String name) {
		if (name.length() > 8) {
			throw new IllegalArgumentException("Nome troppo lungo");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Nome vuoto");
		}
		this.name = name;
		this.hand = new ArrayList<>();
		this.points = 0;
		this.unoCalled = false;
	}

	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return Collections.unmodifiableList(hand);
	}

	public int getPoints() {
		return points;
	}

	/**
	 * Aggiunge punti al totale del giocatore.
	 * 
	 * @param points i punti da aggiungere
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	/**
	 * Resetta i punti del giocatore a 0 (ad esempio, all'inizio di una nuova
	 * partita).
	 */
	public void resetPoints() {
		this.points = 0;
	}

	/**
	 * Imposta lo stato di "UNO" chiamato. Se uno stato true viene impostato quando
	 * il giocatore non ha una sola carta in mano, viene lanciata un'eccezione.
	 * 
	 * @param unoCalled
	 * 
	 * @throws IllegalStateException se si tenta di chiamare UNO quando il giocatore
	 *                               non ha una sola carta in mano
	 */
	public void setUnoCalled(boolean unoCalled) {
		if (unoCalled && hand.size() > 2) {
			throw new IllegalStateException("Non puoi chiamare UNO se non hai una sola carta in mano!");
		}
		this.unoCalled = unoCalled;
	}

	/**
	 * Restituisce lo stato di "UNO" chiamato.
	 * 
	 * @return
	 */
	public boolean isUnoCalled() {
		return unoCalled;
	}

	/**
	 * Aggiunge una carta alla mano del giocatore. Resetta lo stato di "UNO"
	 * chiamato a false, poiché il giocatore ha ora più di una carta in mano.
	 * 
	 * @param card la carta da aggiungere
	 */
	public void addCard(Card card) {
		this.hand.add(card);
		this.unoCalled = false;
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
