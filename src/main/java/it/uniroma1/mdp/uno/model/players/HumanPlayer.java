package it.uniroma1.mdp.uno.model.players;

public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
	}

	@Override
	public boolean isBot() {
		return false;
	}
}
