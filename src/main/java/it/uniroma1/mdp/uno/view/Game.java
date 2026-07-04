package it.uniroma1.mdp.uno.view;

import java.util.List;

import it.uniroma1.mdp.uno.model.GameEngine;
import it.uniroma1.mdp.uno.model.GameMode;
import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.players.Player;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * 
 * Rappresenta la vista principale del gioco UNO, dove avviene la partita.
 * 
 * @author Osema Fadhel
 */
public class Game {
	private final Scene scene;
	private final Pane tablePane = new Pane();
	private final Label direction = new Label();
	private final Label lblRoundResult = new Label();
	private final Label lblGameResult = new Label();
	private final Label lblCurrentPlayer = new Label();
	private final Button btnPass = new Button("Passa il Turno");
	private final Button btnUno = new Button("UNO");
	private final Button btnContestUno = new Button("Contesta Uno");
	private final Button btnMenu = new Button("☰");
	private final Button btnCloseMenu = new Button("Torna al Gioco");
	private final Button btnNextRound = new Button("Prossimo Round");
	private final Button btnNewGame = new Button("Nuova Partita");
	private final VBox colorPickOverlay;
	private final Button btnColorRed = new Button("Rosso");
	private final Button btnColorYellow = new Button("Giallo");
	private final Button btnColorBlue = new Button("Blu");
	private final Button btnColorGreen = new Button("Verde");
	private final VBox challengeOverlay;
	private final Button btnChallenge = new Button("Sfida");
	private final Button btnNoChallenge = new Button("No Challenge");
	private final VBox roundOverOverlay;
	private final VBox gameOverOverlay;
	private final VBox menuOverlay;
	private final Button btnExitGame = new Button("Esci");
	private final HBox cards = new HBox(Style.SPACING);
	private final ScrollPane humanHand = new ScrollPane(cards);

	private final Button btnDeck;
	private final Label lblNotification = new Label();

	/**
	 * Crea una nuova istanza della vista principale del gioco UNO, inizializzando i
	 * componenti grafici.
	 */
	public Game() {
		this.colorPickOverlay = buildColorPickOverlay();
		this.challengeOverlay = buildChallengeOverlay();
		this.roundOverOverlay = buildRoundOverOverlay();
		this.gameOverOverlay = buildGameOverOverlay();
		this.menuOverlay = buildMenuOverlay();
		this.lblNotification.setVisible(false);
		this.lblNotification.getStyleClass().add("label-error");
		this.btnDeck = buildButtonDeck();
		this.direction.getStyleClass().add("label-direction");
		this.btnMenu.getStyleClass().add("button-menu");

		StackPane root = buildRoot();
		this.scene = new Scene(root, Style.WIDTH, Style.HEIGHT);
		Style.applyTo(this.scene);
	}

	/**
	 * @return {@link Scene}
	 */
	public Scene getScene() {
		return this.scene;
	}

	/**
	 * Restituisce il pulsante per pescare una carta dal mazzo.
	 * 
	 * @return {@link Button}
	 */
	private Button buildButtonDeck() {
		Button btn = new Button();

		ImageView deckImg = new ImageView(new Image(getClass().getResourceAsStream("/assets/cards/BACK.JPG")));
		deckImg.setFitHeight(Style.CARD_HEIGHT);
		deckImg.setPreserveRatio(true);
		btn.setGraphic(deckImg);
		btn.getStyleClass().add("button-card");

		return btn;
	}

