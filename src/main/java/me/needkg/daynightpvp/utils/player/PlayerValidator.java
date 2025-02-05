package me.needkg.daynightpvp.utils.player;

import org.bukkit.entity.Player;

public class PlayerValidator {

    public static boolean isPlayer(Object object) {
        return object instanceof Player;
    }

}
