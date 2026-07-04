package it.uniroma1.mdp.uno.model.cards;

/**
 * Rappresenta una carta generica del gioco. Ogni carta ha un colore (rosso,
 * giallo, verde, blu o jolly) e un valore (0-9, Skip, Reverse, Draw Two, Wild,
 * Wild Draw Four).
 * 
 * @author Osema Fadhel
 */
public abstract class Card {
	protected CardColor color;
	protected CardValue value;

	/**
	 * Crea una nuova carta con il colore e il valore specificato.
	 * 
	 * @param color
	 * @param value
	 */
	protected Card(CardColor color, CardValue value) {
		this.color = color;
		this.value = value;
	}

	/**
	 * 
	 * @return {@link CardColor}
	 */
	public CardColor getColor() {
		return color;
	}

	/**
	 * 
	 * @return {@link CardValue}
	 */
	public CardValue getValue() {
		return value;
	}

	/**
	 * Restituisce il punteggio di questa carta ai fini del punteggio di fine round.
	 * 
	 * @return
	 */
	public abstract int getPoints();

	/**
	 * Verifica se questa carta può essere giocata sopra la carta in cima al mazzo
	 * degli scarti, considerando anche il colore attuale (che può essere cambiato
	 * da una carta Wild).
	 * 
	 * @param topCard      la carta in cima al mazzo degli scarti
	 * @param currentColor il colore attuale (può essere diverso dal colore della
	 *                     carta in cima se è stata giocata una carta Wild)
	 * @return true se questa carta può essere giocata, false altrimenti
	 */
	public abstract boolean isPlayable(Card topCard, CardColor currentColor);

	/**
	 * Restituisce il nome del file dell'immagine associata a questa carta.
	 * 
	 * @return il nome del file dell'immagine (es. "RED_5.JPG")
	 */
	public String getImageFileName() {
		return color + "_" + value.getFileName() + ".JPG";
	}
}
