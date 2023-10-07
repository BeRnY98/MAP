package it.map2223.knnClient.controller;



import it.map2223.knnClient.Client;
import it.map2223.knnClient.Main;
import it.map2223.knnClient.ui.MenuActions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Classe per la schermata della fase di predizione
 */
public class PredPhaseController {

	@FXML
    private TextField AttContinue;

    @FXML
    private TextField AttDiscrete;

    @FXML
    private TextField ValK;

    @FXML
    private Button againBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView image;

    @FXML
    private MenuBar menuBar;

	@FXML
    private Text predizione;

    @FXML
    private Button nextBtn;

    @FXML
    private VBox optionsContainer;

    @FXML
    private Text question;

	private String classValue;
	private Client conn;

	/**
	 * Inizializza la vista della schermata di apprendimento
	 */
	public void initialize() {

		@SuppressWarnings("unused")
		MenuActions ma = new MenuActions(menuBar);

		// Rende impossibile aprire un documento nuovo o recente durante la fase di previsione
		menuBar.getMenus().get(0).getItems().get(0).setDisable(true);
		menuBar.getMenus().get(0).getItems().get(1).setDisable(true);

		againBtn.setVisible(false);

		conn = Main.getConnectionInstance();
		AttContinue.setOnAction(null);
		AttDiscrete.setOnAction(null);
		ValK.setOnAction(null);
		optionsContainer.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				onNextBtnPressed(null);
			}
		});		
	}


	/** 
	 *Riabilita le TextFiled e le pulisce dai valori inseriti precedentemente.
	 */
	private void refreshInfo() {

		question.setText("Inserisci i valori");
		AttContinue.clear();
		AttDiscrete.clear();
		ValK.clear();
		AttContinue.disableProperty().set(false);
		AttDiscrete.disableProperty().set(false);
		ValK.disableProperty().set(false);
		predizione.setText("");
		classValue = null;

		
		
	}

	/**
	 *Visualizza il classValue effettuando modifiche alla Scena.
	 * @param classValue La classe predetta dal modello
	 */
	private void showClass(String classValue) {

		question.setText("Previsione terminata!");
		predizione.setText("Predizione = " + classValue);
			
		image.setImage(new Image("/it/map2223/knnClient/images/complete.png"));

		AttContinue.disableProperty().set(true);
		AttDiscrete.disableProperty().set(true);
		ValK.disableProperty().set(true);
		optionsContainer.setAlignment(Pos.BOTTOM_LEFT);
		optionsContainer.setPrefHeight(85);
		optionsContainer.setSpacing(0);
		optionsContainer.setPadding(new Insets(0, 0, 0, 60));

		nextBtn.setDisable(true);
		nextBtn.setVisible(false);

		againBtn.setDisable(false);
		againBtn.setVisible(true);

		homeBtn.setVisible(true);
		homeBtn.setDisable(false);
	}

	/**
	 * Quando il bottone Next viene premuto, invia l'opzione scelta al server.
	 * Visualizza un messaggio di errore se non è selezionata alcuna opzione.
	 * 
	 * @param event l'evento del click sul bottone "Avanti"
	 */
	@FXML
	protected void onNextBtnPressed(ActionEvent event) {

		String Disc = AttDiscrete.getText();
		Double Cont = null;
		int K = 0;
	
		// Parse the AttContinue and ValK values only if they are not empty
		if (!AttContinue.getText().isEmpty() && !AttContinue.getText().contains(" ")) {
			Cont = Double.parseDouble(AttContinue.getText());
		}
	
		if (!ValK.getText().isEmpty() && ValK.getText().matches("\\d+")) {
			K = Integer.parseInt(ValK.getText());
		}
	
		if (!Disc.isEmpty() && Cont != null && K != 0 && !Disc.equals(" ")) {
			System.out.println("Starting prediction phase!");
			conn.SendDis(Disc);    
			conn.SendCont(Cont);
			conn.SendK(K);
			classValue = conn.getAnswer();
			showClass(classValue);
		} else {
			Main.getViewLoader().createErrAlert("Controllare i valori inseriti!");
		}
	}
	
	/**
	 * Quando il pulsante Again viene premuto, inizia una nuova previsione, basata
	 * sullo stesso dataset.
	 * 
	 * @param event l'evento generato dal pulsante "Riprova".
	 */
	@FXML
	protected void onAgainBtnPressed(ActionEvent event) {

		refreshInfo();
		AttContinue.setOnAction(null);
		AttDiscrete.setOnAction(null);
		ValK.setOnAction(null);
		conn.startPrediction();
		
		

		image.setImage(new Image("/it/map2223/knnClient/images/undraw_data_processing_yrrv.png"));
		
		nextBtn.setDisable(false);
		nextBtn.setVisible(true);
		againBtn.setDisable(true);
		againBtn.setVisible(false);
		optionsContainer.setAlignment(Pos.CENTER_LEFT);
		optionsContainer.setSpacing(10);
		optionsContainer.setPadding(new Insets(0, 0, 0, 40));
		optionsContainer.setPrefHeight(224);
		homeBtn.setVisible(false);
		homeBtn.setDisable(true);

	}

	/**
	 * Quando il pulsante Home viene premuto, chiude la connessione e ne crea
	 * un'altra per permettere all'utente di scegliere un nuovo dataset.
	 * Successivamente carica la Scena Home.
	 * 
	 * @param event l'evento generato dal clic del pulsante "Home"
	 */
	@FXML
	protected void onHomeBtnPressed(ActionEvent event) {

		conn.endPrediction();
		if (conn.tryConnection()) {
			Main.getViewLoader().loadHome(false);
		}
	}

}
