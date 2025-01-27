package me.needkg.daynightpvp.utis.player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerNotifier {

    public static void broadcastMessage(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public static void broadcastSound(World world, Sound sound, Float volume) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), sound, volume, 1);
        }
    }

    public static void broadcastTitle(World world, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : world.getPlayers()) {
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        }
    }

    public static void broadcastActionBar(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }

}
