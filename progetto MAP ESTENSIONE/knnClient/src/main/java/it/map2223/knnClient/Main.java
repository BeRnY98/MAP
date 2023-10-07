package it.map2223.knnClient;

import it.map2223.knnClient.ui.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * L'applicazione implementa la GUI di un client per la previsione.
 * fornendo al server il nome del dataset da cui apprendere.
 *
 */
public class Main extends Application {

	private static Client conn;
	private static Settings sett;
	private static ViewLoader vl;

	
	/**
	 * Main della funzione
	 * @param args argomenti passati al main
	 */
	public static void main(String[] args) {

		launch(args);
	}

	
	/** Avvia la schermata del knn learner
	 * @param primaryStage oggetto Stage utilizzato come finestra principale dell'applicazione
	 */
	@Override
	public void start(Stage primaryStage) {

		sett = new Settings();
		conn = Client.getInstance(sett.getServerAddr(), sett.getPortNumber());
		vl = new ViewLoader(primaryStage);
		
		primaryStage.setTitle("Knn Learner");
		primaryStage.setResizable(false);
		
		if (sett.getWelcomeEnabled()) {
			vl.loadWelcome();
			primaryStage.show();

		} else {
			if (conn.tryConnection()) {
				vl.loadHome(false);
				primaryStage.show();

			} else {
				vl.loadPreferences();
				vl.createErrAlert("Non sono riuscito a contattare il server.\n"
						+ "Controlla le impostazioni di connessione"
						+ " e verifica che il server sia in ascolto.");

			}
		}

	}


	
	/**
	 * Metodo statico che restituisce l'istanza di ViewLoader utilizzata nell'applicazione.
	 * @return ViewLoader acquisito
	 */
	public static ViewLoader getViewLoader() {
		return vl;
	}
	/**
	 * Metodo statico che restituisce l'istanza di Client utilizzata nell'applicazione.
	 * @return Client acquisito
	 */
	public static Client getConnectionInstance() {
		return conn;
	}
	/**
	 * Metodo statico che restituisce l'istanza di Settings utilizzata nell'applicazione.
	 * @return Settings acquisito
	 */
	public static Settings getSettings() {
		return sett;
	}

}