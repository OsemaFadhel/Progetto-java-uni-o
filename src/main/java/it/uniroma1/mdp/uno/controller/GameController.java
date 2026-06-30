package it.uniroma1.mdp.uno.controller;

import it.uniroma1.mdp.uno.model.GameEngine;
import it.uniroma1.mdp.uno.model.GameMode;
import it.uniroma1.mdp.uno.model.players.BotPlayer;
import it.uniroma1.mdp.uno.model.players.HumanPlayer;
import it.uniroma1.mdp.uno.model.players.Player;
import it.uniroma1.mdp.uno.model.players.strategies.RandomBotStrategy;
import it.uniroma1.mdp.uno.model.players.strategies.AdvancedBotStrategy;
import it.uniroma1.mdp.uno.view.*;
import it.uniroma1.mdp.uno.model.*;
import javafx.stage.Stage;

public class GameController {
	private GameEngine engine;
	private Stage stage;
	private Menu menuView;
	private Game gameView;

	public GameController(GameEngine engine, Stage primaryStage) {
		this.engine = engine;
		this.stage = primaryStage;
	}

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
			if (nome.isEmpty()) return;
			try {
				Player p = addPlayer(nome, menuView.getTipo());
				menuView.addPlayerLine(p, () -> engine.removePlayer(p));
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

	public void showGame() {
		gameView = new Game();
		stage.setScene(gameView.getScene());
	}

	public void start() {
		stage.setTitle("UNO");
		stage.setWidth(Style.WIDTH);
		stage.setHeight(Style.HEIGHT);
		stage.setResizable(false);
		
		showMenu();
		stage.show();
	}

	/**rimuovere forsE?? */
	public void gameStart(GameMode mode, int pointThreshold) {
		engine.startGame();
		showGame();
		handleStateChange();
	}

	public Player addPlayer(String name, String type) {
		Player player;

		switch (type) {
			case "Bot Casuale":
				player = new BotPlayer(name, new RandomBotStrategy());
				break;
			case "Bot Avanzato":
				player = new BotPlayer(name, new AdvancedBotStrategy());     
				break;      
			default :
				player = new HumanPlayer(name);
		}

		engine.addPlayer(player);
		return player;
	}

	public void removePlayer(Player player) {
		engine.removePlayer(player);
	}

	public void handleStateChange() {

	}
}
