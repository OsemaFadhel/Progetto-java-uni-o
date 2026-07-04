package it.uniroma1.mdp.uno.controller;

import it.uniroma1.mdp.uno.model.GameEngine;
import it.uniroma1.mdp.uno.model.GameMode;
import it.uniroma1.mdp.uno.model.GameState;
import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.BotPlayer;
import it.uniroma1.mdp.uno.model.players.HumanPlayer;
import it.uniroma1.mdp.uno.model.players.Player;
import it.uniroma1.mdp.uno.model.players.strategies.GreedyBotStrategy;
import it.uniroma1.mdp.uno.model.players.strategies.RandomBotStrategy;
import it.uniroma1.mdp.uno.view.Game;
import it.uniroma1.mdp.uno.view.Menu;
import it.uniroma1.mdp.uno.view.Style;
import javafx.animation.PauseTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Gestisce le interazioni tra il modello (GameEngine) e le viste (Menu e Game).
 * 
 * @author Osema Fadhel
 */
public class GameController {
	private GameEngine engine;
	private Stage stage;
	private Menu menuView;
	private Game gameView;

	public GameController(GameEngine engine, Stage primaryStage) {
		this.engine = engine;
		this.stage = primaryStage;
	}

	/**
	 * Avvia il gioco UNO, mostrando il menu principale. Gestisce le interazioni
	 * dell'utente con il menu e avvia la partita in base alle scelte effettuate.
	 * 
	 */
	public void showMenu() {
		menuView = new Menu();

		menuView.getButtonSingleGame().setOnAction(e -> {
			engine.setGameMode(GameMode.SINGLE_GAME);
			menuView.hideThreshold();
			menuView.showSectionPlayers();
		});

		menuView.getButtonPointsGame().setOnAction(e -> {
			engine.setGameMode(GameMode.POINTS_GAME);
			menuView.showThreshold();
			menuView.showSectionPlayers();
		});

		menuView.getButtonAddPlayer().setOnAction(e -> menuView.showFormAddPlayers());

		menuView.getButtonUndo().setOnAction(e -> menuView.hideFormAddPlayers());

		menuView.getButtonConfirm().setOnAction(e -> {
			String nome = menuView.getNome();
			if (nome.isEmpty()) {
				return;
			}
			try {
				Player p = addPlayer(nome, menuView.getTipo());
				menuView.addPlayerLine(nome, () -> engine.removePlayer(p));
				menuView.hideFormAddPlayers();
			} catch (Exception ex) {
				menuView.showError(ex.getMessage());
			}
		});

		menuView.getButtonStart().setOnAction(e -> {
			if (engine.getGameMode() == GameMode.POINTS_GAME) {
				engine.setPointThreshold(menuView.getThreshold());
			}
			try {
				engine.startGame();
				showGame();
				handleStateChange();
			} catch (Exception ex) {
				menuView.showError(ex.getMessage());
			}
		});

		stage.setScene(menuView.getScene());
	}

