package it.uniroma1.mdp.uno.model.cards;

/**
 * Rappresenta le carte azione del gioco Uno (Skip, Reverse e Draw Two).
 * 
 * @author Osema Fadhel
 */
public class ActionCard extends Card {

	/**
	 * Crea una nuova carta azione con il colore e il valore specificato.
	 * 
	 * @param color il colore della carta (rosso, giallo, verde o blu)
	 * @param value il valore dell'azione (SKIP, REVERSE o DRAW_TWO)
	 */
	public ActionCard(CardColor color, CardValue value) {
		super(color, value);
	}

	/**
	 * Restituisce il punteggio della carta azione. Ai fini del punteggio di fine
	 * round, ogni carta azione vale 20 punti.
	 */
	@Override
	public int getPoints() {
		return 20;
	}

	/**
	 * Controlla se questa carta può essere giocata sopra la carta in cima al mazzo
	 * degli scarti, considerando anche il colore attuale
	 * 
	 * @param topCard      la carta in cima al mazzo degli scarti
	 * @param currentColor il colore attuale
	 * @return true se questa carta può essere giocata, altrimenti false
	 */
	@Override
	public boolean isPlayable(Card topCard, CardColor currentColor) {
		return this.color == currentColor || this.value == topCard.getValue();
	}
}
