package it.uniroma1.mdp.uno.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.cards.CardValue;
import it.uniroma1.mdp.uno.model.players.BotPlayer;
import it.uniroma1.mdp.uno.model.players.Player;

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

	/*
	 * Adds a player to the game.
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}

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
		/*
		 * Put first card on the discard pile. If it's a Wild or Wild Draw Four,
		 * reshuffle and draw again.
		 */
		showFirstCard();
	}

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

	public void playCard(Player player, Card card) {
		if (!card.isPlayable(discardPile.peek(), currentColor)) {
			throw new IllegalArgumentException("Carta non giocabile!");
		}

		player.removeCard(card);
		discardPile.push(card);

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

	public void setWildColor(CardColor chosenColor) {
		if (chosenColor == CardColor.WILD) {
			throw new IllegalArgumentException("Non puoi scegliere WILD come colore!");
		}

		this.currentColor = chosenColor;

		moveToNextPlayer();
	}

	/**
	 * Pesca le carte al giocatore
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

	public void moveToNextPlayer() {
		if (isClockwise) {
			currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		} else {
			currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
		}
	}

}
