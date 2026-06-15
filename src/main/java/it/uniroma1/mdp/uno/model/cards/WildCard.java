package it.uniroma1.mdp.uno.model.cards;

/*
 * Represents Wild and Wild Draw Four cards.
 */
public class WildCard extends Card {

	public WildCard(CardColor color, CardValue value) {
		super(color, value);
	}

	@Override
	public int getPoints() {
		/*
		 * Ai fini del punteggio di fine round, ogni carta Wild e Wild Draw Four vale 50
		 * punti.
		 */
		return 50;
	}

	@Override
	public boolean isPlayable(Card topCard, CardColor currentColor) {
		return true; /* Wild cards can be played on any card */
	}
}
