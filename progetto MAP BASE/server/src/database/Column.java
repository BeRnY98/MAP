package database;

/**
 * Modella una colonna della table di un database
 */
public class Column{
	private String name;
	private String type;

	/**
	 * @param name nome della colonna
	 * @param type tipo della colonna
	 */
	public Column(String name, String type){
		this.name = name;
		this.type = type;
	}

	/**
	* @return Il nome della colonna
	*/
	public String getColumnName(){
		return name;
	}

	/**
	* @return se è numerica
	*/
	public boolean isNumber(){
		return type.equals("number");
	}

	/**
	* @return nome più il tipo sottoforma di stringa
	*/
	public String toString(){
		return name+":"+type;
	}
}