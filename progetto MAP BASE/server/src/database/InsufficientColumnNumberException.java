package database;

/**
 * Eccezione in caso di numero insufficiente di colonne
 */
public class InsufficientColumnNumberException extends Exception {

	/**
	 * Eccezione che viene lanciata quando il numero di colonne di una tabella non è sufficiente per l'operazione richiesta
	 * @param msg messaggio di errore associato all'eccezione
	 */
	public InsufficientColumnNumberException(String msg) {
		
		 super(msg);

	}
}
