package it.uniroma1.mdp.uno.model.players;

/**
 * Rappresenta un giocatore umano.
 * 
 * @author Osema Fadhel
 */
public class HumanPlayer extends Player {

	/**
	 * Costruisce un nuovo giocatore umano con il nome specificato.
	 * 
	 * @param name
	 */
	public HumanPlayer(String name) {
		super(name);
	}

	@Override
	public boolean isBot() {
		return false;
	}
}
