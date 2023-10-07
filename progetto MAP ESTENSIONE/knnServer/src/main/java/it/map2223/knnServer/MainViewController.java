package it.map2223.knnServer;

import it.map2223.knnServer.server.MultiServer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Classe del MainViewController
 */
public class MainViewController {

	// Declare the controls used in the FXML file

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button avvia;

    @FXML
    private Label avviato_label;

    @FXML
    private HBox form;

    @FXML
    private TextField textField;

    @FXML
    private VBox vBox;

	private int port = 2050;

	/**
	 * Il metodo initialize viene chiamato quando la vista principale viene caricata, esso si occupa di inizializzare
	 * il controller della vista principale. Il metodo imposta l'etichetta di "avviato" come non visibile.
	 * Inoltre, imposta un EventHandler per il pulsante "avvia" che crea un nuovo thread per avviare il server.
	 * Il metodo legge la porta dalla casella di testo e la imposta come porta del server se è un valore numerico.
	 * Infine, il metodo rende non visibili il form di avvio e il pulsante "avvia", imposta l'etichetta di "avviato"
	 * con il messaggio di avvio del server e la rende visibile.
	 */
	@FXML

	public void initialize() {
		avviato_label.setVisible(false);
		avvia.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				new Thread(() -> {

					if (isNumeric(textField.getText())) {
						port = Integer.parseInt(textField.getText());
					}
					try {
                        new MultiServer(port);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

				}).start();

				avvia.setVisible(false);
				form.setVisible(false);
				avviato_label.setText("Server avviato sulla Port " + port + "!");
				avviato_label.setVisible(true);
			}
		});

	}
/**
 * Il metodo isNumeric verifica se una stringa passata come argomento può essere convertita in un numero intero.
 * Esso restituisce true se la stringa è numerica e false altrimenti.
 * @param strNum la stringa da verificare se può essere convertita in un numero intero
 * @return true se la stringa può essere convertita in un numero intero, false altrimenti
 */
	private boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}