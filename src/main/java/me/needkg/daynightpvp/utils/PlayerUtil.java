package me.needkg.daynightpvp.utils;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static void sendMessageToAllPlayers(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public static void playSoundToAllPlayers(World world, Sound sound, Float volume) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), sound, volume, 1);
        }
    }

    public static void sendTitleToAllPlayers(World world, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : world.getPlayers()) {
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        }
    }

    public static boolean isPlayerInstance(Object object) {
        return object instanceof Player;
    }

}
