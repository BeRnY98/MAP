package it.map2223.knnClient;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Gestisce le impostazioni utente con l'ausilio della classe Preferences.
 * Conserva anche 4 delle tabelle inserite di recente dall'utente.
 * Fornisce metodi per get e set.
 * 
 * @see java.util.prefs.Preferences
 */
public class Settings {
	
	private Preferences prefs;
	
	private String ID1 = "Welcome screen enabled";
    private String ID2 = "Indirizzo del Server";
    private String ID3 = "Port Number";
    private String R1 = "Recent table 1";
    private String R2 = "Recent table 2";
    private String R3 = "Recent table 3";
    private String R4 = "Recent table 4";
    private List<String> recent;

	/**
	 * Classe che rappresenta le impostazioni dell'applicazione.
	 */
	public Settings() {
		recent = new ArrayList<String>();
		prefs = Preferences.userRoot().node(this.getClass().getName());
	}
	
	
	/**
	 * Metodo per abilitare o disabilitare la schermata di benvenuto
	 * @param isEnabled true per abilitare la schermata di benvenuto, false altrimenti
	 */
	public void setWelcomeEnabled(boolean isEnabled) {
		prefs.putBoolean(ID1, isEnabled);
	}
	/**
	 * Metodo per impostare l'indirizzo del server
	 * @param serverAddr indirizzo del server
	 */
	public void setServerAddr(String serverAddr) {
		prefs.put(ID2, serverAddr);
	}
	/**
	 * Metodo per impostare il numero di porta del server
	 * @param port numero di porta del server
	 */
	public void setPortNumber(int port) {
		prefs.putInt(ID3, port);
	}
	
	
	/**
	 * Metodo per ottenere lo stato di abilitazione della schermata di benvenuto
	 * @return true se la schermata di benvenuto è abilitata, false altrimenti
	 */
	public boolean getWelcomeEnabled() {
		return prefs.getBoolean(ID1, true);
	}
	
	
	/**
	 * Metodo per ottenere l'indirizzo del server
	 * @return indirizzo del server
	 */
	public String getServerAddr() {
		return prefs.get(ID2,"localhost");
	}
	
	
	/**
	 * Metodo per ottenere il numero di porta del server
	 * @return numero di porta del server
	 */
	public int getPortNumber() {
		return prefs.getInt(ID3, 2050);
	}

	/**
	 * Metodo per aggiornare la lista delle tabelle recenti
	 * @param tableName nome della tabella appena utilizzata
	 */
	public void refreshRecent(String tableName) {
		if (!recent.contains(tableName)) {
			prefs.put(R4, prefs.get(R3, ""));
			prefs.put(R3, prefs.get(R2, ""));
			prefs.put(R2, prefs.get(R1, ""));
			prefs.put(R1, tableName);
		}
	}
	/**
	 * Metodo per ottenere la lista delle tabelle recenti
	 * @return lista di stringhe contenente i nomi delle tabelle recenti
	 */
	public List<String> getRecent() {
		
		recent.clear();
		recent.add(prefs.get(R1, ""));
		recent.add(prefs.get(R2, ""));
		recent.add(prefs.get(R3, ""));
		recent.add(prefs.get(R4, ""));
		return recent;
	}
}
