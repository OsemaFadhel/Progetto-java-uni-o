package it.uniroma1.mdp.uno.model.players;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;

public class BotPlayer extends Player {
	private BotStrategy strategy;

	public BotPlayer(String name, BotStrategy strategy) {
		super(name);
		this.strategy = strategy;
	}

	@Override
	public boolean isBot() {
		return true;
	}

	/*
	 * Il bot sceglie una carta da giocare in base alla strategia implementata.
	 */
	public Card makePlay(Card topCard, CardColor currentColor) {
		/*
		 * La strategia del bot può essere implementata in vari modi, ad esempio: -
		 * Scegliere la prima carta giocabile - Scegliere la carta con il punteggio più
		 * alto - Scegliere la carta che cambia colore se possibile
		 */
		return strategy.chooseCard(this.hand, topCard, currentColor);
	}
}
