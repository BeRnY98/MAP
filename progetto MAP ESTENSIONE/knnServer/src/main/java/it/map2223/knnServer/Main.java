package it.map2223.knnServer;
import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Vero main del progetto
 */
public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane root;
/**
 * Il metodo start è un metodo sovrascritto dalla classe Application. Esso inizializza la finestra principale per l'interfaccia
 * grafica e carica la vista principale, disabilitando la possibilità di ridimensionare la finestra.
 * @param  primaryStage lo Stage principale per la GUI
 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("knnServer");
		this.primaryStage.setResizable(false);
		loadMainView();
		setCloseAlert();

	}

	/**
	 * Il metodo loadMainView carica la vista principale della GUI utilizzando la classe FXMLLoader.
	 * Essa carica il file FXML della vista dalla directory delle risorse dell'applicazione.
	 * Il metodo imposta la scena primaria con la vista caricata e la visualizza.
	 * @throws IOException se ci sono errori durante la lettura del file FXML.
	 */
    private void loadMainView() {
    	
    	root = new AnchorPane();
        FXMLLoader loader = null;
		try {
			loader = new FXMLLoader(Main.class.getResource("/it/map2223/knnServer/view/MainView.fxml"));
			root = (AnchorPane) loader.load();
    		primaryStage.setScene(new Scene(root));
    		primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }

	/**
	 * Il metodo setCloseAlert imposta un alert di chiusura quando l'utente tenta di chiudere la finestra della GUI.
	 * Il metodo imposta un EventHandler per la finestra primaria che consuma l'evento di chiusura predefinito e
	 * mostra un alert di conferma personalizzato con la richiesta di confermare la chiusura del server.
	 * In caso affermativo, il metodo chiude il server e termina l'applicazione.
	 */
	private void setCloseAlert() {
		Alert a = new Alert(Alert.AlertType.NONE);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				t.consume();

				a.setAlertType(Alert.AlertType.CONFIRMATION);
				a.setTitle("Chiusura server");
				a.setContentText("Chiudere il server?");

				Optional<ButtonType> result = a.showAndWait();
				if (result.get() == ButtonType.OK) {
					Platform.exit();
					System.exit(0);
				}

			}
		});
	}

	/**
	 * Main
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}