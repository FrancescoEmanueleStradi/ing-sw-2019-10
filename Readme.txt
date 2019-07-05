Prova Finale di Ingegneria del Software - A.A. 2018/2019

Scaglione Professor Cugola Gianpaolo
Gruppo 10 - Stradi Francesco Emanuele, Stanimirov Alexander Elvis, Savoia Diego

Requisiti implementati:
- Regole Complete
- CLI
- GUI (purtroppo non siamo riusciti a completarla; abbiamo però lasciato il codice da noi scritto, sperando non sia un problema, per mostrare a che punto fossimo arrivati)
- Socket
- RMI
- Funzionalità Aggiuntiva: Partite Multiple

P.S. I bug presenti con Sonar sono relativi alla GUI.

Generazione dei file .jar a partire da Maven Assembly:
Nel file pom.xml è stato aggiunto il plugin "Maven Assembly", utile per generare entrambi i file .jar necessari.
Prima di eseguire il build, alla configurazione va aggiunto il Maven Goal "clean package".
I file .jar di default si troveranno nella cartella "target", ma per comodità vanno spostati nella root della directory.

Modalità di esecuzione:
Nella repository sono presenti due file .jar, uno per il Server e uno per il Client.
	Server:
	Da linea di comando, portarsi nella directory dove è presente il file Server.jar
	Digitare java -jar Server.jar e premere Invio
	Il server (Socket + RMI) si avvia, mostrando a schermo l'esito dell'avvio.
	Client*:
	Da linea di comando, portarsi nella directory dove è presente il file Client.jar
	Digitare java -jar Client.jar e premere Invio
	Il client si avvia, mostrando una schermata di benvenuto e richiedendo l'ip del Server.

	*La procedura descritta è da ripetersi per ogni Client che desidera connettersi. 
	
