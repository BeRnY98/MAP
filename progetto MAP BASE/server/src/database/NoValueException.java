package database;

public class NoValueException extends Exception {

	/**
	 *Eccezione lanciata quando si cerca di accedere ad un valore inesistente in un'istanza di Example
	 * @param msg messaggio di errore
	 */
	public NoValueException(String msg) {
		
		super(msg);

	}
}