	/**
	 * Restituisce l'overlay per la scelta del colore quando un giocatore gioca una
	 * carta Wild.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildColorPickOverlay() {
		btnColorRed.getStyleClass().add("color-btn-red");
		btnColorYellow.getStyleClass().add("color-btn-yellow");
		btnColorGreen.getStyleClass().add("color-btn-green");
		btnColorBlue.getStyleClass().add("color-btn-blue");

		HBox colors = new HBox(Style.SPACING, btnColorRed, btnColorYellow, btnColorGreen, btnColorBlue);
		colors.setAlignment(Pos.CENTER);

		VBox box = new VBox(Style.SPACING);
		box.getChildren().addAll(new Label("Scegli un colore:"), colors);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	/**
	 * Restituisce l'overlay per la sfida del WILD DRAW FOUR.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildChallengeOverlay() {
		Label label = new Label("Challenge Wild Draw Four?");
		HBox btns = new HBox(Style.SPACING, btnChallenge, btnNoChallenge);
		btns.setAlignment(Pos.CENTER);

		VBox box = new VBox(Style.SPACING, label, btns);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	/**
	 * Restituisce l'overlay per la fine del round, mostrando il vincitore e i punti
	 * guadagnati.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildRoundOverOverlay() {
		VBox box = new VBox(Style.SPACING, lblRoundResult, btnNextRound);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	/**
	 * Restituisce l'overlay per la fine del gioco, mostrando il vincitore.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildGameOverOverlay() {
		VBox box = new VBox(Style.SPACING, lblGameResult, btnNewGame);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	/**
	 * Restituisce l'overlay per il menu di gioco, con opzioni per uscire o tornare
	 * al gioco.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildMenuOverlay() {
		VBox box = new VBox(Style.LARGE_SPACING, btnExitGame, btnCloseMenu);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	/**
	 * Restituisce lo StackPane principale che contiene tutti gli elementi della
	 * vista di gioco.
	 * 
	 * @return {@link StackPane}
	 */
	private StackPane buildRoot() {
		StackPane root = new StackPane();
		root.getStyleClass().add("bg-game");

		BorderPane main = new BorderPane();
		tablePane.setPrefSize(Style.WIDTH, Style.HEIGHT - 250);
		main.setTop(buildTopBar());
		main.setCenter(tablePane);
		main.setBottom(buildBottomBar());
		lblNotification.setTranslateY(90);

		root.getChildren().addAll(main, colorPickOverlay, challengeOverlay, roundOverOverlay, gameOverOverlay,
				menuOverlay, lblNotification);

		return root;
	}

