# **Progetto MDP AA 25-26 canale AL**

Rev: 1.2   
Ultimo aggiornamento 25/05/2026

**ChangeLog**  
Rev 1.2 25/05/2026

- rimozione riferimenti alla gestione del pareggio

**ChangeLog**  
Rev 1.1 17/05/2026

- rimozione riferimento a test funzionali e Junit  
- Inserimento sezione sulle librerie esterne  
- eliminazione bot fiducioso, bot corretto e bot sospettoso  
- specificato meglio il comportamento del bot rischioso e del bot distratto.  
- Eliminazione replay gruppo da 3  
- Chiarificato modalità simulazione batch e eliminati riferimenti alla modalità torneo.

# 

# **Progetto d’esame: implementazione del gioco UNO in Java**

## **1\. Obiettivo del progetto**

Il progetto consiste nella progettazione e implementazione in Java del gioco **UNO**.

Il programma deve consentire lo svolgimento di partite complete tramite interfaccia grafica, supportare giocatori umani e giocatori controllati dal computer e implementare la versione base del gioco, incluse la dichiarazione di UNO e il challenge del *Wild Draw Four*. Quando richiesta dalla dimensione del gruppo, il progetto deve inoltre prevedere una modalità di simulazione tra bot.

Il progetto deve essere sviluppato da zero e deve essere corredato da una relazione che descriva le scelte progettuali adottate.

Il progetto può essere svolto individualmente oppure in gruppo. Ogni gruppo può essere composto da un massimo di **3 studenti**. I requisiti minimi richiesti crescono in base al numero di componenti del gruppo, come descritto nella Sezione 6\.

## **2\. Versione base del gioco UNO**

In questa specifica, per **versione base del gioco UNO** si intende l’implementazione delle regole classiche descritte in questa sezione.

La versione base deve essere sempre disponibile, anche quando il progetto supporta regole alternative o varianti configurabili.

Il gioco deve supportare da **2 a 6 giocatori**. All’inizio di ogni round, ciascun giocatore riceve 7 carte. Le carte rimanenti formano il mazzo di pesca, mentre una carta viene posta scoperta per iniziare la pila degli scarti.

### **2.1 Composizione del mazzo**

Il mazzo classico di UNO è composto da **108 carte**.

Il mazzo deve contenere:

* **76 carte numeriche colorate**;  
* **24 carte azione colorate**;  
* **8 carte jolly**.

I colori delle carte colorate sono quattro: rosso, giallo, verde e blu.

Per ciascun colore devono essere presenti:

* una carta `0`;  
* due carte per ciascun numero da `1` a `9`;  
* due carte *Skip*;  
* due carte *Reverse*;  
* due carte *Draw Two*.

Le carte jolly sono:

* quattro carte *Wild*;  
* quattro carte *Wild Draw Four*.

Gli studenti devono implementare la composizione del mazzo in modo coerente con queste quantità. Eventuali estensioni o varianti devono essere documentate nella relazione e devono essere distinguibili dalla modalità base del gioco.

### **2.2 Carte numeriche**

Le carte numeriche hanno un colore e un valore compreso tra `0` e `9`.

Una carta numerica può essere giocata se ha lo stesso colore della carta in cima alla pila degli scarti oppure se ha lo stesso numero. Ad esempio, un `5 rosso` può essere giocato sopra una carta rossa oppure sopra un altro `5` di qualunque colore.

Le carte numeriche non producono effetti speciali sul turno successivo.

Ai fini del punteggio di fine round, ogni carta numerica vale il proprio valore. Ad esempio, una carta `7` vale 7 punti.

### **2.3 Carta Skip**

La carta *Skip* è una carta azione colorata.

Può essere giocata su una carta dello stesso colore oppure su un’altra carta *Skip*. Quando viene giocata, il giocatore successivo nel verso corrente viene saltato e perde il turno.

