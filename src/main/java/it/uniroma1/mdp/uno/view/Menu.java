package it.uniroma1.mdp.uno.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import it.uniroma1.mdp.uno.model.*;
import it.uniroma1.mdp.uno.model.players.Player;

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
	private final List<PlayerEntry> playerEntries = new ArrayList<>();
	private final Label lblError = new Label();

	private static class PlayerEntry {
		final HBox row;
		final Player player;
		PlayerEntry(HBox row, Player player) {
			this.row = row;
			this.player = player;
		}
	}

	public Menu() {
		cbType.getItems().addAll("Umano", "Bot Casuale", "Bot Avanzato");
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

	private VBox buildSecPlayers() {
		VBox box = new VBox(10);
		Label lbl = new Label("Giocatori (2-6)");
		box.getChildren().addAll(lbl, playerListBox, formAddPlayers, btnAddPlayer);
		return box;
	}

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

		VBox box = new VBox(10, lblValue, sliderThreshold);

		box.setAlignment(Pos.CENTER_LEFT);
		
		box.setVisible(false);
		box.setManaged(false);
		return box;
	}

	private VBox buildFormAddPlayers() {
		HBox btns = new HBox(10, btnConfirm, btnUndo);
		VBox form = new VBox(8, new Label("Nome:"), tfName, new Label("Tipo:"), cbType, btns, lblError);
		form.setVisible(false);
		form.setManaged(false);
		return form;
	}

	public void addPlayerLine(Player player, Runnable onRemove) {
		Label lbl = new Label(player.getName() + " [" + cbType.getValue() + "]");
		Button btnRemove = new Button("✕");
		btnRemove.getStyleClass().add("button-danger");
		HBox row = new HBox(10, lbl, btnRemove);
		row.setAlignment(Pos.CENTER_LEFT);
		
		PlayerEntry entry = new PlayerEntry(row, player);
		playerEntries.add(entry);
		playerListBox.getChildren().add(row);
		
		btnRemove.setOnAction(e -> {
			onRemove.run();
			removePlayerLine(entry);
		});

		if (playerEntries.size() >= 6) {
			btnAddPlayer.setDisable(true);
		}
		updateStartButton();	
	}

	private void removePlayerLine(PlayerEntry entry) {
		playerEntries.remove(entry);
		playerListBox.getChildren().remove(entry.row);

		btnAddPlayer.setDisable(false);
		updateStartButton();
	}

	private void updateStartButton() {
    	btnStart.setDisable(playerEntries.size() < 2);
	}
	
	public Scene  getScene() { 
		return scene;
	}

	public Button getButtonSingleGame() { 
		return btnSingleGame;
	}

	public Button getButtonPointsGame() { 
		return btnPointsGame; 
	}

	public Button getButtonAddPlayer() { 
		return btnAddPlayer; 
	}

	public Button getButtonStart() { 
		return btnStart; 
	}

	public Button getButtonConfirm() { 
		return btnConfirm; 
	}

	public Button getButtonUndo() { 
		return btnUndo; 
	}

	public String getNome() { 
		return tfName.getText().trim(); 
	}

	public String getTipo() { 
		return cbType.getValue(); 
	}

	public int getThreshold() { 
		return (int) sliderThreshold.getValue(); 
	}

	public void showSectionPlayers() {
		secPlayers.setVisible(true);
		secPlayers.setManaged(true);
	}

	public void hideSectionPlayers() {
		secPlayers.setVisible(false);
		secPlayers.setManaged(false);
	}

	public void showThreshold() {
		secThreshold.setVisible(true);
		secThreshold.setManaged(true);
	}

	public void hideThreshold() {
		secThreshold.setVisible(false);
		secThreshold.setManaged(false);
	}

	public void showFormAddPlayers() {
		formAddPlayers.setVisible(true);
		formAddPlayers.setManaged(true);
		btnAddPlayer.setVisible(false);
		btnAddPlayer.setManaged(false);
		tfName.clear();
		tfName.requestFocus();
	}

	public void hideFormAddPlayers() {
		hideError();
		formAddPlayers.setVisible(false);
		formAddPlayers.setManaged(false);
		btnAddPlayer.setVisible(true);
		btnAddPlayer.setManaged(true);
	}

	public void showError(String msg) {
		lblError.setText(msg);
		lblError.setVisible(true);
		lblError.setManaged(true);
	}

	public void hideError() {
		lblError.setVisible(false);
		lblError.setManaged(false);
	}
}
