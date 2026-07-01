package it.uniroma1.mdp.uno.model.cards;

/**
 * Rappresenta le carte Wild del gioco. Le carte Wild possono essere giocate su
 * qualsiasi carta e permettono al giocatore di cambiare il colore attuale del
 * gioco.
 * 
 * @author Osema Fadhel
 */
public class WildCard extends Card {

	/**
	 * Crea una nuova carta Wild con il colore e il valore specificati.
	 * 
	 * @param color
	 * @param value
	 */
	public WildCard(CardColor color, CardValue value) {
		super(color, value);
	}

	/**
	 * Restituisce il punteggio della carta Wild. Ai fini del punteggio di fine
	 * round, ogni carta Wild e Wild Draw Four vale 50 punti.
	 */
	@Override
	public int getPoints() {
		return 50;
	}

	/**
	 * Controlla se questa carta può essere giocata sopra la carta in cima al mazzo
	 * degli scarti. Le carte Wild possono essere giocate su qualsiasi carta.
	 * 
	 * @param topCard      la carta in cima al mazzo degli scarti
	 * 
	 * @param currentColor il colore attuale
	 * 
	 * @return true se questa carta può essere giocata, altrimenti false
	 */
	@Override
	public boolean isPlayable(Card topCard, CardColor currentColor) {
		return true;
	}

	@Override
	public String getImageFileName() {
		return value.getFileName() + ".JPG";
	}
}
