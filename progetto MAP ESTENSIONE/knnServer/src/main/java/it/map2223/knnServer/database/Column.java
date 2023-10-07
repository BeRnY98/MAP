package it.map2223.knnServer.database;

/**
 * Modella una colonna della table di un database
 */
public class Column{
	private String name;
	private String type;

	/**
	 * Il costruttore della classe Column crea una nuova colonna con il nome e il tipo specificati.
	 * @param name nome della colonna
	 * @param type tipo della colonna
	 */
	public Column(String name, String type){
		this.name = name;
		this.type = type;
	}
	/**
	 * Il metodo getColumnName restituisce il nome della colonna.
	 * @return Il nome della colonna
	 */
	public String getColumnName(){
		return name;
	}

	/**
	 * Il metodo isNumber restituisce true se il tipo della colonna è "number", false altrimenti.
	 * @return se è numerica
	 */
	public boolean isNumber(){
		return type.equals("number");
	}

	/**
	 * Il metodo toString restituisce una rappresentazione della colonna come stringa, nel formato "nome:tipo".
	 * @return nome più il tipo sottoforma di stringa
	 */
	public String toString(){
		return name+":"+type;
	}
}