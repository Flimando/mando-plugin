package org.mando;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FlightCommand implements CommandExecutor {

    private final HubRespawnPlugin plugin;

    public FlightCommand(HubRespawnPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;

        // Force-Subbefehl
        if (args.length > 0 && args[0].equalsIgnoreCase("force")) {
            if (!player.hasPermission("mandoplugin.flight.force")) {
                player.sendMessage("§cDu hast keine Berechtigung, diesen Befehl zu verwenden.");
                return true;
            }
            // Direkter Umschaltvorgang, ohne den persönlichen Präferenz-Status zu verändern
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage("§cFlugmodus wurde (force) deaktiviert.");
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage("§aFlugmodus wurde (force) aktiviert.");
            }
            return true;
        }

        // Standard-Command: Prüfe, ob der Spieler die Berechtigung für den Flugmodus besitzt
        if (!player.hasPermission("mandoplugin.flight")) {
            player.sendMessage("§cDu hast keine Berechtigung, diesen Befehl zu verwenden.");
            return true;
        }

        // Toggle der Spielerpräferenz
        UUID playerUUID = player.getUniqueId();
        if (plugin.getDisabledFlightPlayers().contains(playerUUID)) {
            plugin.getDisabledFlightPlayers().remove(playerUUID);
            player.sendMessage("§aFlugmodus wurde aktiviert.");
        } else {
            plugin.getDisabledFlightPlayers().add(playerUUID);
            player.sendMessage("§cFlugmodus wurde deaktiviert.");
        }
        // Anwenden des neuen Status anhand der aktuellen Welt und Präferenz
        plugin.checkFlightStatus(player);
        return true;
    }
}