	/**
	 * Inizializza e mostra la vista di gioco UNO, gestendo le interazioni
	 * dell'utente con la partita.
	 */
	public void showGame() {
		gameView = new Game();

		gameView.getButtonPass().setOnAction(e -> {
			try {
				engine.passTurn();
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonUno().setOnAction(e -> {
			try {
				engine.callUno(engine.getCurrentPlayer());
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonContestUno().setOnAction(e -> {
			try {
				boolean ok = engine.contestUno();
				gameView.showError(ok ? "UNO contestato!" : "Ha chiamto UNO!");
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonMenu().setOnAction(e -> {
			gameView.showMenuOverlay();
		});

		gameView.getButtonNextRound().setOnAction(e -> {
			try {
				gameView.hideRoundOverOverlay();
				engine.startNewRound();
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonNewGame().setOnAction(e -> {
			try {
				gameView.hideGameOverOverlay();
				engine = new GameEngine();
				showMenu();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonColorRed().setOnAction(e -> {
			try {
				gameView.hideColorPickOverlay();
				engine.setWildColor(CardColor.RED);
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonColorYellow().setOnAction(e -> {
			try {
				gameView.hideColorPickOverlay();
				engine.setWildColor(CardColor.YELLOW);
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonColorBlue().setOnAction(e -> {
			try {
				gameView.hideColorPickOverlay();
				engine.setWildColor(CardColor.BLUE);
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonColorGreen().setOnAction(e -> {
			try {
				gameView.hideColorPickOverlay();
				engine.setWildColor(CardColor.GREEN);
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonChallenge().setOnAction(e -> {
			try {
				gameView.hideChallengeOverlay();
				engine.solveChallenge(true);
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonNoChallenge().setOnAction(e -> {
			try {
				gameView.hideChallengeOverlay();
				engine.solveChallenge(false);
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		gameView.getButtonExitGame().setOnAction(e -> {
			engine = new GameEngine();
			showMenu();
		});

		gameView.getButtonCloseMenu().setOnAction(e -> {
			gameView.hideMenuOverlay();
		});

		gameView.getButtonDeck().setOnAction(e -> {
			try {
				engine.drawDuringTurn(engine.getCurrentPlayer());
				handleStateChange();
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
		});

		stage.setScene(gameView.getScene());
	}

	/**
	 * Inizializza dimensioni e stile della finestra di gioco.
	 */
	public void start() {
		stage.setTitle("UNO");
		stage.setWidth(Style.WIDTH);
		stage.setHeight(Style.HEIGHT);
		stage.setResizable(false);

		showMenu();
		stage.show();
	}

	/**
	 * Aggiunge un giocatore al gioco UNO in base al tipo specificato (Umano, Bot
	 * Casuale o Bot Greedy).
	 * 
	 * @param name
	 * @param type
	 * @return {@link Player} il giocatore aggiunto
	 */
	public Player addPlayer(String name, String type) {
		Player player;

		switch (type) {
		case "Bot Casuale":
			player = new BotPlayer(name, new RandomBotStrategy());
			break;
		case "Bot Greedy":
			player = new BotPlayer(name, new GreedyBotStrategy());
			break;
		default:
			player = new HumanPlayer(name);
		}

		engine.addPlayer(player);
		return player;
	}

	/**
	 * Gestisce i cambiamenti di stato del gioco UNO e aggiorna la vista di
	 * conseguenza.
	 */
	public void handleStateChange() {
		gameView.updateTable(engine);

		switch (engine.getGameState()) {
		case NOT_STARTED:
			break;
		case WAITING_FOR_PLAYER_ACTION:
			if (engine.getCurrentPlayer().isBot()) {
				gameView.hideHumanHand();
				handleBotTurn();
			} else {
				gameView.hideHumanHand();
				PauseTransition pause = new PauseTransition(Duration.seconds(3));
				pause.setOnFinished(e -> {
					gameView.clearHumanHand();
					for (Card c : engine.getCurrentPlayer().getHand()) {
						gameView.addCardToHand(c, () -> {
							try {
								engine.playCard(engine.getCurrentPlayer(), c);
								handleStateChange();
							} catch (Exception ex) {
								gameView.showError(ex.getMessage());
							}
						});
					}
					gameView.showHumanHand();
				});
				pause.play();
			}
			break;
		case WAITING_FOR_COLOR_CHOICE:
			gameView.showColorPickOverlay();
			break;
		case WAITING_FOR_CHALLENGE:
			Player target = engine.getTargetPlayer();

			if (target.isBot()) {
				try {
					BotPlayer bot = (BotPlayer) target;
					boolean challenge = bot.shouldChallenge(engine.getCurrentPlayer().getHandSize());
					engine.solveChallenge(challenge);
					handleStateChange();
				} catch (Exception ex) {
					gameView.showError(ex.getMessage());
				}
			} else {
				gameView.showChallengeOverlay();
			}
			break;
		case ROUND_OVER:
			int score = engine.calculateRoundScore(engine.getCurrentPlayer());
			if (engine.getGameState() == GameState.GAME_OVER) {
				gameView.showGameOverOverlay(engine.getCurrentPlayer().getName());
			} else {
				gameView.showRoundOverOverlay(engine.getCurrentPlayer().getName(), score);
			}
			break;
		case GAME_OVER:
			gameView.showGameOverOverlay(engine.getCurrentPlayer().getName());
			break;
		}
	}

	/**
	 * Gestisce il turno del bot, eseguendo le azioni necessarie in base allo stato
	 * del gioco.
	 */
	public void handleBotTurn() {
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.setOnFinished(e -> {
			BotPlayer bot = (BotPlayer) engine.getCurrentPlayer();

			if (bot.shouldContestUno()) {
				engine.contestUno();
			}

			Card card = bot.chooseCardPlay(engine.getTopCard(), engine.getCurrentColor());
			try {
				if (card == null) {
					engine.drawDuringTurn(bot);
					if (bot.shouldPlayDrawnCard(engine.getTopCard(), engine.getCurrentColor())) {
						engine.playCard(bot, bot.getHand().get(bot.getHand().size() - 1));
					} else {
						engine.passTurn();
					}
				} else {
					engine.playCard(bot, card);
				}
			} catch (Exception ex) {
				gameView.showError(ex.getMessage());
			}
			handleStateChange();
		});
		pause.play();
	}
}
