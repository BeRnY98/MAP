package it.map2223.knnClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



	/**
 * Gestisce attributi relativi alla connessione con il server e fornisce 
 * metodi per stabilire la connessione, inviare il nome del dataset e effettuare 
 * la fase di previsione.
 * E' possibile avere una sola istanza attiva perchè il client può essere connesso ad un 
 * solo server alla volta.
 *
 */
public class Client {

	private static Client instance = null;
	
	private String serverName;
	private int port;
	private InetAddress addr;
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

		/**
		 * Questa è una funzione privata che crea una nuova istanza della classe Client con il nome del server e la porta specificati come argomenti.
		 * @param serverName il nome del server a cui connettersi.
		 * @param port il numero di porta a cui connettersi.
		 */
	private Client(String serverName, int port) {
		this.serverName = serverName;
		this.port = port;
	}
	
	/**
	 * Se non esiste un'istanza della classe, ne crea una e la restituisce.
	 * @param serverName nome host
	 * @param port numero di porta
	 * @return Istanza della classe Connection
	 */
	public static Client getInstance(String serverName, int port) {
		
		if(instance == null) {
			instance = new Client(serverName, port);
		}
		return instance;
	}
	
	/**
	 * Tenta di stabilire una connessione al server specificato 
	 * alla creazione dell'istanza.
	 * @return true connessione stabilita, false altrimenti
	 */
    public boolean tryConnection() {
    	
		try {
			addr = InetAddress.getByName(serverName);
		} catch (UnknownHostException e) {
			return false;
		}

		try {
			socket = new Socket(addr, Integer.valueOf(port).intValue());
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e) {
			return false;
		}
		
		return true;
    }

	 /**
     * Invia al server il nome della tabella da cui apprendere specificando il luogo dove cercarla.
     * 
     * @param decision Specifica la posizione della tabella: 1 - Tabella da File;
     * 2 - Tabella da File bianario
	 * 3 - Tabella da Database
     * @param tableName Nome della tabella da cui apprendere i dati
     * @return true se l'apprendimento va a buon fine, false se la tabella non esiste o qualcosa va storto
     */
    public boolean learnKnn(int decision, String tableName) {
    	
    	String risposta;
		try{

		if (decision == 1) {
				System.out.println("Starting data acquisition phase from file!");

				out.writeObject(1);
				out.writeObject(tableName);
				risposta = (String)in.readObject();
				
				if (!risposta.contains("@OK")) {
					Main.getViewLoader().createErrAlert("Tabella inesistente");
					return false;
				}

				System.out.println("Starting learning phase!");

			} else if(decision == 2) {
				System.out.println("Starting data acquisition phase from binary file!");

				out.writeObject(2);
				out.writeObject(tableName);
				risposta = (String)in.readObject();
				
				if (!risposta.contains("@OK")) {
					Main.getViewLoader().createErrAlert("Tabella inesistente");
					return false;
				}

				System.out.println("Starting learning phase!");

			} else if(decision == 3) {
				System.out.println("Starting data acquisition phase from database!");

				out.writeObject(3);
				out.writeObject(tableName);
				risposta = (String)in.readObject();
				
				if (!risposta.contains("@OK")) {
					Main.getViewLoader().createErrAlert("Tabella inesistente");
					return false;
				}

				System.out.println("Starting learning phase!");
			}
			
			out.writeObject(4);
			risposta = (String)in.readObject();
			if (!risposta.contains("@READSTRING")) {
				System.out.println(risposta);
				return false;
			}
			} catch (NullPointerException e) {
			Main.getViewLoader().createErrAlert("Tabella inesistente");
			return false;
		}  catch (Exception e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return false;
		}
		
		return true;
	}
	
	/**
     * Invia al server il segnale che indica l'inizio della fase di previsione.
     * 
     * @return true segnale inviato correttamente, false altrimenti
     */
    public boolean startPrediction() {
		try {
			out.writeObject(4);
			String risposta = (String)in.readObject();
			if (!risposta.contains("@READSTRING")) {
				System.out.println(risposta);
				return false;
			}
			System.out.println("Starting prediction phase!");
			return true;
			
		} catch (Exception e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return false;
		}
    }
/**
     * Invia al server il segnale che indica la fine della fase di previsione.
     * 
     * @return true segnale inviato correttamente, false altrimenti
     */
    public boolean endPrediction() {
    	
		try {
			out.writeObject(5);
			System.out.println("Ending prediction phase!");
			return true;
			
		} catch (Exception e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return false;
		}
    }

	/**Invia al server il valore Discreto e
	 * @return true se riesce a trasmetterlo correttamente
	 * @param val valore discreto da inviare al server
     */

	 public boolean SendDis(String val) {
		try {
			String answer="@ERROR";
			try {
				answer = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(answer.contains("Inserisci valore discreto")){
			out.writeObject(val);}
			try {
				answer = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			} catch (IOException e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return false;
		}
		return true;

    }

	/**Invia al server il valore Continuo e
	 * @return vero se riesce a trasmetterlo correttamente
	 * @param val se l'invio del valore continuo è stato eseguito con successo, false altrimenti
     */

	public boolean SendCont(Double val) {
		try {
			String answer="@ERROR";
			try {
				answer = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(answer.contains("Inserisci valore continuo")){
			
			out.writeObject(val);			
			}
			try {
				answer=(String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			} catch (IOException e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return false;
		}
		return true;

    }

	/**Invia al server il valore di K e
	 * @return vero se riesce a trasmetterlo correttamente
	 * @param val se l'invio del valore di k è stato eseguito con successo, false altrimenti
     */

	public boolean SendK(Integer val) {
		try {
			String answer="@ERROR";
			try {
				answer = (String)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(answer.contains("Inserisci valore di k con")){
			
				out.writeObject(val);			
				}
				
		}catch (IOException e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return false;
		}
		
		return true;
	}
	/**
	 *Restituisce il valore di predizione.
	 *@return la risposta della previsione sotto forma di String.
	 *La stringa "Error" se qualcosa va storto.
     */
    public String getAnswer() {
    	
		try {
    		
			return in.readObject().toString() ;
			
		} catch (Exception e) {
			Main.getViewLoader().createErrAlert(e.toString());
			return "@ERROR";
		}

	}
    
}

