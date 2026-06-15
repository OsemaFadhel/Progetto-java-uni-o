package it.uniroma1.mdp.uno.model;

import java.util.Collections;
import java.util.Stack;

import it.uniroma1.mdp.uno.model.cards.ActionCard;
import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.cards.CardValue;
import it.uniroma1.mdp.uno.model.cards.NumberCard;
import it.uniroma1.mdp.uno.model.cards.WildCard;

class Deck {
	private Stack<Card> cards;

	/*
	 * Initializes the deck with a stack of cards and then shuffles it. Ready to be
	 * drawn from.
	 */
	public Deck() {
		cards = new Stack<>();
		initializeStandardDeck();
		shuffle();
	}

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

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card drawCard() {
		if (cards.isEmpty()) {
			/*
			 * Handle the case when the deck is empty. Game engine will get from the discard
			 * pile and shuffle it back into the deck.
			 */
			throw new IllegalStateException("The deck is empty. Cannot draw a card.");
		}
		/* remove and return the top card from the deck */
		return cards.pop();
	}

}
