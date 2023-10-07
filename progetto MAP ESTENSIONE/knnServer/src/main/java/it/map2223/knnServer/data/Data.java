package it.map2223.knnServer.data;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import it.map2223.knnServer.database.*;
import it.map2223.knnServer.example.Example;
import it.map2223.knnServer.example.ExampleSizeException;

/**
 * Conserva il training set e le informazioni ad esso relative
 */
public class Data implements Serializable {
    
    //Attributi
    private final ArrayList<Example> data;
    private final ArrayList<Double> target;
    private final int numberOfExamples;
    private final ArrayList<Attribute> explanatorySet;
    private ContinuousAttribute classAttribute;
    
    //Metodi
    @Override
    /**
     * Restituisce una rappresentazione in formato stringa dell'oggetto Data, includendo il class attribute, i dati, l'insieme degli attributi esplicativi, il numero di esempi e l'attributo target.
     * @return una stringa che rappresenta l'oggetto Data.
     */
	public String toString() {
		return "Data [classAttribute=" + classAttribute + ",\n data=" + data + ",\n explanatorySet=" + explanatorySet
				+ ",\n numberOfExamples=" + numberOfExamples + ",\n target=" + target + "]";
	}

    /**
     * Modella il Training Set. Legge il file di input e popola le strutture dati necessarie al fine di
     * rappresentare il dataset di training.
     * @param fileName il nome del file contenente il dataset di training
     * @throws TrainingDataException se si verifica un errore nell'analisi del file di input.
     * @throws FileNotFoundException se il file di input non viene trovato.
     */
    @SuppressWarnings("removal")
    public Data(String fileName) throws TrainingDataException, FileNotFoundException {

        File inFile = new File(fileName);

        if (!inFile.exists()) {
            throw new TrainingDataException("File non trovato");
        }

        Scanner sc = new Scanner(inFile);
        String line = sc.nextLine().trim();
        String[] s = line.split(" ");

        if (!s[0].contains("@schema")) {
            throw new TrainingDataException("Parametro @schema non dichiarato");
        }

        if (s.length > 2) {
            throw new TrainingDataException("Numero di parametri di @schema non valido");
        } else if (s.length == 1) {  // s[0] = "@schema"; s[1] = "4";
            throw new TrainingDataException("Valore dello @schema non dichiatato");
        } else {
            boolean isNum = s[1].matches("[0-9]+");  //utilizzo di una espressione regolare TODO controllare se accetta valori decimali

            if (!isNum) {
                throw new TrainingDataException("Il valore inserito del parametro @schema non e' numerico");
            }
        }

        //Popola explanatory Set 
        explanatorySet = new ArrayList<>(Integer.parseInt(s[1]));
        short iAttribute = 0;
        line = sc.nextLine().trim();

        while (!line.contains("@data") && sc.hasNextLine()) {

            s = line.split(" ");

            if (s[0].equals("@desc")) {

                if (s.length == 3) {
                    if (s[2].equalsIgnoreCase("discrete")) {
                        explanatorySet.add(iAttribute, new DiscreteAttribute(s[1], iAttribute));
                    } else if (s[2].equalsIgnoreCase("continuous")) {
                        explanatorySet.add(iAttribute, new ContinuousAttribute(s[1], iAttribute));
                    }
                } else {
                    throw new TrainingDataException("Parametri @desc mancante");
                }

            } else if (s[0].equals("@target")) {

                if (s.length == 2) {
                    classAttribute = new ContinuousAttribute(s[1], iAttribute);
                } else {
                    throw new TrainingDataException("Parametro @target mancante");
                }

            }

            iAttribute++;
            line = sc.nextLine().trim();
        }

        if (!sc.hasNextLine()) {
            throw new TrainingDataException("Parametro @data mancante");
        }

        if (line.split(" ").length > 2) {
            throw new TrainingDataException("Sinstatti parametro @data non corretta");

        } else if ((line.split(" ").length != 2)) {
            throw new TrainingDataException("Valore parametro @data non specificato");
        } else {
            boolean isNum = line.split(" ")[1].matches("[0-9]+");  

            if (!isNum) {
                throw new TrainingDataException("Il valore inserito del parametro @data non e' numerico");
            }
        }

        //Avvalora numero di esempi
        numberOfExamples = Integer.parseInt(line.split(" ")[1]);

        //Popola data e target
        data = new ArrayList<Example>(numberOfExamples);
        target = new ArrayList<Double>(numberOfExamples);

        short iRow = 0;

        while (sc.hasNextLine() && iRow < numberOfExamples) {
            Example e = new Example(getNumberofExplanatoryAttributes());
            line = sc.nextLine().trim();

            s = line.split(",");
            for (short jColumn = 0; jColumn < s.length - 1; jColumn++) {

                if (s[jColumn].matches("[0-9]+[\\.]?[0-9]*")) {
                    e.set(Double.parseDouble(s[jColumn]), jColumn);
                } else {
                    e.set(s[jColumn], jColumn);
                }
            }

            data.add(iRow, e);
            target.add(iRow, new Double(s[s.length - 1]));

            int k = 0;
            for (Attribute a : explanatorySet) {
                if (a instanceof ContinuousAttribute) {
                    ContinuousAttribute continuousAttribute = (ContinuousAttribute) a;
                    continuousAttribute.setMax((Double) e.get(k));
                    continuousAttribute.setMin((Double) e.get(k));
                }

                k++;
            }

            iRow++;
        }

        if (sc.hasNextLine()) {
            System.out.println("Numero di esempi maggiore di quanto dichiarato nel parametro @data");
            System.out.println("Sono stati presi i primi " + numberOfExamples + " esempi");
        }

        sc.close();
    } //Fine costruttore	


