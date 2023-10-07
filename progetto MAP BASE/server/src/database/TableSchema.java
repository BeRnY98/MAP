package database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



/**
 * Modella lo schema di una tabella in un database
 * Conserva una lista di oggetti Column
 */
public class TableSchema implements Iterable<Column>{
	
	private List<Column> tableSchema=new ArrayList<Column>();
	private Column target;
	private String tableName;
	
	/**
	 * La classe TableSchema rappresenta lo schema di una tabella del database, che viene descritto attraverso la lista delle sue colonne.
	 * Viene utilizzata per estrarre lo schema delle tabelle del database e convertire i tipi di dati SQL in tipi di dati Java
	 * @param tableName il nome della tabella di cui si vuole estrarre lo schema
	 * @param db  un oggetto DbAccess che rappresenta la connessione al database
	 * @throws SQLException se si verifica un errore durante l'accesso al database
	 * @throws InsufficientColumnNumberException se la tabella selezionata contiene meno di due colonne
	 */
	public TableSchema(String tableName, DbAccess db) throws SQLException,InsufficientColumnNumberException{
		this.tableName=tableName;
		
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		 DatabaseMetaData meta = db.getConnection().getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
	     
		   
	     while (res.next()) {
	         
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        	if(res.isLast()) 
	        		target=new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 ;
	        	else
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	
	         
	         
	      }
	     
	      res.close();
	      if(target==null || tableSchema.size()==0) throw new InsufficientColumnNumberException("La tabella selezionata contiene meno di due colonne");
		
		}


	/**
	 * Restituisce l'oggetto Column che rappresenta la colonna target della tabella
	 * @return l'oggetto Column che rappresenta la colonna target della tabella.
	 */
	public Column target(){
			return target;
		}

	/**
	 *La funzione getNumberOfAttributes restituisce il numero di colonne presenti nella tabella del dataset.
	 * @return il numero di colonne presenti nella tabella del dataset
	 */
	public int getNumberOfAttributes() {
			return tableSchema.size();
		}

	/**
	 * La funzione getTableName restituisce il nome della tabella rappresentata dall'oggetto TableSchema.
	 * @return il nome della tabella.
	 */
	public String getTableName() {
			return tableName;
		}

	/**
	 * Restituisce un iteratore che itera attraverso le colonne della tabella.
	 * @return un iteratore per le colonne della tabella
	 */
	@Override
		public Iterator<Column> iterator() {
			return tableSchema.iterator();
		}
}

		     


