package it.uniroma1.mdp.uno.model.cards;

/**
 * Rappresenta i valori delle carte Uno. Include numeri da 0 a 9, carte azione
 * (Skip, Reverse, Draw Two) e carte jolly (Wild, Wild Draw Four).
 */
public enum CardValue {
	ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),
	SKIP("SKIP"), REVERSE("REVERSE"), DRAW_TWO("DRAW_2"), WILD("WILD"), WILD_DRAW_FOUR("WILD_DRAW_4");

	private final String fileName;

	/**
	 * Costruttore per CardValue.
	 * 
	 * @param fileName parte di nome del file associato a questo valore di carta
	 *                 (es. "0", "SKIP", "WILD_DRAW_4")
	 */
	CardValue(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Restituisce il nome del file associato a questo valore di carta.
	 * 
	 * @return {@link String} parte del nome del file (es. "0", "SKIP",
	 *         "WILD_DRAW_4")
	 */
	public String getFileName() {
		return fileName;
	}
}
