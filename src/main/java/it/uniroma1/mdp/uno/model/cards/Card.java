package it.uniroma1.mdp.uno.model.cards;

public abstract class Card {
	protected CardColor color;
	protected CardValue value;

	public Card(CardColor color, CardValue value) {
		this.color = color;
		this.value = value;
	}

	public CardColor getColor() {
		return color;
	}

	public CardValue getValue() {
		return value;
	}

	public abstract int getPoints();

	public abstract boolean isPlayable(Card topCard, CardColor currentColor);

	/* To help retrieve image asset (e.g. BLUE_0.JPG) */
	public String getImageFileName() {
		return color + "_" + value + ".JPG";
	}
}
