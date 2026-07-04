package it.uniroma1.mdp.uno.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Rappresenta il menu principale del gioco UNO, schermata iniziale.
 * 
 * @author Osema Fadhel
 */
public class Menu {
	private final Scene scene;
	private final Button btnSingleGame = new Button("Partita Singola");
	private final Button btnPointsGame = new Button("Partita a Punti");
	private final Button btnStart = new Button("Inizia Partita!");
	private final Button btnAddPlayer = new Button("Aggiungi Giocatore");
	private final Button btnConfirm = new Button("Conferma");
	private final Button btnUndo = new Button("Annulla");
	private final TextField tfName = new TextField();
	private final ComboBox<String> cbType = new ComboBox<>();
	private final Slider sliderThreshold = new Slider(100, 1000, 500);
	private final VBox playerListBox = new VBox(6);
	private VBox secPlayers;
	private VBox secThreshold;
	private VBox formAddPlayers;
	private final List<HBox> playerEntries = new ArrayList<>();
	private final Label lblError = new Label();

	/**
	 * Crea una nuova istanza del menu principale del gioco UNO.
	 */
	public Menu() {
		cbType.getItems().addAll("Umano", "Bot Casuale", "Bot Greedy");
		cbType.setValue("Umano");

		secThreshold = buildSecThreshold();
		formAddPlayers = buildFormAddPlayers();
		secPlayers = buildSecPlayers();

		secPlayers.setVisible(false);
		secPlayers.setManaged(false);

		btnConfirm.getStyleClass().add("button-green");
		btnStart.getStyleClass().add("button-start");

		lblError.getStyleClass().add("label-error");
		lblError.setVisible(false);
		lblError.setManaged(false);

		VBox root = buildRoot();
		this.scene = new Scene(root, Style.WIDTH, Style.HEIGHT);
		Style.applyTo(this.scene);
	}

	/**
	 * Restituisce il layout del menu principale del gioco UNO.
	 * 
	 * @return {@link VBox}
	 */
	private VBox buildRoot() {
		VBox root = new VBox(Style.PADDING);
		root.setPadding(new Insets(Style.PADDING));
		root.setAlignment(Pos.TOP_CENTER);
		root.getStyleClass().add("bg-menu");

		Label title = new Label("UNO");
		title.getStyleClass().add("label-header");

		HBox modeBtns = new HBox(Style.PADDING, btnSingleGame, btnPointsGame);
		modeBtns.setAlignment(Pos.CENTER);

		btnStart.setDisable(true);

		root.getChildren().addAll(title, new Separator(), modeBtns, secThreshold, secPlayers, btnStart);
		return root;
	}

	/**
	 * Restituisce il layout della sezione giocatori. Composta da Label, VBox con
	 * lista giocatori e pulsante per aggiungere giocatori.
	 * 
	 * @return {@code VBox}
	 */
	private VBox buildSecPlayers() {
		VBox box = new VBox(Style.LARGE_SPACING);
		Label label = new Label("Giocatori (2-6)");
		box.getChildren().addAll(label, playerListBox, formAddPlayers, btnAddPlayer);
		return box;
	}

	/**
	 * Restituisce il layout della sezione soglia punti. Composta da Label e Slider
	 * per impostare la soglia punti.
	 * 
	 * @return {@code VBox}
	 */
	private VBox buildSecThreshold() {
		sliderThreshold.setMajorTickUnit(50);
		sliderThreshold.setMinorTickCount(0);
		sliderThreshold.setSnapToTicks(true);
		sliderThreshold.setShowTickMarks(true);
		sliderThreshold.setPrefWidth(300);

		Label lblValue = new Label("Soglia: 500 punti");
		sliderThreshold.valueProperty().addListener((obs, old, val) -> {
			int v = (int) Math.round(val.doubleValue());
			lblValue.setText("Soglia: " + v + " punti");
		});

		VBox box = new VBox(Style.LARGE_SPACING, lblValue, sliderThreshold);

		box.setAlignment(Pos.CENTER_LEFT);

		box.setVisible(false);
		box.setManaged(false);
		return box;
	}