Ai fini del punteggio di fine round, ogni carta *Skip* vale **20 punti**.

### **2.4 Carta Reverse**

La carta *Reverse* è una carta azione colorata.

Può essere giocata su una carta dello stesso colore oppure su un’altra carta *Reverse*. Quando viene giocata, il verso di gioco viene invertito.

Nel caso di due soli giocatori, l’effetto della carta *Reverse* deve essere gestito in modo coerente con le regole del gioco. La relazione deve indicare chiaramente la scelta adottata.

Ai fini del punteggio di fine round, ogni carta *Reverse* vale **20 punti**.

### **2.5 Carta Draw Two**

La carta *Draw Two* è una carta azione colorata.

Può essere giocata su una carta dello stesso colore oppure su un’altra carta *Draw Two*. Quando viene giocata, il giocatore successivo deve pescare due carte e perde il turno.

Nella versione base del gioco non è previsto lo stacking dei *Draw Two*. Eventuali varianti che consentono lo stacking devono essere implementate solo come regole alternative configurabili.

Ai fini del punteggio di fine round, ogni carta *Draw Two* vale **20 punti**.

### **2.6 Carta Wild**

La carta *Wild* è una carta jolly.

Può essere giocata indipendentemente dal colore o dal simbolo della carta in cima alla pila degli scarti. Quando viene giocata, il giocatore che l’ha giocata sceglie il nuovo colore corrente.

La carta *Wild* non impone penalità al giocatore successivo.

Ai fini del punteggio di fine round, ogni carta *Wild* vale **50 punti**.

### **2.7 Carta Wild Draw Four**

La carta *Wild Draw Four* è una carta jolly con penalità.

Quando viene giocata, il giocatore che l’ha giocata sceglie il nuovo colore corrente. Il giocatore successivo deve pescare quattro carte e perde il turno, salvo che decida di effettuare un challenge.

La carta *Wild Draw Four* può essere giocata solo se il giocatore non possiede carte del colore corrente. Il progetto deve quindi gestire la possibilità di verificare se la giocata è legale.

Il challenge del *Wild Draw Four* è obbligatorio. Se il challenge ha successo, significa che il *Wild Draw Four* è stato giocato illegalmente: in questo caso, il giocatore che ha giocato la carta deve pescare quattro carte. Se il challenge fallisce, il giocatore che ha effettuato il challenge deve pescare sei carte, cioè le quattro carte previste dalla penalità originaria più due carte aggiuntive.

Nella versione base del gioco non è previsto lo stacking dei *Wild Draw Four*. Eventuali varianti che consentono lo stacking devono essere implementate solo come regole alternative configurabili.

Ai fini del punteggio di fine round, ogni carta *Wild Draw Four* vale **50 punti**.

### **2.8 Turno di gioco**

Durante il proprio turno, un giocatore può giocare una carta compatibile con la carta in cima alla pila degli scarti oppure pescare dal mazzo.

Una carta è giocabile se è compatibile per colore, numero o simbolo, oppure se è una carta jolly giocabile secondo le regole descritte sopra.

Se un giocatore pesca una carta giocabile, può giocarla immediatamente secondo le regole del gioco. Se non la gioca, il turno passa al giocatore successivo.

Il gioco deve gestire correttamente almeno i seguenti aspetti:

* distribuzione iniziale delle carte;  
* turnazione dei giocatori;  
* verso di gioco;  
* effetti delle carte azione;  
* scelta del colore dopo una carta *Wild* o *Wild Draw Four*;  
* pesca dal mazzo;  
* rimescolamento della pila degli scarti quando il mazzo di pesca termina;  
* fine del round quando un giocatore termina le carte;  
* calcolo del punteggio al termine di ogni round;  
* fine della partita quando viene soddisfatta la condizione di vittoria della modalità scelta.

### **2.9 Modalità di partita**

Il progetto può supportare due modalità di partita:

