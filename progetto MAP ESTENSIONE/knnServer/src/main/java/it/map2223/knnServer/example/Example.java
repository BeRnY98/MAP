package it.map2223.knnServer.example;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Modella i valori degli attributi indipendenti di un esempio
 */
public class Example implements Serializable {

    //Attributi
    private ArrayList<Object> example; 

    //Metodi
    /**
     * Costruisce un nuovo oggetto Example con la dimensione specificata.
     * @param size  la dimensione dell'esempio
     */
    public Example(int size) {
        example = new ArrayList<Object>(size);
     }

     /**
     * Imposta il valore dell'attributo dell'esempio nella posizione specificata
     * @param o il valore da impostare
     * @param index l'indice dell'attributo da impostare
     */
    public void set(Object o, int index) {  
        if(index < example.size()) {
			example.set(index, o);
		} else {
			example.add(index, o);
		}
    }

    /**
     * Restituisce l'oggetto all'indice specificato.
     * @param index indice dell'oggetto da restituire
     * @return Object all'indice specificato
     */
    public Object get(int index) {                
        return example.get(index);
    }

    /**
     * Scambia i valori contenuti nel campo example 
     * dell’ oggetto corrente con i valori contenuti nel campo example del parametro e
     * @param e istanza di example
     */
    public void swap(Example e) {
        if(example.size() != e.example.size())
            throw new ExampleSizeException("Example con dimensioni diverse");
        for(int iObject = 0; iObject<example.size(); iObject++) {
            Object tmp = get(iObject);
            set(e.get(iObject), iObject);
            e.set(tmp, iObject);            
        }
    }

    /**
     * Calcola e restituisce la distanza di Hamming 
     * calcolata tra l’istanza di Example passata come parametro e quella corrente
     * @param e l'istanza con cui calcolare la distanza
     * @return la distanza tra le due istanze
     * @throws ExampleSizeException se le due istanze hanno lunghezze differenti
     */
    public double distance(Example e) {
    Double distanza = 0.0;
        if (example.size() != e.example.size()) {
            throw new ExampleSizeException("Lunghezze dei vettori differenti");
        }
        for (int i = 0; i < example.size(); i++) {
            if (!(example.get(i).equals(e.example.get(i)))) {
                if (example.get(i) instanceof String)
                    distanza++;
                else
                    distanza = distanza + Math.abs((Double) example.get(i) - (Double) e.example.get(i));
            }
        }
        return distanza; 
        
    
    }

    /**
     * Effettua il calcolo della distanza sul singolo campo dell'example
     * @param first primo elemento di confronto
     * @param second secondo elemento di confronto
     * @return dist
     */
    public double singleDistance(Object first, Object second) {
        double dist = 0;
        if(first instanceof String && second instanceof String) {
            if(((String)first).compareTo((String)second)!=0) {
                dist = 1;
            } 
        } else if(first instanceof Double && second instanceof Double) {
            dist = Math.abs(((Double)first - (Double)second));
        }
        return dist;
    }
    /**
     * Restituisce la dimensione del vettore rappresentato dall'oggetto Example.
     * @return la dimensione del vettore
     */
    public int size() {
     return example.size();
    }
    /**
     * Restituisce una rappresentazione testuale dell'istanza della classe Example.
     * @return la stringa rappresentante l'oggetto Example.
     */
    public String toString() {
        return "Example [example=" + example + "]";
    }
   
}
