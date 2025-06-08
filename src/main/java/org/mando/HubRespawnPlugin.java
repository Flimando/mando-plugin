package org.mando;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class HubRespawnPlugin extends JavaPlugin implements Listener {

    private List<String> allowedWorlds;
    // Spieler, die den Flugmodus manuell deaktiviert haben
    private final Set<UUID> disabledFlight = new HashSet<>();

    @Override
    public void onEnable() {
        // Falls die Konfig nicht existiert, wird eine Standardversion gespeichert
        saveDefaultConfig();
        allowedWorlds = getConfig().getStringList("allowed-worlds");

        Bukkit.getPluginManager().registerEvents(this, this);
        String reset = "\u001B[0m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";

        getLogger().info(green + "#############################");
        getLogger().info(yellow + "# Plugin by Mando | "+ blue + "MandoPlugin activated! "+ yellow + "#");
        getLogger().info(yellow + "# Version: " + blue + "1.5.1 SNAPSHOT "+ yellow + "#");
        getLogger().info(green + "#############################");

        getCommand("leaderboard").setExecutor(new LeaderboardCommand());
        getCommand("flight").setExecutor(new FlightCommand(this));
    }

    @Override
    public void onDisable() {
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String darkRed = "\u001B[91m";

        getLogger().info(red + "#############################");
        getLogger().info(darkRed + "# MandoPlugin wurde deaktiviert! #");
        getLogger().info(red + "#############################");
    }

    /**
     * Prüft, ob in der angegebenen Welt der Flugmodus erlaubt ist.
     *
     * @param worldName Name der Welt
     * @return true, wenn die Welt in der allowed-worlds Liste enthalten ist, sonst false
     */
    public boolean isWorldAllowed(String worldName) {
        return allowedWorlds != null && allowedWorlds.contains(worldName);
    }

    /**
     * Gibt die Liste der Spieler zurück, die den Flugmodus deaktiviert haben.
     */
    public Set<UUID> getDisabledFlightPlayers() {
        return disabledFlight;
    }

    /**
     * Überprüft und setzt den Flugmodus für einen Spieler gemäß seiner Präferenz
     * und der aktuellen Welt.
     */
    public void checkFlightStatus(Player player) {
        String worldName = player.getWorld().getName();
        boolean worldAllowed = isWorldAllowed(worldName);
        boolean wantsFlight = !disabledFlight.contains(player.getUniqueId());

        if (worldAllowed) {
            if (wantsFlight) {
                // Nur setzen, falls noch nicht aktiviert
                if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
            } else {
                // Spieler will nicht fliegen – falls fälschlicherweise aktiviert, ausschalten
                if (player.getAllowFlight()) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }
            }
        } else {
            // In nicht erlaubten Welten immer deaktivieren
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Teleportiere Spieler ins Hub (falls vorhanden)
        World hubWorld = Bukkit.getWorld("hub2");
        if (hubWorld != null) {
            Location hubSpawn = hubWorld.getSpawnLocation();
            event.getPlayer().teleport(hubSpawn);
        } else {
            getLogger().warning("Die 'hub'-Welt konnte nicht gefunden werden!");
        }
        checkFlightStatus(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Setze den Respawnort in der Hub-Welt
        World hubWorld = Bukkit.getWorld("hub2");
        if (hubWorld != null) {
            Location hubSpawn = hubWorld.getSpawnLocation();
            event.setRespawnLocation(hubSpawn);
        } else {
            getLogger().warning("Die 'hub'-Welt konnte nicht gefunden werden!");
        }
        // Verzögert prüfen, da die Welt erst vollständig geladen sein muss
        Bukkit.getScheduler().runTaskLater(this, () -> checkFlightStatus(event.getPlayer()), 5L);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        checkFlightStatus(event.getPlayer());
    }
}
