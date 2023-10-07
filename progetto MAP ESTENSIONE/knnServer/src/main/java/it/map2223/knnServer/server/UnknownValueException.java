package it.map2223.knnServer.server;

/**
 * Classe che gestisce l'eccezione
 */
public class UnknownValueException extends Exception {

	private static final long serialVersionUID = 14L;

	public UnknownValueException() {}
	/**
	 * Eccezione lanciata quando si tenta di utilizzare un valore sconosciuto o non valido.
	 * @param msg  il messaggio di errore associato all'eccezione.
	 */
	public UnknownValueException(String msg) {
		super(msg);
	}
}
