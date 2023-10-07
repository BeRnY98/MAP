package it.map2223.knnClient.controller;

import it.map2223.knnClient.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Classe per la Welcome
 */
public class WelcomeController {
	
	@FXML private Button btn;
	
	/**
	 * Tenta di stabilire la connessione con il server. Se ha successo, carica la Scena Home.
	 * @param event  L'evento del clic del pulsante di connessione.
	 */
	@FXML
    protected void buttonClicked(ActionEvent event){
		
		if(Main.getConnectionInstance().tryConnection()) {
			Main.getViewLoader().loadHome(false);
		} else {
			Main.getViewLoader().loadPreferences();
			Main.getViewLoader().createErrAlert("Non sono riuscito a contattare il server.\n"
					+ "Controlla le impostazioni di connessione"
					+ " e verifica che il server sia in ascolto.");
			Stage stage = (Stage) btn.getScene().getWindow();
			stage.close();
		}
		
	}

}