* **partita singola**;  
* **partita a punti**.

La modalità **partita singola** è parte della versione base ed è sempre richiesta. La modalità **partita a punti** è richiesta nei livelli indicati nella Sezione 6\.

Nella modalità **partita singola**, il gioco è composto da un solo round. Vince il primo giocatore che termina le carte nella propria mano. In questa modalità non è necessario accumulare punti su più round, ma il programma deve comunque poter calcolare e mostrare il punteggio ottenuto dal vincitore del round, secondo le regole di conteggio descritte nella Sezione 2.10.

Nella modalità **partita a punti**, il gioco è composto da più round. Al termine di ogni round, il vincitore del round ottiene un punteggio pari alla somma dei valori delle carte rimaste nelle mani degli altri giocatori. I punti ottenuti nei diversi round vengono accumulati. Vince la partita il primo giocatore che raggiunge o supera la soglia di vittoria configurata.

La soglia di vittoria predefinita per la partita a punti è **500 punti**, ma deve essere configurabile.

La modalità di partita deve essere selezionabile nella configurazione iniziale del gioco.

### **2.10 Punteggio e penalità**

Alla fine di ogni round, il punteggio del round viene calcolato sommando il valore delle carte rimaste nelle mani dei giocatori che non hanno terminato le carte. Tale punteggio viene assegnato al vincitore del round.

In modalità **partita singola**, il punteggio calcolato a fine round può essere mostrato come risultato della partita, ma il vincitore è semplicemente il giocatore che ha terminato per primo le proprie carte.

In modalità **partita a punti**, il punteggio calcolato a fine round viene aggiunto al punteggio totale del vincitore del round. Se, dopo l’aggiornamento del punteggio, uno o più giocatori hanno raggiunto o superato la soglia di vittoria, la partita termina. Vince il giocatore con il punteggio totale più alto tra quelli che hanno raggiunto o superato la soglia. 

Il calcolo del punteggio deve seguire le regole classiche:

* le carte numeriche valgono il proprio valore;  
* *Skip*, *Reverse* e *Draw Two* valgono **20 punti**;  
* *Wild* e *Wild Draw Four* valgono **50 punti**.

Durante la partita devono inoltre essere gestite le penalità associate alle carte e alle azioni dei giocatori:

* dopo un *Draw Two*, il giocatore successivo pesca 2 carte e perde il turno;  
* dopo un *Wild Draw Four*, il giocatore successivo pesca 4 carte e perde il turno, salvo challenge;  
* se il challenge del *Wild Draw Four* ha successo, pesca 4 carte il giocatore che ha giocato illegalmente il *Wild Draw Four*;  
* se il challenge del *Wild Draw Four* fallisce, il giocatore che ha effettuato il challenge pesca 6 carte;  
* se un giocatore non dichiara UNO quando richiesto e la mancata dichiarazione viene contestata correttamente, quel giocatore pesca 2 carte.

### **2.11 Dichiarazione di UNO**

Il gioco deve gestire la dichiarazione di UNO.

Quando un giocatore gioca una carta e rimane con una sola carta in mano, deve dichiarare UNO. Se non lo fa, gli altri giocatori devono poter contestare la mancata dichiarazione secondo una modalità coerente con l’interfaccia realizzata.

In caso di contestazione valida, il giocatore che non ha dichiarato UNO deve ricevere la penalità prevista dalle regole del gioco.

La relazione deve descrivere chiaramente come è stata modellata e implementata la dichiarazione di UNO, soprattutto nel caso di partite con giocatori umani, bot e modalità simulazione.

### **2.12 Challenge del Wild Draw Four**

Il challenge del *Wild Draw Four* è obbligatorio.

Il gioco deve verificare la legalità della giocata di una carta *Wild Draw Four*. Tale carta può essere giocata solo se il giocatore non possiede carte del colore corrente.

