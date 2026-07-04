package it.uniroma1.mdp.uno.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.cards.CardValue;
import it.uniroma1.mdp.uno.model.players.BotPlayer;
import it.uniroma1.mdp.uno.model.players.Player;

/**
 * Rappresenta il motore di gioco per una partita di UNO. Gestisce lo stato del
 * gioco, i giocatori, il mazzo di carte e le regole. Il motore di gioco è
 * responsabile di: 1. Inizializzare la partita con i giocatori e il mazzo 2.
 * Gestire il turno di gioco, l'ordine dei giocatori e le azioni 3. Applicare le
 * regole del gioco (ad esempio, effetti delle carte, chiamata di UNO) 4.
 * Determinare il vincitore alla fine della partita
 * 
 * @author Osema Fadhel
 */
public class GameEngine {
	private GameState gameState;
	private List<Player> players;
	private int currentPlayerIndex;
	private Deck deck;
	private Stack<Card> discardPile;
	private CardColor currentColor;
	private CardColor colorBeforeWild;
	private boolean isClockwise;
	private GameMode gameMode;
	private int pointThreshold;
	private boolean madePlay;

	/**
	 * Costruttore per inizializzare il motore di gioco UNO.
	 */
	public GameEngine() {
		this.players = new ArrayList<>();
		this.discardPile = new Stack<>();
		this.gameState = GameState.NOT_STARTED;
		this.pointThreshold = 500;
		this.gameMode = GameMode.SINGLE_GAME;
		this.madePlay = false;
	}

	/**
	 * @return {@link GameState}
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @return {@code List<Player>}
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/**
	 * @return {@link Player}
	 */
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	/**
	 * @return {@link Card}
	 */
	public Card getTopCard() {
		return discardPile.peek();
	}

	/**
	 * @return {@link CardColor}
	 */
	public CardColor getCurrentColor() {
		return currentColor;
	}

	/**
	 * @return {@link CardColor}
	 */
	public CardColor getColorBeforeWild() {
		return colorBeforeWild;
	}

	/**
	 * @return true se il gioco procede in senso orario, false altrimenti
	 */
	public boolean isClockwise() {
		return isClockwise;
	}

	/**
	 * @return {@link GameMode}
	 */
	public GameMode getGameMode() {
		return this.gameMode;
	}

	/**
	 * Imposta la modalità di gioco. Può essere SINGLE_GAME o POINTS_GAME.
	 * 
	 * @param mode modalità di gioco.
	 */
	public void setGameMode(GameMode mode) {
		if (gameState != GameState.NOT_STARTED) {
			throw new IllegalStateException("Non puoi, gioco iniziato!");
		}
		this.gameMode = mode;
	}

	/**
	 * Imposta la soglia di punti per la modalità POINTS_GAME.
	 * 
	 * @param points soglia di punti.
	 */
	public void setPointThreshold(int points) {
		if (gameState != GameState.NOT_STARTED) {
			throw new IllegalStateException("Non puoi, gioco iniziato!");
		}
		this.pointThreshold = points;
	}

	/**
	 * Aggiunge un giocatore alla partita. Deve essere chiamato prima di iniziare la
	 * partita.
	 * 
	 * Massimo 6 giocatori umani possono partecipare a una partita.
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		if (gameState != GameState.NOT_STARTED) {
			throw new IllegalStateException("Partita iniziata, non puoi aggiungere giocatori!");
		}
		if (players.size() >= 6) {
			throw new IllegalStateException("Non puoi aggiungere più di 6 giocatori!");
		}
		boolean exists = players.stream().anyMatch(p -> p.getName().equalsIgnoreCase(player.getName()));
		if (exists) {
			throw new IllegalStateException("Nome già in uso!");
		}
		players.add(player);
	}

	/**
	 * Rimuove un giocatore.
	 * 
	 * @param player
	 */
	public void removePlayer(Player player) {
		if (gameState != GameState.NOT_STARTED) {
			throw new IllegalStateException("Partita iniziata, non puoi rimuovere giocatori!");
		}
		players.remove(player);
	}

	/**
	 * Ripulisce lo stato di gioco.
	 */
	public void resetGame() {
		if (gameState != GameState.GAME_OVER) {
			throw new IllegalStateException("Gioco in corso!");
		}
		this.players.clear();
		this.discardPile.clear();
		this.gameMode = GameMode.SINGLE_GAME;
		this.pointThreshold = 500;
		this.gameState = GameState.NOT_STARTED;
		this.currentColor = null;
		this.colorBeforeWild = null;
	}

	/**
	 * Inizia la partita.
	 */
	public void startGame() {
		if (gameState != GameState.NOT_STARTED) {
			throw new IllegalStateException("Gioco in corso!");
		}
		if (players.size() < 2) {
			throw new IllegalStateException("Servono almeno due giocatori per iniziare la partita.");
		}

		for (Player p : players) {
			p.resetPoints();
		}

		initRound();

		gameState = GameState.WAITING_FOR_PLAYER_ACTION;
	}

