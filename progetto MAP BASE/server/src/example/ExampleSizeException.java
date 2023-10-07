package example;

public class ExampleSizeException extends RuntimeException {

    public ExampleSizeException(){};
    /**
     * Eccezione personalizzata che viene lanciata quando si cerca di eseguire operazioni con due esempi di dimensioni differenti.
     * @param s la stringa di descrizione dell'errore
     */
    public ExampleSizeException(String s){
        
        super(s);

    }
}