Quando un giocatore subisce un *Wild Draw Four*, deve avere la possibilità di accettare la penalità oppure effettuare un challenge.

Il sistema deve gestire correttamente almeno i seguenti casi:

* challenge riuscito;  
* challenge fallito;  
* penalità applicata al giocatore che ha giocato illegalmente il *Wild Draw Four*;  
* penalità applicata al giocatore che ha effettuato un challenge errato.

La relazione deve descrivere come viene conservata l’informazione necessaria per valutare la legalità del *Wild Draw Four* e come viene gestito il challenge nei diversi tipi di partita.

## **3\. Interfaccia grafica**

Il progetto deve includere un’interfaccia grafica realizzata in **Swing** oppure in **JavaFX**.

L’interfaccia grafica deve consentire almeno di:

* configurare una nuova partita;  
* scegliere la modalità di partita, singola o a punti;  
* scegliere il numero di giocatori;  
* indicare il nome dei giocatori;  
* scegliere se ciascun giocatore è umano oppure controllato dal computer;  
* configurare la soglia di vittoria, nel caso della partita a punti;  
* visualizzare lo stato corrente della partita;  
* visualizzare la carta in cima alla pila degli scarti;  
* visualizzare il colore corrente;  
* visualizzare il verso di gioco;  
* visualizzare la mano del giocatore corrente, quando appropriato;  
* visualizzare il numero di carte possedute dagli altri giocatori;  
* permettere al giocatore corrente di compiere una mossa valida;  
* mostrare messaggi relativi a errori, mosse illegali, effetti delle carte, dichiarazione di UNO, challenge e fine round;  
* visualizzare i punteggi;  
* mostrare il vincitore della partita.

L’aspetto grafico non è l’elemento principale della valutazione. Saranno tuttavia valutate la chiarezza dell’interazione, la completezza delle informazioni mostrate, la gestione degli errori e l’usabilità complessiva.

## **4\. Regole alternative e profili dei bot**

### **4.1 Regole alternative**

Oltre alla versione base, il progetto può prevedere il supporto a regole alternative o varianti configurabili.

Le regole alternative non devono sostituire la versione base, che resta obbligatoria. Ogni regola alternativa implementata deve essere attivabile e disattivabile dalla configurazione del gioco. Le regole alternative implementabili sono esclusivamente quelle elencate in questa sezione. La relazione deve indicare chiaramente quali regole alternative sono state implementate e come sono state integrate nel progetto.

Le regole alternative implementabili sono:

* **stacking delle carte pesca**: quando un giocatore subisce una penalità da una carta *Draw Two* o *Wild Draw Four*, può giocare a sua volta una carta *Draw Two* o *Wild Draw Four*, accumulando la penalità per il giocatore successivo;  
* **number rush**: durante il proprio turno, un giocatore può scartare più carte numeriche con lo stesso valore della carta in cima alla pila degli scarti, anche se di colori diversi. La carta visibile in cima alla pila degli scarti diventa l’ultima carta giocata;  
* **seven-zero rule**: quando viene giocato uno 0, tutti i giocatori passano la propria mano al giocatore successivo nel verso di gioco; quando viene giocato un 7, il giocatore sceglie un altro giocatore con cui scambiare la mano;  
* **custom scoring**: variante del calcolo del punteggio, purché documentata e configurabile.

La progettazione deve rendere chiaro come le varianti interagiscono con la versione base. In presenza di più regole alternative attive contemporaneamente, il comportamento del gioco deve essere definito in modo non ambiguo.

### **4.2 Profili dei bot**

Il progetto può prevedere bot con strategie e profili comportamentali differenti. Le strategie dei bot non devono limitarsi alla scelta casuale di una carta, soprattutto nei gruppi più numerosi.

Esempi di strategie o profili dei bot sono:

