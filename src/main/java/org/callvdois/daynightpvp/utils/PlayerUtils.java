package org.callvdois.daynightpvp.utils;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.files.ConfigFile;

public class PlayerUtils {

    private final ConfigFile configFile;

    public PlayerUtils() {
        configFile = new ConfigFile();
    }

    public void sendMessageToAllPlayers(World world, String message) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(message);
        }
    }

    public void playSoundToAllPlayers(World world, Sound sound) {
        for (Player player : world.getPlayers()) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

    public void sendTitleToAllPlayers(World world, String title, String subTitle) {
        for (Player player : world.getPlayers()) {
            player.sendTitle(title, subTitle, configFile.getNotifyPlayersTitleFadeIn(), configFile.getNotifyPlayersTitleStay(), configFile.getNotifyPlayersTitleFadeOut());
        }
    }
}
