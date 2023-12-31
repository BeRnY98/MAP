package server;

import data.Data;
import data.TrainingDataException;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.InsufficientColumnNumberException;
import mining.KNN;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class ServerOneClient extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    /**
	 * Inizializza gli attributi socket, in e out
     * Avvia il thread.
     * @param skt socket con cui instaurare la connessione con il client
     * @throws IOException se ci sono errori di input/output
     */
    public ServerOneClient(Socket skt) throws IOException {
        this.socket = skt;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        start();
    }

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client in
     * modo da rispondere alle richieste del client
     */
    public void run() {

        try {

            KNN knn = null;
            do {

                int loadPick = (Integer) in.readObject();

                switch (loadPick) {

                    case 1 : {
                        Data trainingSet = null;
                        String file = "";

                        try {
                            file = (String) in.readObject();

                            trainingSet = new Data(file);
                            System.out.println(trainingSet);

                            out.writeObject("@OK");
                        } catch (TrainingDataException exc) {
                            System.out.println("[ServerOneClient_run_TrainingDataException] : " + exc.getMessage());
                            out.writeObject("@ERROR");
                        }

                        knn = new KNN(trainingSet);

                        try {
                            knn.salva(file + ".dmp");
                        } catch (IOException exc) {
                            System.out.println(exc.getMessage());
                        }
                    } break;

                    case 2: {
                        String file;

                        try {
                            file = (String) in.readObject();
                            knn = KNN.carica(file);

                            out.writeObject("@OK");
                        } catch (IOException | ClassNotFoundException exc) {
                            System.out.println("[ServerOneClient_run_IOException | ClassNotFoundException]" + exc.getMessage());
                            out.writeObject("@ERROR");
                        }

                    } break;

                    case 3: {
                        Data trainingSet = null;
                        String table = "";
                        DbAccess db = null;

                        try {
                            System.out.print("Connecting to DB...");
                            db = new DbAccess();
                            System.out.println("done!");

                            table = (String) in.readObject();

                            trainingSet = new Data(db, table);

                            out.writeObject("@OK");
                        } catch (InsufficientColumnNumberException exc1) {
                            System.out.println("[ServerOneClient_run_InsufficientColumnNumberException]: " + exc1.getMessage());
                            out.writeObject("@ERROR");
                        } catch (DatabaseConnectionException exc2) {
                            System.out.println("[ServerOneClient_run_DatabaseConnectionException]: " + exc2.getMessage());
                            out.writeObject("@ERROR");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            if (db != null) {
                                System.out.print("chiusura connessione database...");
                                db.closeConnection();
                                System.out.println("done");
                            }
                        }

                        knn = new KNN(trainingSet);
                        try {
                            knn.salva(table + "DB.dmp");
                        } catch (IOException exc) {
                            System.out.println("[ServerOneClient_run_IOException]: " + exc.getMessage());
                        }


                    } break;

                    case 4: {
                        out.writeObject(knn.predict(out, in));
                    } break;
                }

            } while (true);


        } catch (IOException e) {
            System.out.println("[ServerOneClient_run_IOException] : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[ServerOneClient_run_ClassNotFoundException] : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("[ServerOneClient_run_IOException] Socket not closed : " + e.getMessage());
            }
        }
    }
}