	/**
	 * Restituisce la barra superiore della vista di gioco, contenente il pulsante
	 * del menu e l'indicatore di direzione del turno.
	 * 
	 * @return {@link HBox}
	 */
	private HBox buildTopBar() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		HBox bar = new HBox(direction, spacer, btnMenu);
		bar.setPadding(new Insets(10, 15, 10, 15));
		bar.setAlignment(Pos.CENTER_LEFT);
		bar.getStyleClass().add("top-bar");
		return bar;
	}

	/**
	 * Restituisce la barra inferiore della vista di gioco, contenente la mano del
	 * giocatore umano e i pulsanti di azione.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildBottomBar() {
		humanHand.setPrefHeight(Style.CARD_HEIGHT);
		humanHand.getStyleClass().add("hand-scroll");
		humanHand.setMaxWidth(Style.WIDTH - 200);
		humanHand.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		humanHand.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		btnUno.getStyleClass().add("button-uno");
		btnContestUno.getStyleClass().add("button-contest");
		btnPass.getStyleClass().add("button-pass");
		HBox bottomBtns = new HBox(Style.SPACING, btnContestUno, btnPass);
		bottomBtns.setAlignment(Pos.CENTER);

		VBox actionBtns = new VBox(Style.SPACING, btnUno, bottomBtns);
		actionBtns.setAlignment(Pos.CENTER);
		actionBtns.setPadding(new Insets(0, 10, 0, 10));

		HBox handArea = new HBox(Style.LARGE_SPACING, humanHand, actionBtns);
		HBox.setHgrow(humanHand, Priority.ALWAYS);

		VBox bottom = new VBox(Style.SPACING, handArea, lblCurrentPlayer);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(Style.LARGE_SPACING));
		bottom.getStyleClass().add("bottom-bar");
		return bottom;
	}

	/**
	 * Aggiorna la vista del tavolo di gioco in base allo stato attuale del gioco
	 * UNO.
	 * 
	 * @param engine
	 */
	public void updateTable(GameEngine engine) {
		direction.setText(engine.isClockwise() ? "↻" : "↺");
		lblCurrentPlayer.setText("Turno di: " + engine.getCurrentPlayer().getName());

		tablePane.getChildren().clear();

		double coordinatex = tablePane.getPrefWidth() / 2;
		double coordinatey = tablePane.getPrefHeight() / 2;

		buildCardsCenter(coordinatex, coordinatey, engine);
		buildPlayerCircle(coordinatex, coordinatey, engine);
	}

	/**
	 * Costruisce la rappresentazione grafica delle carte al centro del tavolo di
	 * gioco, mostrando la carta in cima al mazzo degli scarti e il mazzo di pesca.
	 * 
	 * @param coordinatex
	 * @param coordinatey
	 * @param engine
	 */
	private void buildCardsCenter(double coordinatex, double coordinatey, GameEngine engine) {
		String topCard = engine.getTopCard().getImageFileName();
		ImageView discardView = new ImageView(new Image(getClass().getResourceAsStream("/assets/cards/" + topCard)));
		discardView.setFitHeight(Style.CARD_HEIGHT);
		discardView.setPreserveRatio(true);

		StackPane discardWrapper = new StackPane(discardView);
		discardWrapper.setLayoutX(coordinatex + 10);
		discardWrapper.setLayoutY(coordinatey - 80);
		String colorHex = Style.getColor(engine.getCurrentColor());
		discardWrapper.setStyle("-fx-border-color: " + colorHex + "; -fx-border-width: 4;");

		btnDeck.setLayoutX(coordinatex - 80);
		btnDeck.setLayoutY(coordinatey - 80);

		tablePane.getChildren().addAll(btnDeck, discardWrapper);
	}

	/**
	 * Costruisce la rappresentazione grafica dei giocatori attorno al tavolo di
	 * gioco,
	 * 
	 * @param coordinatex
	 * @param coordinatey
	 * @param engine
	 */
	private void buildPlayerCircle(double coordinatex, double coordinatey, GameEngine engine) {
		List<Player> players = engine.getPlayers();
		int n = players.size();
		double r = Math.min(coordinatex, coordinatey) * 0.7;

		for (int i = 0; i < n; i++) {
			Player p = players.get(i);
			double angle = (2 * Math.PI * i / n) - Math.PI / 2;
			double x = coordinatex + r * Math.cos(angle);
			double y = coordinatey + r * Math.sin(angle);

			Label nameLabel = new Label(p.getName());
			Label cardCount = new Label("" + p.getHandSize());
			Label points = new Label("points: " + p.getPoints());
			if (engine.getGameMode() == GameMode.POINTS_GAME) {
				points.setVisible(true);
			} else {
				points.setVisible(false);
			}
			VBox seat = new VBox(Style.SPACING, nameLabel, cardCount, points);
			seat.setAlignment(Pos.CENTER);
			seat.getStyleClass().add("player-seat");
			if (p == engine.getCurrentPlayer()) {
				seat.getStyleClass().add("player-active");
			}
			seat.setLayoutX(x - 40);
			seat.setLayoutY(y - 30);

			tablePane.getChildren().add(seat);
		}
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonPass() {
		return btnPass;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonUno() {
		return btnUno;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonContestUno() {
		return btnContestUno;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonMenu() {
		return btnMenu;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonNextRound() {
		return btnNextRound;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonNewGame() {
		return btnNewGame;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonColorRed() {
		return btnColorRed;
	}

	/**
	 * @return
	 */
	public Button getButtonColorYellow() {
		return btnColorYellow;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonColorBlue() {
		return btnColorBlue;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonColorGreen() {
		return btnColorGreen;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonChallenge() {
		return btnChallenge;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonNoChallenge() {
		return btnNoChallenge;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonExitGame() {
		return btnExitGame;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonCloseMenu() {
		return btnCloseMenu;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonDeck() {
		return btnDeck;
	}

	/**
	 * Rappresenta un messaggio nella vista di gioco.
	 * 
	 * @param msg
	 */
	public void showError(String msg) {
		lblNotification.setText(msg);
		lblNotification.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> lblNotification.setVisible(false));
		pause.play();
	}

	/**
	 * Mostra l'overlay per la scelta del colore quando un giocatore gioca una carta
	 * Wild.
	 */
	public void showColorPickOverlay() {
		colorPickOverlay.setVisible(true);
		colorPickOverlay.setManaged(true);
	}

	/**
	 * Nasconde l'overlay per la scelta del colore.
	 */
	public void hideColorPickOverlay() {
		colorPickOverlay.setVisible(false);
		colorPickOverlay.setManaged(false);
	}

	/**
	 * Mostra l'overlay per la sfida del WILD DRAW FOUR.
	 */
	public void showChallengeOverlay() {
		challengeOverlay.setVisible(true);
		challengeOverlay.setManaged(true);
	}

	/**
	 * Nasconde l'overlay per la sfida del WILD DRAW FOUR.
	 */
	public void hideChallengeOverlay() {
		challengeOverlay.setVisible(false);
		challengeOverlay.setManaged(false);
	}

	/**
	 * Mostra l'overlay per la fine del round, indicando il vincitore e i punti
	 * guadagnati.
	 * 
	 * @param winner
	 * @param score
	 */
	public void showRoundOverOverlay(String winner, int score) {
		lblRoundResult.setText(winner + " vince il round! +" + score + " punti");
		roundOverOverlay.setVisible(true);
		roundOverOverlay.setManaged(true);
	}

	/**
	 * Nasconde l'overlay per la fine del round.
	 */
	public void hideRoundOverOverlay() {
		roundOverOverlay.setVisible(false);
		roundOverOverlay.setManaged(false);
	}

	/**
	 * Mostra l'overlay per la fine del gioco, indicando il vincitore.
	 * 
	 * @param winner
	 */
	public void showGameOverOverlay(String winner) {
		lblGameResult.setText(winner + " WINS!");
		gameOverOverlay.setVisible(true);
		gameOverOverlay.setManaged(true);
	}

	/**
	 * Nasconde l'overlay per la fine del gioco.
	 */
	public void hideGameOverOverlay() {
		gameOverOverlay.setVisible(false);
		gameOverOverlay.setManaged(false);
	}

	/**
	 * Mostra l'overlay del menu di gioco.
	 */
	public void showMenuOverlay() {
		menuOverlay.setVisible(true);
		menuOverlay.setManaged(true);
	}

	/**
	 * Nasconde l'overlay del menu di gioco.
	 */
	public void hideMenuOverlay() {
		menuOverlay.setVisible(false);
		menuOverlay.setManaged(false);
	}

	/**
	 * Pulisce la mano del giocatore umano, rimuovendo tutte le carte dalla vista.
	 */
	public void clearHumanHand() {
		cards.getChildren().clear();
	}

	/**
	 * Aggiunge una carta alla mano del giocatore umano nella vista di gioco.
	 * 
	 * @param card
	 * @param onPlay
	 */
	public void addCardToHand(Card card, Runnable onPlay) {
		ImageView image = new ImageView(
				new Image(getClass().getResourceAsStream("/assets/cards/" + card.getImageFileName())));
		image.setPreserveRatio(true);
		image.setFitHeight(Style.CARD_HEIGHT);
		Button btn = new Button();
		btn.setGraphic(image);
		btn.getStyleClass().add("button-card");
		btn.setOnAction(e -> onPlay.run());
		cards.getChildren().add(btn);
	}

	/**
	 * Mostra la mano del giocatore umano nella vista di gioco.
	 */
	public void showHumanHand() {
		humanHand.setVisible(true);
		humanHand.setManaged(true);
	}

	/**
	 * Naconde la mano del giocatore umano nella vista di gioco.
	 */
	public void hideHumanHand() {
		cards.getChildren().clear();
		humanHand.setVisible(false);
	}
}
