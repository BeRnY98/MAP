package it.map2223.knnServer.data;

import java.io.Serializable;
/**
 * La classe modella un generico attributo discreto o continuo
 */
public abstract class Attribute implements Serializable {

	private final String name;
	private int index;

    /**
     * Il costruttore della classe Attribute crea un nuovo attributo con il nome e l'indice specificati.
     * @param name i l nome dell'attributo
     * @param index l'indice dell'attributo
     */
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