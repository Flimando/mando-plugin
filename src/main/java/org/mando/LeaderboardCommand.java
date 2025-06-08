package org.mando;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;


class LeaderboardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;
        List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());

        // Sortiere Spieler nach Spielzeit (größte zuerst)
        List<OfflinePlayer> sortedPlayers = players.stream()
                .sorted((p1, p2) -> Integer.compare(
                        p2.getStatistic(Statistic.PLAY_ONE_MINUTE),
                        p1.getStatistic(Statistic.PLAY_ONE_MINUTE)))
                .limit(5) // Nur die Top 5
                .collect(Collectors.toList());

        player.sendMessage("\n§6Top 5 Spieler nach Spielzeit");
        for (int i = 0; i < sortedPlayers.size(); i++) {
            OfflinePlayer p = sortedPlayers.get(i);
            int minutes = p.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60; // Umrechnung in Minuten
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            player.sendMessage("§e" + (i + 1) + ". §a" + p.getName() + "§7 - §b" + hours + "h " + remainingMinutes + "min");
        }
        return true;
    }
}