* **bot casuale**: sceglie casualmente tra le mosse legali disponibili;  
* **bot greedy**: cerca di liberarsi rapidamente delle carte, giocando ad esempio carte azione o carte di valore alto quando possibile;  
* **bot conservativo**: tende a conservare carte speciali e jolly per le fasi finali del round;  
* **bot aggressivo**: privilegia carte che penalizzano gli altri giocatori, come *Skip*, *Draw Two* e *Wild Draw Four*;  
* **bot difensivo**: cerca di ridurre il rischio di rimanere senza mosse nei turni successivi;  
* **bot orientato al punteggio**: tiene conto del valore delle carte rimaste in mano e cerca di ridurre il punteggio potenzialmente ceduto agli avversari;  
* **bot opportunista**: effettua challenge del *Wild Draw Four* solo quando ha indizi favorevoli;  
* **bot rischioso**: può tentare di giocare un *Wild Draw Four* illegalmente con una probabilità P, accettando il rischio di challenge;  
* **bot distratto**: può dimenticare con una certa probabilità P di dichiarare UNO.

È possibile implementare anche bot custom, definiti autonomamente dagli studenti, purché il loro comportamento sia descritto e motivato nella relazione. È inoltre possibile definire bot ottenuti come combinazione di più profili, ad esempio un bot greedy e aggressivo, oppure un bot conservativo ma sospettoso nei challenge. In questo caso, la relazione deve chiarire come vengono combinate le diverse strategie e quale criterio viene usato per risolvere eventuali conflitti tra comportamenti.

I profili comportamentali devono essere implementati in modo coerente e documentati nella relazione. Nel caso di bot che possono compiere azioni rischiose o scorrette, il comportamento deve rimanere all’interno delle possibilità previste dal gioco e deve essere gestito correttamente dal sistema, ad esempio tramite contestazioni, challenge e penalità.

## **5\. Modalità simulazione**

Quando richiesta dalla dimensione del gruppo, il progetto deve prevedere una modalità di simulazione tra soli bot.

La modalità simulazione deve permettere di eseguire partite senza intervento umano. Deve essere possibile configurare almeno:

* modalità di partita, singola o a punti;  
* numero di giocatori;  
* strategie dei bot, se sono disponibili più strategie;  
* soglia di vittoria, nel caso della partita a punti;  
* numero di simulazioni, round o partite da eseguire, secondo le funzionalità richieste per la dimensione del gruppo.

Al termine della simulazione, il programma deve mostrare statistiche sintetiche. A seconda della dimensione del gruppo, le statistiche richieste possono includere:

* numero di vittorie per giocatore o strategia;  
* punteggio finale;  
* numero di round giocati;  
* numero medio di turni per round;  
* numero di carte pescate;  
* numero di challenge effettuati;  
* numero di challenge riusciti o falliti;  
* durata o andamento della simulazione.

## **6\. Requisiti e dimensione del gruppo**

I requisiti minimi del progetto dipendono dal numero di componenti del gruppo.

La distinzione tra progetto individuale, gruppo da 2 studenti e gruppo da 3 studenti non riguarda semplicemente il livello di cura atteso, ma soprattutto il numero di funzionalità che devono essere implementate. I gruppi più numerosi devono quindi realizzare un insieme più ampio di feature obbligatorie.

Tutti i progetti, indipendentemente dal numero di componenti, devono includere una versione funzionante del gioco tramite interfaccia grafica.

### **6.1 Progetto individuale**

Un progetto individuale deve implementare la versione base del gioco UNO, come definita nella Sezione 2\.

Per i progetti individuali, il punteggio massimo ottenibile dipende dalle funzionalità implementate. I livelli sono incrementali: per accedere a un livello superiore, il progetto deve includere anche le funzionalità richieste dai livelli precedenti.

#### **Fino a 24 punti**

Il progetto può ottenere fino a 24 punti se implementa almeno le seguenti funzionalità:

