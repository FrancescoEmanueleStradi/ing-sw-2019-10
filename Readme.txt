Prova Finale di Ingegneria del Software - A.A. 2018/2019

Scaglione Professor Cugola Gianpaolo
Gruppo 10 - Stradi Francesco Emanuele, Stanimirov Alexander Elvis, Savoia Diego

Requisiti implementati:
- Regole Complete
- CLI
- GUI (attualmente in sviluppo)
- Socket
- RMI
- Funzionalità Aggiuntiva: Partite Multiple

Generazione dei .jar a partire da maven assembly:
Nel pom vi è il plugin maven assembly che genera entrambi i .jar.
Prima di eseguire il build, alla configurazione va aggiunto il Maven Goal "clean package".
I .jar di default si troveranno in target, ma vanno spostati nel root della directory.

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
	