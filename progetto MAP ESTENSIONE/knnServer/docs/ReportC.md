# Relazione Tecnica
## INDICE
- ### [Introduzione](#1-introduzione) 
- ### [Requisiti specifici](#2-requisiti-specifici) 
  - Requisiti funzionali
  - Requisiti non funzionali
- ### [Diagramma delle classi](#3-diagramma-delle-classi) 
- ### [Riepilogo del test](#4-riepilogo-del-test)
- ### [Manuale Utente VERSIONE ESTESA](#5-manuale-utente-versione-estesa)
- Server
- Client
- ### [Conclusioni](#6-conclusioni)
  - Analisi Retrospettiva

<br/><br/>

# 1. Introduzione
Nella seguente documentazione verrà descritta la ***relazione tecnica*** del caso di studio di **Metodi Avanzati di Programmazione Anno Accademico 2021/2022**. <br/><br/>
Ci presentiamo, il gruppo é composto da: <br/>

<br>

***<h2 style="color: #2e6c80;">Team:</h2>***
<ol style="list-style: none; font-size: 14px; line-height: 32px; font-weight: bold;">
<li style="clear: both;">&nbsp;&nbsp;&nbsp;Cristallo Bernardino (b.cristallo3@studenti.uniba.it)<br/>&nbsp;&nbsp;&nbsp;24 anni, Altamura(BA)</li>
</li><br/><li style="clear: both;">&nbsp;&nbsp;&nbsp;Castriotta Antonio (a.castriotta5@studenti.uniba.it)<br/>&nbsp;&nbsp;&nbsp;23 anni, Foggia(FG)</li><br/>
<li style="clear: both;">&nbsp;&nbsp;&nbsp;Giuliani William (w.giuliani@studenti.uniba.it)<br/>&nbsp;&nbsp;&nbsp;21 anni, Brindisi(BR)</li>
</li><br/></ol>

<br/>

Il **progetto** svolto consiste nella *progettazione* e *realizzazione* di un sistema ***Client-Server*** (denominato **Knn**), ove il ***Server*** include funzionalitá di *data mining*, mentre il ***Client*** consente di usufruire del servizio offerto dal Server.

<br/>

# 2. Requisiti Specifici
## Requisiti funzionali:
Il progetto è stato realizzato in maniera iterativa con diversi "*Sprint*" denominati "**Lab #num**", in cui si andava a sviluppare il software in tutte le sue parti e funzionalità, ponendosi degli obiettivi per ogni iterazione, modificando e aggiungendo nuove classi e cambiandone anche la struttura. Nel seguito si riportano i vari momenti di sviluppo del caso di studio, precisamente sono stati 6.


## Requisiti non funzionali:

Il funzionamento del software richiede:

- Versione **Java SDK 11\OpenJDK 11** o superiore
- Versione **MYSQL 8.0** o superiore installato con servizi MYSQL attivi
- Creare database tramite il file di testo "```provac.script.sql.txt```" (fornito dal docente) nel caso in cui non sia già importato.
- Consigliato l'utilizzo su **Windows**.


Per visualizzare il *JavaDoc* di entrambe le versioni è possibile ritrovarlo nelle corrispondenti cartelle ***"docs"*** dei due progetti.

<br/>

# 3. Diagramma delle classi

![server](/docs/uml/server.png "server")
 ![Data](/docs/uml/Data.png "Data")
 ![Database](/docs/uml/Database.png "Database")
 ![mining](/docs/uml/mining.png "mining")
 ![utility](/docs/uml/utility.png "utility")
 
 Il diagramma delle classi del server è molto semplice in quanto esso è una Single-View Application.
 Per completezza è allegato l'uml della logica utilizzata nel progetto base.
 

<br/>

## 4. Riepilogo del test 

Se si inserisce un nome di una tabella non valido, cioè vuoto o inesistente, il sistema visualizza un alert

![Tabella Inesistente](/docs/screenshots/testcases/tabella_inesistente.png "Tabella Inesistente")

Se, nella fase di previsione, si tenta di andare avanti senza aver inserito i valori, o averli inseriti in maniera non corretta il sistema visualizza un alert

![No option selected](/docs/screenshots/testcases/no_option.png "No option selected")

Se, nella schermata delle impostazioni, si inserisce un numero di porta non numerico, il sistema visualizza un alert

![PortNumber Error](/docs/screenshots/testcases/portnumber_error.png "PortNumber Error")


