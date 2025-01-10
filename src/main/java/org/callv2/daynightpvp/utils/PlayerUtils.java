package org.callv2.daynightpvp.utils;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerUtils {

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

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

    public static boolean isRealPlayer(Object object) {
        return object instanceof Player;
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

}