* implementazione completa della versione base del gioco;  
* supporto a partite con soli giocatori umani da 2 a 6 giocatori;  
* modalità partita singola;  
* interfaccia grafica   
* configuratore iniziale della partita.

#### **Fino a 28 punti**

Il progetto può ottenere fino a 28 punti se, oltre alle funzionalità richieste per il livello precedente, implementa anche:

* modalità partita a punti;  
* soglia di vittoria configurabile per la partita a punti;  
* supporto a partite miste, cioè con almeno un giocatore umano e uno o più bot;  
* bot casuale.

#### **Fino a 30 punti**

Il progetto può ottenere fino a 30 punti se, oltre alle funzionalità richieste per i livelli precedenti, implementa anche:

* almeno un bot avanzato.

Per **bot avanzato** si intende un bot che non sceglie semplicemente una mossa casuale tra quelle disponibili, ma adotta una strategia riconoscibile e documentata nella relazione. Ad esempio, il bot può cercare di ridurre il valore delle carte rimaste in mano, privilegiare carte azione, conservare le carte jolly, scegliere il colore più vantaggioso o decidere se effettuare challenge in base a una politica definita.

### **6.2 Gruppo da 2 studenti**

Un gruppo da 2 studenti deve implementare tutte le funzionalità richieste per il progetto individuale fino al livello da 30 punti.

Per i gruppi da 2 studenti, il punteggio massimo ottenibile dipende dalle funzionalità aggiuntive implementate. I livelli sono incrementali: per accedere a un livello superiore, il progetto deve includere anche le funzionalità richieste dai livelli precedenti.

A differenza del progetto individuale, un gruppo da 2 studenti deve supportare anche partite con soli bot (simulazione tra bot).

#### **Fino a 24 punti**

Il progetto può ottenere fino a 24 punti se implementa almeno tutte le funzionalità richieste per il progetto individuale fino a 30 punti e, in aggiunta:

* modalità simulazione partita singola  
* storico delle mosse della partita;  
* salvataggio dello stato corrente di una partita;  
* ripristino di una partita salvata.

Lo storico deve registrare almeno:

* giocatore che ha effettuato la mossa;  
* carta giocata o azione compiuta;  
* eventuale effetto della carta;  
* eventuale challenge;  
* eventuale dichiarazione o mancata dichiarazione di UNO.

Lo storico deve essere visualizzabile tramite GUI e consultabile al termine della partita.

Il salvataggio deve includere almeno le informazioni necessarie per ricostruire correttamente lo stato del gioco, tra cui:

* modalità di partita;  
* giocatori e relativi tipi, umani o bot;  
* profili dei bot;  
* punteggi correnti;  
* carte in mano ai giocatori;  
* mazzo di pesca;  
* pila degli scarti;  
* carta o colore corrente;  
* verso di gioco;  
* giocatore di turno;  
* eventuali regole alternative attive.

Dopo il caricamento, la partita deve poter continuare in modo coerente dallo stato salvato.

#### **Fino a 28 punti**

Il progetto può ottenere fino a 28 punti se, oltre alle funzionalità richieste per il livello precedente, implementa anche:

* la regola alternativa **stacking delle carte pesca**, selezionabile nella configurazione della partita;  
* la regola alternativa **number rush**, selezionabile nella configurazione della partita.

Le regole alternative richieste non devono sostituire la versione base del gioco, che deve sempre restare disponibile.

#### **Fino a 30 punti**

Il progetto può ottenere fino a 30 punti se, oltre alle funzionalità richieste per i livelli precedenti, implementa anche:

* almeno tre profili di bot selezionabili nella configurazione della partita;

I profili devono produrre comportamenti distinguibili. Possono essere scelti tra quelli descritti nella Sezione 4, possono essere bot custom definiti dagli studenti, oppure possono essere combinazioni di più profili.

Al termine della simulazione, il programma deve mostrare statistiche che permettano di confrontare i risultati dei profili utilizzati, ad esempio vittorie, punteggi medi, numero di carte pescate, numero di challenge effettuati e numero di challenge riusciti.

