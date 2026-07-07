<h1 align="center"> UNO - Progetto MDP 2025/26 </h1>

Implementazione del gioco di carte UNO in Java con interfaccia grafica JavaFX.
Progetto individuale per il corso di Metodologie di Programmazione, Università La Sapienza Roma.

**Autore:** Osema Fadhel

![gameview](https://github.com/OsemaFadhel/Progetto-java-uni-o/blob/main/docs/screenshots/gameview.png)


## Requisiti

| Requisito | Versione |
|-----------|---------|
| Java | 21 o superiore |
| Maven | 3.9.9 |

## Classe principale

``` bash
it.uniroma1.mdp.uno.Main
```

> Non è possibile eseguire direttamente Main.java con Run As -> Java Application. Seguire la guida descritta sotto.


## Esecuzione

### Da terminale

Dalla cartella radice del progetto:

```bash
mvn javafx:run
```

### Da Eclipse

1. `File -> Import -> Maven -> Existing Maven Projects`: selezionare la cartella del progetto
2. `Run -> Run Configurations`
3. In Goals inserire `javafx:run`
4. Cliccare `Run`


## Avvio di una partita normale

1. Avviare l'applicazione
2. Selezionare la modalità di partita: Partita Singola o Partita a Punti
3. Per la modalità a punti, impostare la soglia di vittoria tramite lo slider (default: 500)
4. Aggiungere da 2 a 6 giocatori specificando nome e tipo (Umano / Bot Casuale / Bot Greedy)
5. Cliccare Inizia Partita


## Limitazioni note

- La carta Reverse con 2 giocatori non produce effetto sull'ordine dei turni.
- La prima carta del mazzo degli scarti non applica il proprio effetto se è una carta azione.
- Le carte del giocatore umano vengono nascoste per 3 secondi quando il giocatore passa il turno.