    /**
     * Questo metodo inizializza una nuova istanza di Data a partire dai dati estratti da una tabella in un database.
     * @param db L'istanza di DbAccess utilizzata per accedere al database.
     * @param table Il nome della tabella da cui estrarre i dati.
     * @throws InsufficientColumnNumberException Se ci sono problemi con il numero di colonne della tabella.
     * @throws SQLException Se si verificano problemi durante l'interazione con il database.
     */
    public Data(DbAccess db, String table) throws InsufficientColumnNumberException, SQLException {

        TableSchema tableSchema = new TableSchema(table, db);
        TableData tableData = new TableData(db, tableSchema);

        explanatorySet = new ArrayList<Attribute>();

        Iterator<Column> itSchema = tableSchema.iterator();
        int counter = 0;
        while (itSchema.hasNext()) {
            Column col = itSchema.next();

            if (!col.isNumber()) {
                explanatorySet.add(counter, new DiscreteAttribute(col.getColumnName(), counter));
            } else {
                explanatorySet.add(counter, new ContinuousAttribute(col.getColumnName(), counter));
                ((ContinuousAttribute) explanatorySet.get(counter)).setMin(tableData.getAggregateColumnValue(col, QUERY_TYPE.MIN));
                ((ContinuousAttribute) explanatorySet.get(counter)).setMax(tableData.getAggregateColumnValue(col, QUERY_TYPE.MAX));
            }

            counter++;
        }

        numberOfExamples = tableData.getExamples().size();

        data = new ArrayList<>(numberOfExamples);
        target = new ArrayList<>(numberOfExamples);

        Iterator<Example> itData = tableData.getExamples().iterator();
        Iterator<?> itTarget = tableData.getTargetValues().iterator();
        int i = 0;
        while (itData.hasNext()) {
            data.add(i, itData.next());
            target.add(i, (Double) itTarget.next());
            i++;
        }

    }

    /**
     * Questa funzione restituisce il numero di attributi esplicativi presenti nell'oggetto Data.
     * @return Il numero di elementi dell'explanatorySet
     */
    public int getNumberofExplanatoryAttributes() {
        return explanatorySet.size();
    }


    /**
     * Partiziona l'ArrayList 'key' rispetto all'elemento in posizione mediana tra 'inf' e 'sup',    * @param key
     * utilizzando l'algoritmo QuickSort, e restituisce il punto di separazione.
     * @param key L'ArrayList di Double su cui eseguire la partizione.
     * @param inf L'indice iniziale dell'intervallo di partizionamento.
     * @param sup L'indice finale dell'intervallo di partizionamento.
     * @return L'indice del punto di separazione ottenuto dalla partizione.
     */
    private int partition(ArrayList<Double> key, int inf, int sup) {
        int i = inf, j = sup;
        int med = (inf + sup) / 2;

        Double x = key.get(med);

        data.get(inf).swap(data.get(med));

        double temp = target.get(inf);
        target.set(inf, target.get(med));
        target.set(med, temp);

        temp = key.get(inf);
        key.set(inf, key.get(med));
        key.set(med, temp);

        while (true) {

            while (i <= sup && key.get(i) <= x) {
                i++;
            }

            while (key.get(j) > x) {
                j--;
            }

            if (i < j) {
                data.get(i).swap(data.get(j));

                temp = target.get(i);
                target.set(i, target.get(j));
                target.set(j, temp);

                temp = key.get(i);
                key.set(i, key.get(j));
                key.set(j, temp);

            } else {
                break;
            }
        }

        data.get(inf).swap(data.get(j));

        temp = target.get(inf);
        target.set(inf, target.get(j));
        target.set(j, temp);

        temp = key.get(inf);
        key.set(inf, key.get(j));
        key.set(j, temp);

        return j;

    }

