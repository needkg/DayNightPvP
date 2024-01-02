package org.callvdois.daynightpvp.utils;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.config.ConfigManager;

public class PlayerUtils {

    private final ConfigManager configManager;

    public PlayerUtils() {
        configManager = new ConfigManager();
    }

    public static void sendMessageToAllPlayers(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public static void sendMessageToPlayer(Player player, String message) {
        player.sendMessage(message);
    }

    public static void playSoundToAllPlayers(World world, Sound sound) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

    public static void playSoundToPlayer(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public void sendTitleToAllPlayers(World world, String title, String subTitle) {
        for (Player player : world.getPlayers()) {
            player.sendTitle(title, subTitle, configManager.getInt("notify-players.title.fade-in"), configManager.getInt("notify-players.title.stay"), configManager.getInt("notify-players.title.fade-out"));
        }
    }
}
