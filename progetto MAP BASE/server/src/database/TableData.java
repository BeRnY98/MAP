package database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import example.Example;

/**
 * Modella l’insieme di transazioni collezionate in una tabella
 */
public class TableData {

	private DbAccess db;
	private String table;
	private TableSchema tSchema;
	private ArrayList<Example> transSet;
	private ArrayList<Object> target;

	/**
	 * Costruisce un'istanza della classe TableData utilizzando il database specificato e lo schema della tabella specificato
	 * @param db oggetto di tipo DbAccess utilizzato per la connessione al database
	 * @param tSchema schema della tabella di cui costruire l'istanza di TableData
	 * @throws SQLException in caso di errori nella connessione al database o nella lettura dei dati dalla tabella
	 * @throws InsufficientColumnNumberException se la tabella non contiene un numero sufficiente di colonne
	 */
	public TableData(DbAccess db, TableSchema tSchema) throws SQLException,InsufficientColumnNumberException{
		this.db = db;
		this.tSchema = tSchema;
		this.table = tSchema.getTableName();
		transSet = new ArrayList<Example>();
		target = new ArrayList<Object>();	
		init();
	}
	
    /**
	 * Inizializza l'oggetto TableData interrogando il database tramite la connessione fornita dal DbAccess passato al
	 * costruttore e caricando i dati nella struttura dati interna. Vengono create nuove istanze di Example per ogni tupla del
	 * dataset, in cui i valori sono settati nel rispettivo indice, e aggiunti a transSet.
	 * @throws SQLException  in caso di problemi di accesso al database
	 */
	private void init() throws SQLException{		
		String query="select ";
		
		for(Column c:tSchema){			
			query += c.getColumnName();
			query+=",";
		}
		query +=tSchema.target().getColumnName();
		query += (" FROM "+table);
		
		Statement statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			Example currentTuple=new Example(tSchema.getNumberOfAttributes());
			int i=0;
			for(Column c:tSchema) {
				if(c.isNumber())
					currentTuple.set(rs.getDouble(i+1),i);
				else
					currentTuple.set(rs.getString(i+1),i);
				i++;
			}
			transSet.add(currentTuple);
			
			if(tSchema.target().isNumber())
				target.add(rs.getDouble(tSchema.target().getColumnName()));
			else
				target.add(rs.getString(tSchema.target().getColumnName()));
		}
		rs.close();
		statement.close();	
	}
	
	

	/**
	 * Restituisce l'insieme di esempi rappresentati dalla tabella
	 * @return ArrayList di Example contenente gli esempi della tabella
	 */
	public ArrayList<Example> getExamples(){
		return transSet; 
	}
	
	/**
	 * @return l'ArrayList contenente i valori del target.
	 */
	public ArrayList<Object> getTargetValues(){
		return target; 
	}
	
	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore MIN o MAX di column
	 * @param column la colonna per cui si vuole calcolare il valore aggregato.
	 * @param aggregate il tipo di aggregazione da effettuare (MIN o MAX).
	 * @throws SQLException se si verifica un errore durante l'esecuzione della query.
	 * @return il valore aggregato.
	 */
	public Double getAggregateColumnValue(Column column, QUERY_TYPE aggregate) throws SQLException {
		Double value = new Double(0);
		String query = "select " + aggregate + "(" + column.getColumnName() + ")" + " from " + table;
		
		System.out.println(query);
		
		Statement statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getDouble(1);
		}
		
		System.out.println(value);
		
		return value;
	}
	
}