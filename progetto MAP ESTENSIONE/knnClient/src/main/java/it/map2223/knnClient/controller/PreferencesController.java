package it.map2223.knnClient.controller;

import it.map2223.knnClient.Main;
import it.map2223.knnClient.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Classe delle funzioni del preferencesController
 */
public class PreferencesController {

	@FXML private CheckBox enableWelcome;
	@FXML private TextField serverAddress;
	@FXML private TextField portNumber;
	@FXML private Button cancelButton;
	@FXML private Button saveButton;
	
	private Settings sett;
	
	/**
	 * Visualizza lo stato attuale delle preferenze dell'utente.
	 */
	public void initialize() {
		
		sett = Main.getSettings();
		
		enableWelcome.setSelected(sett.getWelcomeEnabled());
		serverAddress.setPromptText(sett.getServerAddr());
		portNumber.setPromptText(Integer.toString(sett.getPortNumber()));
	}
	
	/**
	 * Controlla eventuali variazioni alle impostazioni. Se sono valide, le salva e chiude la finestra.
	 * @param event evento che scatena il metodo
	 */
	@FXML
	protected void onSaveBtnPressed(ActionEvent event) {

		sett.setWelcomeEnabled(enableWelcome.isSelected());

		if (!serverAddress.getText().isEmpty()) {
			sett.setServerAddr(serverAddress.getText());
		}
		
		try {
			
			if (!portNumber.getText().isEmpty()) {
				sett.setPortNumber(Integer.parseInt(portNumber.getText()));
			}
			
		} catch (NumberFormatException e) {
			if (!portNumber.getText().isEmpty()) {
				Main.getViewLoader().createErrAlert("Non hai inserito un Port Number Valido!");
				return;
			}
		}

		String header = "Le impostazioni sono state salvate";
		String content = "Le modifiche saranno valide dal prossimo avvio dell'applicazione.";
		Main.getViewLoader().createInfoAlert(header, content);

		Stage currentStage = (Stage) saveButton.getScene().getWindow();
		currentStage.close();

	}
	
	/**
	 * Chiude la finestra senza salvare le eventuali variazioni alle preferenze.
	 * @param event l'evento di pressione del pulsante "Annulla"
	 */
	@FXML
	protected void onCancelBtnPressed(ActionEvent event){
	
		Stage currentStage = (Stage) cancelButton.getScene().getWindow();
		currentStage.close();
	}

}
