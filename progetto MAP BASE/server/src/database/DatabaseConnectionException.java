package database;

/**
 * Modella il fallimento della connessione al Database
 */
public class DatabaseConnectionException extends Exception {

	/**
	 * Eccezione che rappresenta un errore di connessione al database.
	 * @param msg messaggio di errore dell'eccezione.
	 */
	public DatabaseConnectionException(String msg){
		super(msg);
	}
	
}

