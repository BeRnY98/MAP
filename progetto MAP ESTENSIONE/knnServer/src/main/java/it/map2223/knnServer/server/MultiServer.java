package it.map2223.knnServer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che gestisce il Multiserve
 */
public class MultiServer {
    
    //Attributi
    private int port = 2050;
    
    //Metodi

    /**
     * Inizializza la porta ed invoca run()
     * @param port il numero di porta su cui il server verrà eseguito.
     * @throws IOException  viene lanciata se si verifica un errore di input/output durante l'esecuzione del server.
     */
    public MultiServer(int port) throws IOException {
        this.port = port;
        System.out.println("inizio server");

        run();
    }

    /**
     * Istanzia un oggetto istanza della classe ServerSocket che si pone in attesa 
     * di richiesta di connessioni da parte del client
     * Ad ogni nuova richiesta connessione si istanzia ServerOneClient
     * @throws IOException e ci sono problemi nella creazione del ServerSocket.
     */
    private void run() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("inizio: " + serverSocket);

        try {
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione avvenuta con successo");

                try {
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    System.out.println("[MultiServer_run_IOexception] : " + e.getMessage());
                }
            }

        } finally {
            System.out.println("[MultiServer_run] : sono nel finally");
            serverSocket.close();
        }

    }

}