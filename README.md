### Setup
- Starte `gradlew setupDecompWorkspace`
- Starte `gradlew idea`
- Starte `gradlew genIntellijRuns`
- Öffne das Projekt in IntelliJ IDEA

### Building
- Starte `gradlew build`

### Features
- unendlich, konfigurierbare Hologramme
- unendlich, einstellbare Hauptgewinne/Jacpots
- Discord Webhook/Nachrichten
- Einstellbare Nachrichten mit Platzhaltern

### Platzhalter
- Hologramme
  - Jackpot
    - {price} = Wert des HGWs
    - {amount} = Anzahl der bisheringen Ankäufe
    - {player} = Name des letzten Spielers
  - Umsatz
    - {wager} = Umsatz des Spielers
    - {position} = Position im Ranking
    - {player} = Name des Spielers
- Nachrichten
  - Ingame & Discord
    - {price} = Wert des HGWs
    - {amount} = Anzahl der bisheringen Ankäufe
    - {player} = Name des letzten Spielers
    - {purchase_amount} = Anzahl im letzten Ankauf
    - {total} = Insgesamter Wert der HGWs

### Ingame-Commands
- /ggstats - Öffnet die Ingame-Command Übersicht

### Mitwirken

### 1. Fehler melden (Bug Reports)
Wenn du einen Fehler findest, öffne bitte ein [Issue](https://github.com/BemmeUtils/HoloStats/issues). Nutze die Vorlage und beschreibe:
* Was hast du getan?
* Was hast du als Ergebnis erwartet?
* Was ist stattdessen passiert? (Gerne mit Screenshots oder Fehlermeldungen)

### 2. Features vorschlagen
Du hast eine Idee für eine neue Funktion?
Bitte öffne ein Issue und markiere es mit dem Label `enhancement`.
Ein Entwickler, oder du selber können, dieses dann umsetzen.

### 3. Code-Änderungen einreichen (Pull Requests)
Wenn du direkt am Code arbeiten möchtest, folge bitte diesem Workflow:

1. Öffne bitte ein [Issue](https://github.com/BemmeUtils/HoloStats/issues)
2. **Forke** das Repository in deinen eigenen GitHub-Account.
3. Erstelle einen neuen **Branch** für deine Änderungen:
   ```bash
   git checkout -b feature/mein-feature
   ```
   oder
   ```bash
   git checkout -b fix/bug-fix
   ```
4. **Committe** deine Änderungen mit einer aussagekräftigen Nachricht
5. **Pushe** den Branch in deinen Fork und öffne einen **Pull Request (PR)** gegen unseren `main`-Branch. Verlinke in deiner Request dein Issue