    /**
     * Algoritmo di ordinamento quicksort per l'ordinamento dell'attributo target di data
     * data utilizzando come relazione d'ordine totale "<=" definita su key.
     * Vengono quindi ordinati contemporaneamente gli elementi di data e il loro corrispondente target.
     * @param key ArrayList di Double contenente i valori su cui effettuare l'ordinamento
     * @param inf inf indice dell'elemento iniziale dell'ArrayList su cui effettuare l'ordinamento
     * @param sup indice dell'elemento finale dell'ArrayList su cui effettuare l'ordinamento
     */
    private void quicksort(ArrayList<Double> key, int inf, int sup) {

        if (sup >= inf) {

            int pos;

            pos = partition(key, inf, sup);

            if ((pos - inf) < (sup - pos + 1)) {
                quicksort(key, inf, pos - 1);
                quicksort(key, pos + 1, sup);
            } else {
                quicksort(key, pos + 1, sup);
                quicksort(key, inf, pos - 1);
            }

        }

    }


    /**
     * 1) Avvalora key con le distanze calcolate tra ciascuna istanza di Example memorizzata in data ed e (usando il metodo distance di Example)
     * 2) ordina data, target e key in accordo ai valori contenuti in key (usando quicksort)
     * 3) identifica gli esempi di data che sono associati alle k distanze più piccole in key
     * 4) calcola e restituisce la media dei valori memorizzati in target in corrispondenza degli esempi isolati al punto 3
     * @param e  l'istanza di Example di cui calcolare i vicini più prossimi
     * @param k il numero di vicini più prossimi da considerare
     * @return la media dei valori target associati ai k esempi più vicini
     * @throws RuntimeException se il valore di k è non valido
     */
    public double avgClosest(Example e, int k) {
        if (k <= 0) {
			throw new RuntimeException("Valore K non corretto");
		}
	
		ArrayList<Double> key = new ArrayList<Double>(data.size());
		e = scaledExample(e);
		
		Iterator<Example> it = data.iterator();
		Example x;
		while (it.hasNext()) {
			x = it.next();
			key.add(scaledExample(x).distance(e));
		}
	
		quicksort(key, 0, this.data.size() - 1);
	
		double sum = 0;
		int count = 0;
		sum += target.get(0);
		count++;
	
	
		for (int i = 1; i < key.size() && k > 0; i++) {
			if (!key.get(i - 1).equals(key.get(i))) {
				k--;
			}
			
			if (k > 0) {
				sum += target.get(i);
				count++;
			}
		}
		return sum / count;
    }


    /**
     * Modella un'istanza di Example in base agli attributi dell'explanatory Set 
     * @param out l'oggetto ObjectOutputStream su cui scrivere gli output.
     * @param in  l'oggetto ObjectInputStream da cui leggere gli input.
     * @return un'istanza di Example costruita a partire dai valori inseriti.
     * @throws IOException  se si verificano errori di I/O durante la lettura e la scrittura.
     * @throws ClassNotFoundException se si tenta di leggere un oggetto di una classe non definita nel classpath corrente.
     */
    public Example readExample(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        Example e = new Example(getNumberofExplanatoryAttributes());

        int i = 0;
        for (Attribute a : explanatorySet) {
            if (a instanceof DiscreteAttribute) {
                out.writeObject("@READSTRING");
                out.writeObject("Inserisci valore discreto X[" + i + "]:");
                e.set((in.readObject()), i);
            } else {
                out.writeObject("@READNUMBER");
                double x;
                do {
                    out.writeObject("Inserisci valore continuo X[" + i + "]:");
                    x = (double)(in.readObject());
                } while (new Double(x).equals(Double.NaN));
                e.set(x, i);
            }
            i++;
        }
        out.writeObject("@ENDEXAMPLE");
        return e;
    }


    /**
     * Restituisce una nuova istanza di Example che contiene i valori discreti di e senza
     * modifiche e i valori continui di e scalati tra 0 e 1
     * @param e istanza di Example da andare a sostituire
     * @return newE nuova istanza di Example con valori continui scalati
     * @throws ExampleSizeException se la lunghezza dell'example non è uniforme all'ExplanatorySet
     */
    private Example scaledExample(Example e) {
        Example newE = new Example(explanatorySet.size());

		if(explanatorySet.size() != e.size())
			throw new ExampleSizeException("Lunghezza dell'example non uniforme all'ExplanatorySet.");

		for(int jColumn = 0; jColumn < e.size(); jColumn++) {
			if(explanatorySet.get(jColumn) instanceof DiscreteAttribute) {
				newE.set(e.get(jColumn), jColumn);
			} else if(explanatorySet.get(jColumn) instanceof ContinuousAttribute) {
				try {
					//utilizziamo variabili locali per leggibilità//
					Double value = (Double) e.get(jColumn);
					ContinuousAttribute jAttribute = (ContinuousAttribute) explanatorySet.get(jColumn);
					newE.set(jAttribute.scale(value), jColumn);
				} catch(ClassCastException ex) {
					System.out.println("Impossibile effettuare lo scalare se entrambi i valori non sono continui o discreti.");									
				}
			}
		}
		return newE;
    }

}
