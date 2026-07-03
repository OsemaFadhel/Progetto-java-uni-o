package it.uniroma1.mdp.uno.view;

import java.util.List;

import it.uniroma1.mdp.uno.model.cards.Card;
import it.uniroma1.mdp.uno.model.cards.CardColor;
import it.uniroma1.mdp.uno.model.GameEngine;
import it.uniroma1.mdp.uno.model.GameMode;
import it.uniroma1.mdp.uno.model.players.Player;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

/**
 * 
 * Game
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
	private final HBox cards = new HBox(6);
	private final ScrollPane humanHand = new ScrollPane(cards);


	private final Button btnDeck;
	private final Label lblNotification = new Label();

	public Game() {
		this.colorPickOverlay = buildColorPickOverlay();
		this.challengeOverlay = buildChallengeOverlay();
		this.roundOverOverlay = buildRoundOverOverlay();
		this.gameOverOverlay = buildGameOverOverlay();
		this.menuOverlay = buildMenuOverlay();
		this.lblNotification.setVisible(false);
		this.lblNotification.getStyleClass().add("lable-error");
		this.btnDeck = buildButtonDeck();
		this.direction.getStyleClass().add("label-direction");
		this.btnMenu.getStyleClass().add("button-menu");

		StackPane root = buildRoot();
		this.scene = new Scene(root, Style.WIDTH, Style.HEIGHT);
		Style.applyTo(this.scene);
	}

	public Scene getScene() {
		return this.scene;
	}

	private Button buildButtonDeck() {
		Button btn = new Button();

		ImageView deckImg = new ImageView(new Image(
			getClass().getResourceAsStream("/assets/cards/BACK.JPG")));
		deckImg.setFitHeight(120);
		deckImg.setPreserveRatio(true);	
		btn.setGraphic(deckImg);
		btn.getStyleClass().add("button-card");
		
		return btn;
	}

	private VBox buildColorPickOverlay() {
		btnColorRed.getStyleClass().add("color-btn-red");
		btnColorYellow.getStyleClass().add("color-btn-yellow");
		btnColorGreen.getStyleClass().add("color-btn-green");
		btnColorBlue.getStyleClass().add("color-btn-blue");

		HBox colors = new HBox(6, btnColorRed, btnColorYellow, btnColorGreen, btnColorBlue);
		colors.setAlignment(Pos.CENTER);

		VBox box = new VBox(6);
		box.getChildren().addAll(new Label("Scegli un colore:"), colors);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	private VBox buildChallengeOverlay() {
		Label label = new Label("Challenge Wild Draw Four?");
		HBox btns = new HBox(6, btnChallenge, btnNoChallenge);
		btns.setAlignment(Pos.CENTER);

		VBox box = new VBox(6, label, btns);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	private VBox buildRoundOverOverlay() {
		HBox btns = new HBox(6, btnNextRound);
		btns.setAlignment(Pos.CENTER);

		VBox box = new VBox(6, lblRoundResult, btns);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	private VBox buildGameOverOverlay() {
		VBox box = new VBox(6, lblGameResult, btnNewGame);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

	private VBox buildMenuOverlay() {
		VBox box = new VBox(15, btnExitGame, btnCloseMenu);
		box.setAlignment(Pos.CENTER);
		box.getStyleClass().add("overlay");
		box.setVisible(false);
		box.setManaged(false);

		return box;
	}

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

	private HBox buildTopBar() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		HBox bar = new HBox(direction, spacer, btnMenu);
		bar.setPadding(new Insets(10, 15, 10, 15));
		bar.setAlignment(Pos.CENTER_LEFT);
		bar.getStyleClass().add("top-bar");
		return bar;
	}

	private VBox buildBottomBar() {
		humanHand.setPrefHeight(140);
		humanHand.getStyleClass().add("hand-scroll");
		humanHand.setMaxWidth(Style.WIDTH - 200);
		humanHand.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		humanHand.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		btnUno.getStyleClass().add("button-uno");
		btnContestUno.getStyleClass().add("button-contest");
		btnPass.getStyleClass().add("button-pass");
		HBox bottomBtns = new HBox(6, btnContestUno, btnPass);
		bottomBtns.setAlignment(Pos.CENTER);


		VBox actionBtns = new VBox(6, btnUno, bottomBtns);
		actionBtns.setAlignment(Pos.CENTER);
		actionBtns.setPadding(new Insets(0, 10, 0, 10));

		HBox handArea = new HBox(10, humanHand, actionBtns);
		HBox.setHgrow(humanHand, Priority.ALWAYS);

		VBox bottom = new VBox(5, handArea, lblCurrentPlayer);
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(10));
		bottom.getStyleClass().add("bottom-bar");
		return bottom;
	}

	public void updateTable(GameEngine engine) {
		direction.setText(engine.isClockwise() ? "↻" : "↺");
		lblCurrentPlayer.setText("Turno di: " + engine.getCurrentPlayer().getName());

		tablePane.getChildren().clear();

		double coordinatex = tablePane.getPrefWidth() / 2;
		double coordinatey = tablePane.getPrefHeight() / 2;

		buildCardsCenter(coordinatex, coordinatey, engine);
		buildPlayerCircle(coordinatex, coordinatey, engine);
	}

	private void buildCardsCenter(double coordinatex, double coordinatey, GameEngine engine) {
		String topCard = engine.getTopCard().getImageFileName();
		ImageView discardView = new ImageView(new Image(
			getClass().getResourceAsStream("/assets/cards/" + topCard)));
		discardView.setFitHeight(120);
		discardView.setPreserveRatio(true);

		StackPane discardWrapper = new StackPane(discardView);
		discardWrapper.setLayoutX(coordinatex + 10);
		discardWrapper.setLayoutY(coordinatey - 80);
		String colorHex = getColor(engine.getCurrentColor());
		discardWrapper.setStyle("-fx-border-color: " + colorHex + 
			"; -fx-border-width: 4;");

		btnDeck.setLayoutX(coordinatex - 80);
		btnDeck.setLayoutY(coordinatey - 80);

		tablePane.getChildren().addAll(btnDeck, discardWrapper);
	}

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
			VBox seat = new VBox(4, nameLabel, cardCount, points);
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

	private String getColor(CardColor color) {
		return switch (color) {
			case RED    -> "red";
			case YELLOW -> "yellow";
			case GREEN  -> "green";
			case BLUE   -> "blue";
			default     -> "transparent";
		};
	}

	public Button getButtonPass() {
		return btnPass;
	}

	public Button getButtonUno() {
		return btnUno;
	}

	public Button getButtonContestUno() {
		return btnContestUno;
	}

	public Button getButtonMenu() {
		return btnMenu;
	}

	public Button getButtonNextRound() {
		return btnNextRound;
	}

	public Button getButtonNewGame() {
		return btnNewGame;
	}

	public Button getButtonColorRed() {
		return btnColorRed;
	}

	public Button getButtonColorYellow() {
		return btnColorYellow;
	}

	public Button getButtonColorBlue() {
		return btnColorBlue;
	}

	public Button getButtonColorGreen() {
		return btnColorGreen;
	}

	public Button getButtonChallenge() {
		return btnChallenge;
	}

	public Button getButtonNoChallenge() {
		return btnNoChallenge;
	}

	public Button getButtonExitGame() {
		return btnExitGame;
	}
	
	public Button getButtonCloseMenu() {
		return btnCloseMenu;
	}

	public Button getButtonDeck() {
		return btnDeck;
	}

	public void showError(String msg) {
		lblNotification.setText(msg);
		lblNotification.setVisible(true);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> lblNotification.setVisible(false));
		pause.play();
	}

	public void showColorPickOverlay() {
		colorPickOverlay.setVisible(true);
		colorPickOverlay.setManaged(true);
	}

	public void hideColorPickOverlay() {
		colorPickOverlay.setVisible(false);
		colorPickOverlay.setManaged(false);
	}

	public void showChallengeOverlay() {
		challengeOverlay.setVisible(true);
		challengeOverlay.setManaged(true);
	}

	public void hideChallengeOverlay() {
		challengeOverlay.setVisible(false);
		challengeOverlay.setManaged(false);
	}

	public void showRoundOverOverlay(String winner, int score) {
		lblRoundResult.setText(winner + " vince il round! +" + score + " punti");
		roundOverOverlay.setVisible(true);
		roundOverOverlay.setManaged(true);
	}

	public void hideRoundOverOverlay() {
		roundOverOverlay.setVisible(false);
		roundOverOverlay.setManaged(false);
	}

	public void showGameOverOverlay(String winner) {
		lblGameResult.setText(winner + " WINS!");
		gameOverOverlay.setVisible(true);
		gameOverOverlay.setManaged(true);
	}

	public void hideGameOverOverlay() {
		gameOverOverlay.setVisible(false);
		gameOverOverlay.setManaged(false);
	}

	public void showMenuOverlay() {
		menuOverlay.setVisible(true);
		menuOverlay.setManaged(true);
	}

	public void hideMenuOverlay() {
		menuOverlay.setVisible(false);
		menuOverlay.setManaged(false);
	}

	public void clearHumanHand() {
		cards.getChildren().clear();
	}

	public void addCardToHand(Card card, Runnable onPlay) {
		ImageView image = new ImageView(new Image(
			getClass().getResourceAsStream("/assets/cards/" + card.getImageFileName())));
		image.setPreserveRatio(true);
		image.setFitHeight(120);
		Button btn = new Button();
		btn.setGraphic(image);
		btn.getStyleClass().add("button-card");
		btn.setOnAction(e -> onPlay.run());
		cards.getChildren().add(btn);
	}

	public void showHumanHand() {
		humanHand.setVisible(true);
		humanHand.setManaged(true);
	}

	public void hideHumanHand() {
		cards.getChildren().clear();
		humanHand.setVisible(false);
	}
}
