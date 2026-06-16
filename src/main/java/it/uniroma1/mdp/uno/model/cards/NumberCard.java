package it.uniroma1.mdp.uno.model.cards;

/**
 * Rappresenta le carte numeriche del gioco. Ogni carta numerica ha un colore
 * (rosso, giallo, verde o blu) e un valore numerico (0-9).
 * 
 * @author Osema Fadhel
 */
public class NumberCard extends Card {
	/**
	 * Crea una nuova carta numerica con il colore e il valore specificati.
	 * 
	 * @param color il colore della carta (rosso, giallo, verde o blu)
	 * @param value il valore numerico della carta (0-9)
	 */
	public NumberCard(CardColor color, CardValue value) {
		super(color, value);
	}

	/**
	 * Restituisce il punteggio della carta numerica. Ai fini del punteggio di fine
	 * round, ogni carta numerica vale il proprio valore. Ad esempio, una carta 7
	 * vale 7 punti.
	 */
	@Override
	public int getPoints() {
		return Integer.parseInt(value.getFileName());
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