### **6.3 Gruppo da 3 studenti**

Un gruppo da 3 studenti deve implementare tutte le funzionalità richieste per il gruppo da 2 studenti fino al livello da 30 punti.

Per i gruppi da 3 studenti, il punteggio massimo ottenibile dipende dalle funzionalità aggiuntive implementate. I livelli sono incrementali: per accedere a un livello superiore, il progetto deve includere anche le funzionalità richieste dai livelli precedenti.

#### **Fino a 24 punti**

Il progetto può ottenere fino a 24 punti se implementa almeno tutte le funzionalità richieste per il gruppo da 2 studenti fino a 30 punti e, in aggiunta:

* tutte le regole alternative elencate nella Sezione 4;  
* configurazione avanzata della partita;  
* modalità privacy per le partite con più giocatori umani sullo stesso computer.

Le regole alternative devono essere configurabili singolarmente: deve essere possibile attivare o disattivare ciascuna regola alternativa dalla configurazione del gioco, mantenendo sempre disponibile la versione base del gioco.

La configurazione avanzata deve permettere di selezionare almeno:

* modalità di partita;  
* soglia di vittoria;  
* numero di giocatori;  
* tipo di ciascun giocatore;  
* profilo di ciascun bot;  
* regole alternative attive.

La modalità privacy deve evitare che un giocatore possa vedere la mano degli altri giocatori umani. Prima del turno di ciascun giocatore umano, deve essere prevista una fase di passaggio turno, in cui la mano del giocatore successivo non è ancora visibile e il sistema chiede conferma prima di mostrarla.

Durante il turno di un giocatore umano, devono essere visibili solo le informazioni pubbliche della partita e le informazioni private del giocatore corrente. Le informazioni pubbliche includono, ad esempio, la carta in cima alla pila degli scarti, il colore corrente, il verso di gioco, il numero di carte degli altri giocatori, i punteggi e i messaggi pubblici relativi alle ultime azioni.

La modalità privacy deve essere attivabile o disattivabile nella configurazione della partita. Nel caso di partite con un solo giocatore umano e uno o più bot, la modalità privacy può non avere effetti visibili.

#### **Fino a 28 punti**

Il progetto può ottenere fino a 28 punti se, oltre alle funzionalità richieste per il livello precedente, implementa anche:

* simulazione partita a punti e simulazione batch. Una simulazione batch è l'esecuzione automatica più partite singole consecutive. Il numero di partite da eseguire in una simulazione batch deve poter essere modificato.  
* statistiche aggregate sui risultati della simulazione batch o della partita a punti;  
* esportazione dei risultati in formato `.json` oppure `.csv`.

Le statistiche devono includere almeno:

* numero di partite vinte da ciascun giocatore o profilo di bot;  
* punteggio medio;  
* numero medio di round per partita;  
* numero medio di turni per round;  
* numero totale o medio di challenge;  
* numero totale o medio di penalità applicate.

Il file esportato deve contenere dati sufficienti per analizzare e confrontare le prestazioni dei diversi bot o delle diverse configurazioni di gioco.

#### **Fino a 30 punti**

Il progetto può ottenere fino a 30 punti se, oltre alle funzionalità richieste per i livelli precedenti, implementa anche:

* salvataggio avanzato con gestione di più salvataggi;  
* visualizzazione post-partita.

Il salvataggio avanzato deve permettere almeno di:

* creare più file di salvataggio;  
* scegliere quale partita salvata caricare;  
* mostrare alcune informazioni descrittive sui salvataggi disponibili, ad esempio data del salvataggio, modalità di partita, numero di giocatori e giocatore di turno;  
* gestire in modo robusto salvataggi non validi, mancanti o incompatibili.

La visualizzazione post-partita può essere realizzata, ad esempio, come:

