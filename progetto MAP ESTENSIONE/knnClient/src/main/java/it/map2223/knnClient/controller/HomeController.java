package it.map2223.knnClient.controller;

import it.map2223.knnClient.Main;
import it.map2223.knnClient.ui.MenuActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Classe per la Home
 */
public class HomeController {
	@FXML private ChoiceBox<String> choiceBox;
	@FXML private TextField tableName;
	@FXML private Button learnBtn;
	@FXML private MenuBar menuBar;
	
	/**
	 * Istanzia la classe MenuActions per gestire la barra dei menu.
	 * Inserisce le opzioni disponibili nel choiceBox.
	 * Permette di usare il tasto invio per simulare la pressione del bottone Learn.
	 */
	public void initialize() {
		@SuppressWarnings("unused")
		MenuActions ma = new MenuActions(menuBar);

		choiceBox.getItems().removeAll(choiceBox.getItems());
		choiceBox.getItems().addAll("Learn from File", "Learn from Binary File","Learn from Database");
		choiceBox.getSelectionModel().select(0);
		
		tableName.setOnKeyPressed((event) -> { if(event.getCode() == KeyCode.ENTER) {onLearnBtnPressed(null);} });
	}
	
	/**
	 * Quando il bottone Learn viene premuto, invia al server le informazioni per l'apprendimento e 
	 * se l'operazione va a buon fine carica la schermata per la fase di previsione.
	 * Aggiorna poi le tabelle usate di recente.
	 * @param event l'evento generato dal click sul pulsante "Learn"
	 */
	@FXML
	protected void onLearnBtnPressed(ActionEvent event){
		
		if(Main.getConnectionInstance()
				.learnKnn(choiceBox.getSelectionModel().getSelectedIndex() + 1, tableName.getText())) {
			Main.getViewLoader().loadPredView();
			Main.getSettings().refreshRecent(tableName.getText());
		} else {
			Main.getConnectionInstance().tryConnection();
		}
		
	}
}