# 5. Manuale Utente VERSIONE ESTESA

### **Sostituzione percorsi**
Innanzitutto per far funzionare bene sia il Server che il Client, sostituire nel file settings.json(se presente), presente nella cartella '.vscode'(se presente), i propri percorsi delle librerie del jdk e di JavaFX rispettivamente richiamati dalla riga 6 alla riga 14.
Altrimenti aggiungerli dalla voce 'Referenced Libraries' presente in 'Java Project'.

### Server

Per far funzionare il server, è sufficiente avviare l'applicazione knnServer.jar, o in alternativa runnare il file App.java.
La schermata dell'applicazione visualizzerà un form dove è possibile specificare il numero di porta che il Server utilizzerà per accettare nuove connessioni. Non inserendo alcuna porta il Server verrà avviato sulla porta di default, 2050.


![Server Home](/docs/screenshots/userguide/Server.png "Server Home")


![Server Avviato](/docs/screenshots/userguide/Avviato.png "Server Avviato")

## **Utilizzo di Database**
Nella cartella del progetto e nella sottocartella 'database' è presente un file denominato DbAccess.java,
sostituire in riga 19 e 20 l'ID e la PASSWORD con le proprie credeziali di mysql.

### Client

Se l'applicazione viene avviata per la prima volta, verrà visualizzata una schermata di benvenuto.

![Client Welcome](/docs/screenshots/userguide/Welcome.png "Client Welcome")

Premendo il pulsante, viene visualizzata la Home, dove è possibile scegliere la fonte da cui attingere ai dati (File, File binario,Database) e inserire il nome della tabella che contiene il dataset che si vuole utilizzare.

![Client Home](/docs/screenshots/userguide/Home.png "Client Home")


A questo punto, dopo aver premuto il pulsante "Learn", inizia la fase di previsione.
L'utente può scegliere i valori da utilizzare per la previsione, il classvalue è calcolato per la regola specificata attraverso la scelta delle singole opzioni.

![Pred Phase](/docs/screenshots/userguide/PredPhase.png "Pred Phase")

Terminata la fase di previsione, viene visualizzato il classvalue. I due bottoni presenti permettono di:
- eseguire una nuova previsione basata sullo stesso dataset
- scegliere un altro dataset per effettuare una previsione

![Classvalue](/docs/screenshots/userguide/ClassValue.png "Classvalue")


Dal menu File è possibile eseguire diverse azioni:
- Aprire una nuova finestra
- Aprire una tabella utilizzata di recente
- Chiudere il programma

![Menu File](/docs/screenshots/userguide/MenuBar.png "Menu File")


Dal menu Help, è possibile:
- Consultare la guida utente
- Aprire le Impostazioni

Le impostazioni permettono di scegliere se visualizzare la schermata di benvenuto all'avvio e di specificare valori diversi da quelli di default per la connessione al server.

![Preferences](/docs/screenshots/userguide/Settings.png "Preferences")

# 6. Conclusioni


Il progetto originale è stato sviluppato in concomitanza delle lezioni di laboratorio, in modo tale da rimanere sempre al passo e chiedere eventuali informazioni nei vari ricevimenti. <br>
Non abbiamo riscontrato particolari problemi poichè le lezioni teoriche erano in parallelo con le richieste dei vari Knn.
<br>
Una volta finalizzato la versione base del progetto, ci siamo concentrati sul cercare una valida estensione e alla fine abbiamo deciso di adottare un'interfaccia grafica, nello specifico JavaFx.
<br>
Dopo esserci confrontati sull'utilizzo di quest'ultimo, abbiamo innanzitutto creato le varie "scene" funzionali per il programma originale attraverso l'utilizzo di SceneBuilder.
<br>
Successivamente abbiamo creato il codice con Visual Studio Code per poter adattare le scene create alle funzionalità vere e proprie del client-server originali.
<br> 
Infine abbiamo realizzato i diagrammi di classe/package, esportati i file ".jar" e generati i rispettivi JavaDoc di entrambe le versioni.

<br>

 ### **Analisi Retrospettiva**

- Siamo rimasti soddisfatti del risultato finale, essendo completo in tutte le sue richieste nei limiti delle nostre capacità.

- Siamo rimasti amareggiati del fatto che, se avessimo avuto più esperienza nel campo delle interfacce grafiche e di JavaFx, avremmo potuto realizzare qualcosa di ancor più avanzato.
