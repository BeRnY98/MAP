package data;

import java.io.Serializable;

/**
 * La classe modella un generico attributo discreto o continuo
 */
public abstract class Attribute implements Serializable {

	private final String name;
	private int index;
	
	public Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }
/**
 * Restituisce il nome dell'oggetto KNN corrente.
 * @return una stringa che rappresenta il nome dell'oggetto KNN corrente.
 */
	public String getName() {
        return new String(name);
    }
    
/**
 * Restituisce l'indice dell'oggetto KNN corrente.
 * @return l'indice dell'oggetto KNN corrente come un intero
 */
    public int getIndex() {
        return index;
    }
	
/**
 *Restituisce una rappresentazione in formato stringa dell'oggetto Attribute corrente.
 * @return una stringa che rappresenta l'oggetto Attribute corrente.
 */
	public String toString() {
        return "Attribute [index=" + index + ", name=" + name + "]";
    }
	
}