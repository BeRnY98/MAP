package  it.map2223.knnServer.data;

import java.io.Serializable;

/**
 * Estende la classe Attribute e rappresenta un attributo continuo
 * Modella un attributo continuo
 */
public class ContinuousAttribute extends Attribute implements Serializable {
	
	private double min = Double.POSITIVE_INFINITY;
	private double max = Double.NEGATIVE_INFINITY;

	/**
	 * Imposta il valore minimo dell'oggetto Double corrente.
	 * @param v il valore minimo da impostare per l'oggetto Double corrente.
	 */
	public void setMin(Double v) {
		if(v < min) {
			min = v;
		}
	}

	/**
	 * Imposta il valore massimo dell'oggetto Double corrente.
	 * @param v il valore massimo da impostare per l'oggetto Double corrente.
	 */
	public void setMax(Double v) {
		if(v > max) {
			max = v;
		}
	}

	/**
	 * Restituisce il valore scalato dell'oggetto Double corrente
	 * @param value  il valore da scalare.
	 * @return il valore scalato dell'oggetto Double corrente come un valore double
	 */
	public double scale(Double value) {
		return ((value - min) / (max - min));
	}

	/**
	 * Costruisce un nuovo oggetto ContinuousAttribute con il nome e l'indice specificati.
	 * @param nm il nome dell'attributo.
	 * @param idx l'indice dell'attributo.
	 */
	public ContinuousAttribute(String nm, int idx) {
		super(nm, idx);
	}

	/**
	 * Restituisce una rappresentazione in formato stringa dell'oggetto ContinuousAttribute, includendo il nome, l'indice, il valore minimo e il valore massimo.
	 * @return una stringa che rappresenta l'oggetto ContinuousAttribute.
	 * */
	public String toString() {
		return super.toString() + " min: " + min + " max: " + max;
	}

}