	/**
	 * Restituisce il layout del form per aggiungere giocatori. Composto da Label,
	 * TextField per il nome, ComboBox per il tipo di giocatore, pulsante per
	 * confermare e pulsante per annullare.
	 * 
	 * @return {@code VBox}
	 */
	private VBox buildFormAddPlayers() {
		HBox btns = new HBox(Style.LARGE_SPACING, btnConfirm, btnUndo);
		VBox form = new VBox(Style.SPACING, new Label("Nome:"), tfName, new Label("Tipo:"), cbType, btns, lblError);
		form.setVisible(false);
		form.setManaged(false);
		return form;
	}

	/**
	 * Aggiunge nuovo giocatore nella sezione giocatori.
	 * 
	 * 
	 * @param name
	 * @param onRemove
	 */
	public void addPlayerLine(String name, Runnable onRemove) {
		Label label = new Label(name + " [" + cbType.getValue() + "]");
		Button btnRemove = new Button("✕");
		btnRemove.getStyleClass().add("button-danger");
		HBox row = new HBox(Style.LARGE_SPACING, label, btnRemove);
		row.setAlignment(Pos.CENTER_LEFT);

		playerEntries.add(row);
		playerListBox.getChildren().add(row);

		btnRemove.setOnAction(e -> {
			onRemove.run();
			removePlayerLine(row);
		});

		if (playerEntries.size() >= 6) {
			btnAddPlayer.setDisable(true);
		}
		updateStartButton();
	}

	/**
	 * Rimuove un giocatore dalla sezione giocatori.
	 * 
	 * @param row
	 */
	private void removePlayerLine(HBox row) {
		playerEntries.remove(row);
		playerListBox.getChildren().remove(row);

		btnAddPlayer.setDisable(false);
		updateStartButton();
	}

	/**
	 * Aggiorna lo stato del pulsante "Inizia Partita!" in base al numero di
	 * giocatori presenti.
	 */
	private void updateStartButton() {
		btnStart.setDisable(playerEntries.size() < 2);
	}

	/**
	 * @return {@link Scene}
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonSingleGame() {
		return btnSingleGame;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonPointsGame() {
		return btnPointsGame;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonAddPlayer() {
		return btnAddPlayer;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonStart() {
		return btnStart;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonConfirm() {
		return btnConfirm;
	}

	/**
	 * @return {@link Button}
	 */
	public Button getButtonUndo() {
		return btnUndo;
	}

	/**
	 * @return {@link String}
	 */
	public String getNome() {
		return tfName.getText().trim();
	}

	/**
	 * @return {@link String}
	 */
	public String getTipo() {
		return cbType.getValue();
	}

	/**
	 * @return {@link int}
	 */
	public int getThreshold() {
		return (int) sliderThreshold.getValue();
	}

	/**
	 * Mostra la sezione giocatori nel menu principale.
	 */
	public void showSectionPlayers() {
		secPlayers.setVisible(true);
		secPlayers.setManaged(true);
	}

	/**
	 * Nasconde la sezione giocatori nel menu principale.
	 */
	public void hideSectionPlayers() {
		secPlayers.setVisible(false);
		secPlayers.setManaged(false);
	}

	/**
	 * Mostra la sezione soglia punti nel menu principale.
	 */
	public void showThreshold() {
		secThreshold.setVisible(true);
		secThreshold.setManaged(true);
	}

	/**
	 * Nasconde la sezione soglia punti nel menu principale.
	 */
	public void hideThreshold() {
		secThreshold.setVisible(false);
		secThreshold.setManaged(false);
	}

	/**
	 * Mostra il form per aggiungere giocatori.
	 */
	public void showFormAddPlayers() {
		formAddPlayers.setVisible(true);
		formAddPlayers.setManaged(true);
		btnAddPlayer.setVisible(false);
		btnAddPlayer.setManaged(false);
		tfName.clear();
		tfName.requestFocus();
	}

	/**
	 * Nasconde il form per aggiungere giocatori.
	 */
	public void hideFormAddPlayers() {
		hideError();
		formAddPlayers.setVisible(false);
		formAddPlayers.setManaged(false);
		btnAddPlayer.setVisible(true);
		btnAddPlayer.setManaged(true);
	}

	/**
	 * Mostra un messaggio di errore nel form per aggiungere giocatori.
	 * 
	 * @param msg
	 */
	public void showError(String msg) {
		lblError.setText(msg);
		lblError.setVisible(true);
		lblError.setManaged(true);
	}

	/**
	 * Nasconde il messaggio di errore nel form per aggiungere giocatori.
	 */
	public void hideError() {
		lblError.setVisible(false);
		lblError.setManaged(false);
	}
}
