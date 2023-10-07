# Relazione Tecnica
## INDICE
- ### [Introduzione](#1-introduzione) 
- ### [Requisiti specifici](#2-requisiti-specifici) 
  - Requisiti funzionali
  - Requisiti non funzionali
- ### [Diagramma delle classi](#3-diagramma-delle-classi) 
- ### [Riepilogo del test](#4-riepilogo-del-test)
- ### [Manuale Utente VERSIONE BASE](#5-manuale-utente-versione-base)
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
<br/><li style="clear: both;">&nbsp;&nbsp;&nbsp;Castriotta Antonio (a.castriotta5@studenti.uniba.it)<br/>&nbsp;&nbsp;&nbsp;23 anni, Foggia(FG)</li><br/>
<li style="clear: both;">&nbsp;&nbsp;&nbsp;Giuliani William (w.giuliani@studenti.uniba.it)<br/>&nbsp;&nbsp;&nbsp;21 anni, Brindisi(BR)</li>
<br/></ol>

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

 ![client](/docs/uml/Client.png "client")
 ![utility](/docs/uml/utility.png "utility")

 Il package client contiene:
 - Client, una classe per gestire lo scambio di messaggi con il server durante le fasi di connessione, learning e prediction.


<br/>

## 4. Riepilogo del test 

Se si inserisce un nome di una tabella non valido, cioè vuoto o inesistente, il sistema visualizza un alert nel server

***N.B.*** Se si preme solo il tasto di invio senza aver digitato nulla non restituisce nessun errore, andrà solamenta a capo aspettando un valore da inserire. Abbiamo provato a gestire l'eccezione e a vedere come passasse l'invio in readObject, anche con l'uso dei breakpoint del debug, ma non sappiamo perchè il tasto 'invio' non riesce a leggerlo e quindi non riesce ad uscire dal ciclo dei delimiters presente nella funzione 'getNextInputToken' di Keyboard.java

![Tabella Inesistente](/docs/screenshots/testcases/tabella_inesistente.png "Tabella Inesistente")

Se, nella fase di previsione, si tenta di andare avanti avendo inserito solo uno spazio, o averli inseriti in maniera non corretta il sistema visualizza un alert

![No option selected](/docs/screenshots/testcases/no_option.png "No option selected")


# 5. Manuale Utente VERSIONE BASE



### Server

Per far funzionare il server, è sufficiente avviare l'applicazione server.jar da terminale oppure runnare il main da Multiserver.
La linea di comando specificherà la porta e l'address su cui il server è in ascolto (per cambiare questi bisognerà cambiare i valori direttamente dal file Client.java nel 'main' e nella classe 'Multiserver' del file Multiserver.java di server )


![Server Avviato](/docs/screenshots/userguide/Avviato.png "Server Avviato")

Nel momento dell'acquisizione della tabella inserita nel cliente verrà visualizzata nella parte del server insieme all' ExplanatorySet, il numberOfExample e il target 

![Dataset Caricato](/docs/screenshots/userguide/Caricato.png "Dataset Caricato")

## **Utilizzo di Database**
Nella cartella del progetto e nella sottocartella 'database' è presente un file denominato DbAccess.java,
sostituire in riga 19 e 20 l'ID e la PASSWORD con le proprie credeziali di mysql.

### Client

Per far funzionare il Client, è sufficiente avviare l'applicazione client.jar da terminale o eventualmente runnare il main presente in client.java.
Dopo aver effettuato la connessione, dalla parte del client verranno visualizzate le varie opzioni per l'acquisizione del dataset.
A questo punto, dopo aver premuto il pulsante "Invio" sulla tastiera, l'utente specificherà la tabella da cui prendere il dataset.

![Client Home](/docs/screenshots/userguide/Home.png "Client Home")

Successivamente l'utente può scegliere i valori da utilizzare per la previsione, il classvalue è calcolato per la regola specificata attraverso la scelta delle singole opzioni.

![Pred Phase](/docs/screenshots/userguide/PredPhase.png "Pred Phase")

Terminata la fase di previsione, viene visualizzato il classvalue. E infine verrà chiesto se si vuole predire un nuovo risultato o uscire dalla predizione e infine se uscire completamente dall'applicazione

![Classvalue](/docs/screenshots/userguide/ClassValue.png "Classvalue")


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
