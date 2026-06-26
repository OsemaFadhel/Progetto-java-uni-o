package it.uniroma1.mdp.uno.model;

import java.util.ArrayList;
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
	private List<Player> players;
	private int currentPlayerIndex;
	private Deck deck;
	private Stack<Card> discardPile;
	private CardColor currentColor;
	private boolean isClockwise;

	public GameEngine() {
		this.players = new ArrayList<>();
		this.discardPile = new Stack<>();
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
		if (players.size() >= 6) {
			throw new IllegalStateException("Non puoi aggiungere più di 6 giocatori!");
		}
		players.add(player);
	}

	/**
	 * Inizia la partita. Distribuisce le carte ai giocatori e posiziona la prima
	 * carta sul mazzo degli scarti.
	 */
	public void startGame() {
		if (players.size() < 2) {
			throw new IllegalStateException("Servono almeno due giocatori per iniziare la partita.");
		}

		this.currentPlayerIndex = 0;
		this.deck = new Deck();
		this.discardPile.clear();
		this.isClockwise = true;

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

		while (firstCard.getValue() == CardValue.WILD_DRAW_FOUR) {
			discardPile.push(firstCard);
			deck.refillDeck(discardPile);
			firstCard = deck.drawCard();
		}
		discardPile.push(firstCard);
		currentColor = firstCard.getColor();
	}

	/**
	 * Gioca una carta dal giocatore corrente. Applica gli effetti della carta e
	 * passa al turno successivo.
	 * 
	 * @param player
	 * @param card
	 */
	public void playCard(Player player, Card card) {
		if (!card.isPlayable(discardPile.peek(), currentColor)) {
			throw new IllegalArgumentException("Carta non giocabile!");
		}

		player.removeCard(card);
		discardPile.push(card);

		if (player.getHandSize() == 0) {
			/*
			 * calcolare i Punti? da vedere meglio la vittoria.
			 */
			return;
		}

		if (player.isBot() && player.getHandSize() == 1) {
			callUno(player);
		}

		/*
		 * apply effects of action cards (SKIP, REVERSE, DRAW_2, WILD_DRAW_FOUR)
		 */
		applyCardEffect(card);

		if (card.getColor() == CardColor.WILD) {
			// For Wild cards, the player must choose a color
			this.currentColor = CardColor.WILD;

			if (player.isBot()) {
				BotPlayer botPlayer = (BotPlayer) player;
				CardColor chosenColor = botPlayer.chooseWildColor();
				setWildColor(chosenColor);
				moveToNextPlayer();
			} else {
				/*
				 * For Human players, controller will do the job remember to add a timer like 10
				 * seconds to choose a color, otherwise the game will automatically choose a
				 * color for the player (randomly)
				 */
				return;
			}
		} else {
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
		this.currentColor = chosenColor;
	}

	/**
	 * Pesca le carte dal deck e le assegna al giocatore
	 * 
	 * @param player giocatore che deve pescare
	 * @param count  numero di carte da pescare
	 */
	public void drawCards(Player player, int count) {
		for (int i = 0; i < count; i++) {
			try {
				player.addCard(deck.drawCard());
			} catch (IllegalStateException e) {
				Card topCard = discardPile.pop();
				deck.refillDeck(discardPile);
				discardPile.push(topCard);
				player.addCard(deck.drawCard());
			}
		}
	}

	/**
	 * Applica l'azione della carta (REVERSE, SKIP, DRAW_2, WILD_DRAW_FOUR)
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
		case WILD_DRAW_FOUR:
			Player targetFour = getTargetPlayer();
			drawCards(targetFour, 4);
			moveToNextPlayer();
			break;
		default:
			break;
		}
	}

	/**
	 * Ottiene il giocatore target per le carte azione (DRAW_2, WILD_DRAW_FOUR)
	 * 
	 * @return Player Il giocatore target
	 */
	private Player getTargetPlayer() {
		int targetIndex;
		if (isClockwise) {
			targetIndex = (currentPlayerIndex + 1) % players.size();
		} else {
			targetIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
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
	}

	/**
	 * Permette a un giocatore di chiamare "UNO" se ha una o due carte in mano.
	 * 
	 * @param player
	 */
	public void callUno(Player player) {
		if (player.getHandSize() == 1) {
			player.setUnoCalled(true);
		} else {
			throw new IllegalStateException("Non puoi chiamare UNO se non hai una sola carta in mano.");
		}
	}

	/**
	 * Se un giocatore ha una sola carta in mano e non ha chiamato "UNO", deve
	 * pescare due carte come penalità.
	 * 
	 * @param player
	 */
	public void unoNotCalled(Player player) {
		if (player.getHandSize() == 1 && !player.isUnoCalled()) {
			drawCards(player, 2);
		}
	}

}
