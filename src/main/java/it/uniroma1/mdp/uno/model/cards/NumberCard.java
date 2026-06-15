package it.uniroma1.mdp.uno.model.cards;

public class NumberCard extends Card {

	public NumberCard(CardColor color, CardValue value) {
		super(color, value);
	}

	@Override
	public int getPoints() {
		/*
		 * Ai fini del punteggio di fine round, ogni carta numerica vale il proprio
		 * valore. Ad esempio, una carta 7 vale 7 punti.
		 */
		return Integer.parseInt(value.getFileName());
	}

	@Override
	public boolean isPlayable(Card topCard, CardColor currentColor) {
		return this.color == currentColor || this.value == topCard.getValue();
	}
}