* riepilogo dettagliato della partita;  
* visualizzazione dello storico completo delle mosse;  
* schermata finale con statistiche dettagliate;  
* confronto grafico o tabellare tra giocatori o bot.

### **6.4 Osservazioni generali**

Le funzionalità aggiuntive richieste ai gruppi più numerosi devono essere integrate nel progetto in modo coerente. Non è sufficiente aggiungere funzioni isolate o scollegate dal resto del sistema.

Le regole alternative devono essere configurabili: deve essere sempre possibile attivare o disattivare ciascuna regola alternativa dalla configurazione del gioco e deve sempre essere possibile giocare secondo la versione base di UNO.

I profili dei bot devono essere documentati nella relazione, indicando quali criteri usano per scegliere le mosse e in che modo differiscono tra loro. Nel caso di bot custom o di bot ottenuti combinando più profili, la relazione deve spiegare il comportamento risultante e le scelte progettuali adottate.

La funzionalità di salvataggio e ripristino deve essere documentata nella relazione, indicando quali informazioni vengono salvate, quale formato è stato scelto e come viene garantita la coerenza dello stato dopo il caricamento.

Le funzionalità implementate devono essere verificabili durante l’esecuzione del programma.

## **8\. Relazione**

Il progetto deve essere corredato da una relazione in formato PDF.

La relazione deve descrivere il progetto realizzato e motivare le principali scelte implementative e progettuali.

La relazione deve includere almeno:

* descrizione generale del progetto;  
* modalità di partita implementate e gestione del punteggio;  
* eventuali semplificazioni o scelte interpretative;  
* architettura adottata;  
* diagramma UML delle classi principali;  
* descrizione delle responsabilità delle classi principali;  
* spiegazione dell’uso di interfacce, classi astratte, eccezioni custom, generics, iteratori, lambda o comparatori;  
* descrizione della GUI con screenshot;  
* descrizione della modalità simulazione, se richiesta dalla dimensione del gruppo;  
* descrizione delle eventuali regole alternative implementate;  
* descrizione delle strategie e dei profili comportamentali dei bot;  
* descrizione della funzionalità di salvataggio e ripristino, se richiesta dalla dimensione del gruppo;  
* istruzioni per compilare ed eseguire il progetto;  
* limiti noti del progetto;  
* per i gruppi, contributo dei singoli componenti.

Non è previsto un limite massimo di pagine. Tuttavia, la relazione deve essere chiara, organizzata e non deve includere porzioni inutilmente lunghe di codice sorgente.

## **9\. Utilizzo di librerie**

Per il progetto è possibile utilizzare le seguenti librerie:

Creazione e lettura di JSON:

* GSON  
* Jackson

Non è permesso l'utilizzo di librerie aggiuntive non esplicitamente elencate.

Qualora si ravvisi l'estrema necessità di utilizzare una libreria esterna non inclusa, è necessario effettuare una richiesta motivando tale necessità, specificando in che modo la libreria individuata risolva il problema. La richiesta sarà soggetta a valutazione per l'eventuale approvazione.

## **10\. Modalità di consegna**

La consegna prevede un archivio `.zip` contenente:

* progetto Java importabile in Eclipse;  
* codice sorgente;  
* eventuali risorse necessarie alla GUI;  
* JavaDOC  
* relazione in formato PDF.

Il progetto deve essere eseguibile senza modifiche manuali non documentate.

Nel README devono essere indicate chiaramente:

* versione di Java utilizzata;  
* eventuale uso di Swing o JavaFX;  
* eventuali librerie o configurazioni necessarie;  
* classe principale da eseguire;  
* istruzioni per avviare una partita normale;  
* istruzioni per avviare la modalità simulazione, se richiesta dalla dimensione del gruppo;  
* istruzioni per salvare e ripristinare una partita, se richiesto dalla dimensione del gruppo;  
* eventuali limitazioni note.