	/**
	 * Inizializza un nuovo round di gioco. Distribuisce le carte ai giocatori e
	 * posiziona la prima carta sul mazzo degli scarti.
	 */
	private void initRound() {
		this.currentPlayerIndex = 0;
		this.deck = new Deck();
		this.discardPile.clear();
		this.isClockwise = true;
		this.madePlay = false;

		for (Player player : players) {
			player.clearHand();
			for (int i = 0; i < 7; i++) {
				player.addCard(deck.drawCard());
			}
		}
		showFirstCard();
	}

	/**
	 * Mostra la prima carta del mazzo degli scarti. Se è una carta Wild Draw Four,
	 * rimescola e pesca un'altra carta finché non si ottiene una carta valida.
	 */
	private void showFirstCard() {
		Card firstCard = deck.drawCard();

		while (firstCard.getColor() == CardColor.WILD) {
			discardPile.push(firstCard);
			deck.refillDeck(discardPile);
			firstCard = deck.drawCard();
		}
		discardPile.push(firstCard);
		currentColor = firstCard.getColor();
	}

	/**
	 * Pesca dal mazzo.
	 * 
	 * @param player
	 */
	public void drawDuringTurn(Player player) {
		if (gameState != GameState.WAITING_FOR_PLAYER_ACTION) {
			throw new IllegalStateException("Non puoi pescare una carta al momento!");
		}
		if (player != getCurrentPlayer()) {
			throw new IllegalArgumentException("Non è il turno di questo giocatore!");
		}
		drawCards(player, 1);
		this.madePlay = true;
	}

	/**
	 * Passa il turno al giocatore successivo.
	 */
	public void passTurn() {
		if (gameState != GameState.WAITING_FOR_PLAYER_ACTION) {
			throw new IllegalStateException("Non puoi skippare il turno!");
		}
		if (!madePlay) {
			throw new IllegalStateException("Devi Pescare o Giocare una Carta!");
		}
		moveToNextPlayer();
	}

	/**
	 * Gioca una carta dal giocatore corrente. Applica gli effetti della carta e
	 * passa al turno successivo.
	 * 
	 * @param player
	 * @param card
	 */
	public void playCard(Player player, Card card) {
		if (gameState != GameState.WAITING_FOR_PLAYER_ACTION) {
			throw new IllegalStateException("Non puoi giocare una carta al momento!");
		}
		if (player != getCurrentPlayer()) {
			throw new IllegalArgumentException("Non è il turno di questo giocatore!");
		}
		if (!card.isPlayable(getTopCard(), currentColor)) {
			throw new IllegalArgumentException("Carta non giocabile!");
		}

		player.removeCard(card);
		discardPile.push(card);

		madePlay = true;

		if (player.getHandSize() == 0) {
			this.gameState = GameState.ROUND_OVER;
			return;
		}

		/*
		 * maybe change with shouldCallUno()
		 */
		if (player.isBot() && player.getHandSize() == 1) {
			callUno(player);
		}

		if (card.getColor() == CardColor.WILD) {
			this.colorBeforeWild = this.currentColor;

			if (player.isBot()) {
				BotPlayer botPlayer = (BotPlayer) player;
				this.currentColor = botPlayer.chooseWildColor(currentColor);
				if (card.getValue() == CardValue.WILD_DRAW_FOUR) {
					this.gameState = GameState.WAITING_FOR_CHALLENGE;
					return;
				} else {
					moveToNextPlayer();
				}
			} else {
				this.gameState = GameState.WAITING_FOR_COLOR_CHOICE;
				return;
			}
		} else {
			applyCardEffect(card);
			this.currentColor = card.getColor();
			moveToNextPlayer();
		}
	}

	/**
	 * Sceglie un colore per una carta Wild. Deve essere chiamato dopo aver giocato
	 * una carta Wild.
	 * 
	 * @param chosenColor
	 */
	public void setWildColor(CardColor chosenColor) {
		if (gameState != GameState.WAITING_FOR_COLOR_CHOICE) {
			throw new IllegalStateException("Non puoi scegliere un colore! Gioca una carta Wild!");
		}
		this.currentColor = chosenColor;
		if (getTopCard().getValue() == CardValue.WILD_DRAW_FOUR) {
			this.gameState = GameState.WAITING_FOR_CHALLENGE;
		} else {
			moveToNextPlayer();
			this.gameState = GameState.WAITING_FOR_PLAYER_ACTION;
		}
	}

	/**
	 * Sfida una carta Wild Draw Four giocata da un altro giocatore. Deve essere
	 * chiamato dopo che un giocatore ha giocato una carta Wild Draw Four.
	 * 
	 * @param doChallenge
	 */
	public void solveChallenge(boolean doChallenge) {
		if (gameState != GameState.WAITING_FOR_CHALLENGE) {
			throw new IllegalStateException("Carta Wild 4 deve essere stata giocata!");
		}
		Player currentPlayer = getCurrentPlayer();
		Player challenger = getTargetPlayer();

		if (doChallenge) {
			if (isWildDrawFourLegal()) {
				drawCards(challenger, 6);
				moveToNextPlayer();
				moveToNextPlayer();
			} else {
				this.currentColor = colorBeforeWild;
				drawCards(currentPlayer, 4);
				moveToNextPlayer();
			}
		} else {
			drawCards(challenger, 4);
			moveToNextPlayer();
			moveToNextPlayer();
		}
		this.gameState = GameState.WAITING_FOR_PLAYER_ACTION;
	}

