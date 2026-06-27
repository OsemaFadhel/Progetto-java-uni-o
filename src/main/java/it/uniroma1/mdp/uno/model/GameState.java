package it.uniroma1.mdp.uno.model;

/**
 * Rappresenta lo stato della partita di UNO.
 */
public enum GameState {
	NOT_STARTED, WAITING_FOR_PLAYER_ACTION, WAITING_FOR_COLOR_CHOICE, WAITING_FOR_CHALLENGE, ROUND_OVER, GAME_OVER
}
