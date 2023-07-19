package com.needkg.daynightpvp.utils;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerInteract {

    public static void sendMessageToPlayers(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public static void playSoundToAllPlayers(World world, Sound sound, float volume, float pitch) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public static void playSoundToPlayer(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void sendTitleToPlayers(World world, String title, String subTitle) {
        for (Player player : world.getPlayers()) {
            player.sendTitle(title, subTitle);
        }
    }
}
