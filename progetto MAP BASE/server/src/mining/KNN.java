package mining;
import java.io.Serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.Data;
import example.Example;

/**
 * Modella il miner.
 */
public class KNN implements Serializable {
    
    //Attributi
    private Data data;

    //Metodi
    
    /**
     * Avvalora il training set
     * @param trainingSet il trainingset che riceve in input dal client.
     */
    public KNN (Data trainingSet) {
        data = trainingSet;
    } 

    /**
     * Questo metodo predice il valore target dell'esempio passato come parametro.
     * @param out l'oggetto ObjectOutputStream utilizzato per scrivere l'esempio.
     * @param in l'oggetto ObjectInputStream utilizzato per leggere l'esempio.
     * @return il valore target previsto per l'esempio passato come parametro.
     * @throws IOException  se si verifica un errore di input/output durante la lettura o la scrittura dell'esempio.
     * @throws ClassNotFoundException se la classe dell'oggetto letto dallo stream di input non viene trovata.
     * @throws ClassCastException se l'oggetto letto dallo stream di input non può essere convertito in un intero.
     */
    public double predict (ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, ClassCastException{
     Example e = data.readExample(out,in);
     int k=0;
     out.writeObject("Inserisci valore di k con k>=1:");
     k=(Integer)(in.readObject());
     return data.avgClosest(e, k);
}
/**
 * Restituisce una rappresentazione stringa dell'oggetto KNN corrente.
 * @return una stringa che rappresenta l'oggetto KNN corrente.
 */
    public String toString() {
        return "KNN [data=" + data + "]/n";
    } 
    
    /**
     * Salva la sessione corrente
     * @param nomeFile il nome del file in cui salvare la sessione corrente.
     * @throws IOException se si verifica un errore di input/output durante la scrittura del file.
     */
    public void salva(String nomeFile) throws IOException {
		FileOutputStream outFile = new FileOutputStream(nomeFile);
		ObjectOutputStream outstream = new ObjectOutputStream(outFile);
		outstream.writeObject(this);
        outstream.close();
	}
    
    /**
     * Carica sessione da file
     * @param nomeFile il nome del file da cui caricare la sessione KNN.
     * @return la sessione KNN caricata dal file specificato.
     * @throws IOException se si verifica un errore di input/output durante la lettura del file.
     * @throws ClassNotFoundException se la classe dell'oggetto letto dallo stream di input non viene trovata.
     */
	public static KNN carica(String nomeFile) throws IOException, ClassNotFoundException {

		FileInputStream inFile = new FileInputStream(nomeFile);
		ObjectInputStream inStream = new ObjectInputStream(inFile);
		KNN knn = (KNN) inStream.readObject();
		inStream.close();

		return knn;
	}

}
