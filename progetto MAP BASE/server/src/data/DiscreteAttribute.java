package data;

import java.io.Serializable;

/**
 * Estende la classe Attribute e rappresenta un attributo discreto
 */
 public class DiscreteAttribute extends Attribute implements Serializable {
    
    /**
     * Costruisce una nuova istanza di DiscreteAttribute.
     * @param name  il nome dell'attributo
     * @param index l'indice dell'attributo
     */
	public DiscreteAttribute(String name, int index) { 
        super(name, index);
    }

}

