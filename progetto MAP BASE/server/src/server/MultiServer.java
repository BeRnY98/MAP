package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



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

    /**
     * Metodo main che avvia il server sulla porta 2050.
     * In caso di eccezione di tipo IOException, viene stampato un messaggio di errore.
     * @param args argomenti passati al main
     */
    public static void main(String[] args) {

        try {
            new MultiServer(2050);
        } catch (IOException e) {
            System.out.println("[MultiServer_main_IOException] : " + e.getMessage());
        }


    }
}