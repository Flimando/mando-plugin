# 🚀 MandoPlugin

**Version:** 1.5.1-SNAPSHOT  
**Minecraft Version:** 1.20.1+  
**API:** Paper/Spigot  
**Autor:** Mando

---

## 📋 Beschreibung

Das **MandoPlugin** ist ein vielseitiges Minecraft-Plugin, das mehrere nützliche Features für deinen Server bereitstellt. Es wurde speziell für Paper-Server entwickelt und bietet eine nahtlose Integration in bestehende Hub-Server-Umgebungen.

### ✨ Hauptfeatures

- **🏠 Hub-Teleportation**: Automatische Teleportation aller Spieler ins Hub beim Server-Beitritt und Respawn
- **🏆 Leaderboard**: Top 5 Ranking nach Spielzeit der Server-Mitglieder
- **✈️ Flight-Management**: Intelligente Flugsteuerung basierend auf Welten-Berechtigung
- **🔧 Konfigurierbar**: Einfache Anpassung über config.yml

---

## 🛠️ Installation

1. **Voraussetzungen**:
   - Paper oder Spigot Server (Minecraft 1.20.1+)
   - Java 17 oder höher

2. **Download & Installation**:
   ```bash
   # Plugin aus dem Repository klonen
   git clone https://github.com/username/MandoPlugin.git
   cd MandoPlugin
   
   # Mit Maven kompilieren
   mvn clean package
   ```

3. **Plugin installieren**:
   - Die generierte `.jar`-Datei aus `target/` in den `plugins/`-Ordner deines Servers kopieren
   - Server neustarten

---

## ⚙️ Konfiguration

### config.yml
```yaml
allowed-worlds:
  - "hub2"
  - "creative"
```

**Erklärung:**
- `allowed-worlds`: Liste der Welten, in denen Spieler fliegen dürfen

---

## 🎮 Commands

| Command | Beschreibung | Berechtigung | Verwendung |
|---------|-------------|-------------|------------|
| `/leaderboard` | Zeigt die Top 5 Spieler nach Spielzeit | Keine | `/leaderboard` |
| `/flight` | Aktiviert/Deaktiviert den Flugmodus | Keine | `/flight` |

---

## 🌍 Features im Detail

### Hub-System
- **Automatische Teleportation**: Alle Spieler werden beim Betreten des Servers automatisch zur Hub-Welt (`hub2`) teleportiert
- **Respawn-Management**: Bei einem Tod respawnen Spieler ebenfalls im Hub
- **Weltenwechsel-Support**: Nahtlose Integration beim Wechsel zwischen verschiedenen Welten

### Flight-Management
- **Welten-basiert**: Flugmodus wird nur in konfigurierten Welten aktiviert
- **Spieler-Präferenz**: Spieler können ihre Flug-Einstellung individuell verwalten
- **Automatische Anpassung**: Bei Weltenwechsel wird der Flugstatus automatisch angepasst

### Leaderboard-System
- **Spielzeit-Tracking**: Erfassung der gesamten Spielzeit pro Spieler
- **Top 5 Ranking**: Übersichtliche Anzeige der aktivsten Spieler
- **Echtzeit-Updates**: Automatische Aktualisierung der Statistiken

---

## 🔧 Entwicklung

### Build-Prozess
```bash
# Projekt kompilieren
mvn clean compile

# Tests ausführen
mvn test

# Plugin-JAR erstellen
mvn package
```

### Projektstruktur
```
MandoPlugin/
├── src/
│   ├── main/
│   │   ├── java/org/mando/
│   │   │   ├── HubRespawnPlugin.java    # Hauptklasse
│   │   │   ├── FlightCommand.java       # Flight-Command
│   │   │   ├── LeaderboardPlugin.java   # Leaderboard-Logic
│   │   │   └── LeaderboardCommand.java  # Leaderboard-Command
│   │   └── resources/
│   │       ├── plugin.yml              # Plugin-Konfiguration
│   │       └── config.yml              # Standard-Config
│   └── test/                           # Unit Tests
├── pom.xml                             # Maven-Konfiguration
└── README.md                           # Diese Datei
```

---

## 🚨 Bekannte Probleme

- Das Plugin sucht nach der Welt `hub2` - stelle sicher, dass diese existiert
- Flight-Permissions werden automatisch verwaltet, überschreiben eventuell andere Plugin-Einstellungen

---

## 📝 Changelog

### Version 1.5.1-SNAPSHOT
- Hub-Teleportation beim Serverstart
- Flight-Management System
- Leaderboard-Feature
- Konfigurierbare Welt-Berechtigung

---

## 🤝 Mitwirken

1. Fork das Repository
2. Erstelle einen Feature-Branch (`git checkout -b feature/amazing-feature`)
3. Committe deine Änderungen (`git commit -m 'Add amazing feature'`)
4. Push zum Branch (`git push origin feature/amazing-feature`)
5. Öffne einen Pull Request

---

## 📄 Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Siehe `LICENSE`-Datei für Details.

---

## 📞 Support

Bei Fragen oder Problemen:
- **Issues**: [GitHub Issues](https://github.com/username/MandoPlugin/issues)
- **Discord**: Mando#1234

---

*Entwickelt mit ❤️ für die Minecraft-Community*
