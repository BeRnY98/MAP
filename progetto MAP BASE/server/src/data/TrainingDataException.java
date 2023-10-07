package data;

public class TrainingDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public TrainingDataException() { }
/**
 * Eccezione che viene sollevata in caso di problemi con i dati di training.
 * @param message il messaggio di errore da mostrare
 */
	public TrainingDataException(String message) {
		super(message);
	}

}
