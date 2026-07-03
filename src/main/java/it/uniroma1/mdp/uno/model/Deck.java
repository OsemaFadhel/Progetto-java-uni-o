package it.uniroma1.mdp.uno.model;

import java.util.Collections;
import java.util.Stack;

import it.uniroma1.mdp.uno.model.cards.ActionCard;
import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.cards.CardValue;
import it.uniroma1.mdp.uno.model.cards.NumberCard;
import it.uniroma1.mdp.uno.model.cards.WildCard;

/**
 * Rappresenta un mazzo di carte Uno. Il mazzo contiene tutte le carte Uno
 * standard e fornisce metodi per mescolare, pescare e rifornire il mazzo.
 * 
 * @author Osema Fadhel
 */
class Deck {
	private Stack<Card> cards;

	/**
	 * Crea un nuovo mazzo di carte Uno standard.
	 */
	Deck() {
		cards = new Stack<>();
		initializeStandardDeck();
		shuffle();
	}

	/**
	 * Inizializza il mazzo con le carte Uno standard. Aggiunge tutte le carte
	 * numeriche, le carte azione e le carte Wild.
	 */
	private void initializeStandardDeck() {
		/*
		 * We add all NumberCards, then all ActionCards, then all WildCards.
		 */
		for (CardColor color : CardColor.values()) {
			if (color == CardColor.WILD) {
				/*
				 * quattro carte Wild; quattro carte Wild Draw Four.
				 */
				for (int i = 0; i < 4; i++) {
					cards.push(new WildCard(CardColor.WILD, CardValue.WILD));
					cards.push(new WildCard(CardColor.WILD, CardValue.WILD_DRAW_FOUR));
				}
			} else {
				/* Add Number cards (0-9, two of each except 0, and action cards */
				for (CardValue value : CardValue.values()) {
					if (value != CardValue.WILD && value != CardValue.WILD_DRAW_FOUR) {
						boolean isAction = (value == CardValue.SKIP || value == CardValue.REVERSE
								|| value == CardValue.DRAW_TWO);
						if (isAction) {
							cards.push(new ActionCard(color, value));
							cards.push(new ActionCard(color, value));
						} else {
							cards.push(new NumberCard(color, value));
							/* only one 0 card per color */
							if (value != CardValue.ZERO) {
								cards.push(new NumberCard(color, value));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Rifornisce il mazzo con le carte scartate e le mescola.
	 * 
	 * @param discardPile lo stack delle carte scartate
	 */
	void refillDeck(Stack<Card> discardPile) {
		cards.addAll(discardPile);
		discardPile.clear();
		shuffle();
	}

	/**
	 * Mescola le carte nel mazzo.
	 */
	void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * Prende una carta dal mazzo. Se il mazzo è vuoto, lancia un'eccezione. Il
	 * GameEngine si occuperà di rifornire il mazzo dagli scarti e rimescolare.
	 * 
	 * @return Card: la carta pescata
	 */
	Card drawCard() {
		if (cards.isEmpty()) {
			throw new IllegalStateException("The deck is empty. Cannot draw a card.");
		}
		return cards.pop();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
