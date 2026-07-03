E per Reverse/Skip/Draw Two come prima carta?
Questa è una questione diversa. Le regole ufficiali dicono che se la prima carta è un'azione, l'effetto si applica prima che chiunque giochi. La specifica però dice genericamente "effetti delle carte azione" senza specificare esplicitamente la prima carta.

È una semplificazione accettabile non applicare questi effetti sulla prima carta, a patto di documentarlo nella relazione nella sezione "eventuali semplificazioni o scelte interpretative". La specifica lo prevede esplicitamente come punto della relazione.

Per il tuo progetto universitario però:

Hai 2 modalità fisse, lo sai fin dall'inizio, non cambieranno. Il professore non chiede estensibilità sulle modalità — chiede che tu usi i pattern giusti dove ha senso (Strategy per i Bot, State Machine per il flusso). Usare una Strategy su GameMode con 2 case sarebbe over-engineering e potrebbe sembrare che hai messo un pattern a caso.

Risposta onesta: in produzione → Strategy/interfaccia. Per questo esame → enum è la scelta corretta e difendibile.


Reverse with 2 players: With 2 players, Reverse just flips isClockwise but the turn order with 2 players is identical regardless of direction — so Reverse has no effect. Spec says: "deve essere gestito in modo coerente e la relazione deve indicare la scelta adottata". You must document this explicitly in the report.

First card on discard pile: If the first revealed card is an action card (Skip/Reverse/Draw Two), its effect is NOT applied. This is a valid interpretation but should be mentioned.


FX_JARS=$(find ~/.m2/repository/org/openjfx -name "*.jar" | grep "23.0.2" | grep -v sources | grep -v javadoc | tr '\n' ':')

javadoc -d docs/JavaDOC -sourcepath src/main/java -subpackages it.uniroma1.mdp.uno \
  -classpath "$FX_JARS" -encoding UTF-8 -charset UTF-8 \
  -windowtitle "UNO Game" -author -private