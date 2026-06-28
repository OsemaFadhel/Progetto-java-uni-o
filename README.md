E per Reverse/Skip/Draw Two come prima carta?
Questa è una questione diversa. Le regole ufficiali dicono che se la prima carta è un'azione, l'effetto si applica prima che chiunque giochi. La specifica però dice genericamente "effetti delle carte azione" senza specificare esplicitamente la prima carta.

È una semplificazione accettabile non applicare questi effetti sulla prima carta, a patto di documentarlo nella relazione nella sezione "eventuali semplificazioni o scelte interpretative". La specifica lo prevede esplicitamente come punto della relazione.

Per il tuo progetto universitario però:

Hai 2 modalità fisse, lo sai fin dall'inizio, non cambieranno. Il professore non chiede estensibilità sulle modalità — chiede che tu usi i pattern giusti dove ha senso (Strategy per i Bot, State Machine per il flusso). Usare una Strategy su GameMode con 2 case sarebbe over-engineering e potrebbe sembrare che hai messo un pattern a caso.

Risposta onesta: in produzione → Strategy/interfaccia. Per questo esame → enum è la scelta corretta e difendibile.