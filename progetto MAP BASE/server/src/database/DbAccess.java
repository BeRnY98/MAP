package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * @author Map Tutor
 *
 */
public class DbAccess {

	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";
	private final int PORT = 3306;
	private final String DATABASE = "map";
	private final String USER_ID = "Student";
	private final String PASSWORD = "map1";

	private Connection conn;

	/**
	 * Classe che gestisce la connessione al database.
	 * Viene utilizzato il driver JDBC per interfacciarsi al DBMS specificato.
	 * @throws DatabaseConnectionException se non è possibile connettersi al database
	 */
	public DbAccess() throws DatabaseConnectionException{
		String connectionString =  DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
				+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
	}

	/**
	 * Restituisce l'oggetto Connection utilizzato per la connessione al database
	 * @return l'oggetto Connection utilizzato per la connessione al database
	 */
	public Connection getConnection(){
		return conn;
	}

	/**
	 * Chiude la connessione al database.
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}
}
