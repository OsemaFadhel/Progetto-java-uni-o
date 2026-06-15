package it.uniroma1.mdp.uno.model.cards;

/*
 * Represents an action card (SKIP, REVERSE, DRAW_2).
 */
public class ActionCard extends Card {

	public ActionCard(CardColor color, CardValue value) {
		super(color, value);
	}

	@Override
	public int getPoints() {
		/*
		 * Ai fini del punteggio di fine round, ogni carta Skip, Reverse, Draw Two vale
		 * 20 punti.
		 */
		return 20;
	}

	@Override
	public boolean isPlayable(Card topCard, CardColor currentColor) {
		return this.color == currentColor || this.value == topCard.getValue();
	}
}