	/**
	 * Controlla se la carta Wild Draw Four giocata è legale. Una carta Wild Draw
	 * Four è legale se il giocatore che l'ha giocata non aveva altre carte dello
	 * stesso colore della carta in cima al mazzo degli scarti.
	 * 
	 * @return true se la carta Wild Draw Four è legale, false altrimenti
	 */
	private boolean isWildDrawFourLegal() {
		return getCurrentPlayer().getHand().stream().noneMatch(c -> c.getColor() == colorBeforeWild);
	}

	/**
	 * Pesca le carte dal deck e le assegna al giocatore
	 * 
	 * @param player giocatore che deve pescare
	 * @param count  numero di carte da pescare
	 */
	private void drawCards(Player player, int count) {
		for (int i = 0; i < count; i++) {
			if (deck.isEmpty()) {
				Card topCard = discardPile.pop();
				deck.refillDeck(discardPile);
				discardPile.push(topCard);
			}
			player.addCard(deck.drawCard());
		}
	}

	/**
	 * Applica l'azione della carta (REVERSE, SKIP, DRAW_2)
	 * 
	 * @param card carta giocata
	 */
	private void applyCardEffect(Card card) {
		switch (card.getValue()) {
		case REVERSE:
			isClockwise = !isClockwise;
			break;
		case SKIP:
			moveToNextPlayer();
			break;
		case DRAW_TWO:
			Player targetTwo = getTargetPlayer();
			drawCards(targetTwo, 2);
			moveToNextPlayer();
			break;
		default:
			break;
		}
	}

	/**
	 * Restituisce il giocatore target per le carte azione (DRAW_2, WILD_DRAW_FOUR).
	 *
	 * @return Player Il giocatore target
	 */
	public Player getTargetPlayer() {
		int targetIndex;
		if (isClockwise) {
			targetIndex = (currentPlayerIndex + 1) % players.size();
		} else {
			targetIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
		}
		return players.get(targetIndex);
	}

	/**
	 * Restituisce il giocatore precedente.
	 * 
	 * @return {@link Player} Il giocatore precedente
	 */
	private Player getPreviousPlayer() {
		int targetIndex;
		if (isClockwise) {
			targetIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
		} else {
			targetIndex = (currentPlayerIndex + 1) % players.size();
		}
		return players.get(targetIndex);
	}

	/**
	 * Passa al prossimo giocatore in base alla direzione del gioco (oraria o
	 * antioraria)
	 */
	private void moveToNextPlayer() {
		if (isClockwise) {
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		} else {
			currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
		}
		madePlay = false;
	}

	/**
	 * Permette a un giocatore di chiamare "UNO" se ha una o due carte in mano.
	 * 
	 * @param player
	 */
	public void callUno(Player player) {
		if (player.getHandSize() == 1 || player.getHandSize() == 2) {
			player.setUnoCalled(true);
		} else {
			throw new IllegalStateException("Non puoi chiamare UNO con più di due carte in mano.");
		}
	}

	/**
	 * Se un giocatore ha una sola carta in mano e non ha chiamato "UNO", deve
	 * pescare due carte come penalità se contestato.
	 * 
	 * @return true se il giocatore precedente ha una carta in mano e non ha
	 *         chiamato UNO, false altrimenti
	 */
	public boolean contestUno() {
		Player prevPlayer = getPreviousPlayer();
		if (prevPlayer.hasOneCard() && !prevPlayer.isUnoCalled()) {
			drawCards(prevPlayer, 2);
			return true;
		}
		return false;
	}

	/**
	 * Calcola i punti del round per il vincitore. I punti sono calcolati sommando
	 * il valore delle carte rimanenti in mano agli altri giocatori.
	 * 
	 * @return score punti del round
	 */
	public int calculateRoundScore(Player winner) {
		if (gameState != GameState.ROUND_OVER) {
			throw new IllegalStateException("Gioco in corso!");
		}
		int score = 0;
		for (Player p : players) {
			if (p != winner) {
				for (Card c : p.getHand()) {
					score += c.getPoints();
				}
			}
		}
		winner.addPoints(score);
		if (gameMode == GameMode.SINGLE_GAME) {
			gameState = GameState.GAME_OVER;
		}
		if (gameMode == GameMode.POINTS_GAME && winner.getPoints() >= pointThreshold) {
			this.gameState = GameState.GAME_OVER;
		}
		return score;
	}

	/**
	 * Inizia un nuovo round.
	 */
	public void startNewRound() {
		if (gameState != GameState.ROUND_OVER) {
			throw new IllegalStateException("Gioco in corso!");
		}

		initRound();

		gameState = GameState.WAITING_FOR_PLAYER_ACTION;
	}
}
