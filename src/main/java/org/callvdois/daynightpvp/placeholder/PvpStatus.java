package org.callvdois.daynightpvp.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

public class PvpStatus extends PlaceholderExpansion {

    private final LangManager langManager;
    private final ConfigManager configManager;

    public PvpStatus() {
        langManager = new LangManager();
        configManager = new ConfigManager();
    }


    @Override
    public @NotNull String getIdentifier() {
        return "dnp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "needkg";
    }

    @Override
    public @NotNull String getVersion() {
        return DayNightPvP.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {

        if (params.equalsIgnoreCase("current_world_pvpstatus")) {
            boolean pvpStatus;

            World world = player.getWorld();
            if (SearchUtils.stringExistInList(configManager.getList("daynightpvp.worlds"), world.getName())) {
                long time = world.getTime();
                pvpStatus = time >= configManager.getInt("daynightpvp.day-end");
            } else {
                return langManager.getString("feedback-error");
            }
            return pvpStatus ? langManager.getString("placeholder-pvp-enabled") : langManager.getString("placeholder-pvp-disabled");
        }

        if (params.startsWith("pvpstatus_")) {
            boolean pvpStatus;
            String worldName = params.substring("pvpstatus_".length());
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                if (SearchUtils.stringExistInList(configManager.getList("daynightpvp.worlds"), world.getName())) {
                    long time = world.getTime();
                    pvpStatus = time >= configManager.getInt("daynightpvp.day-end");
                    return pvpStatus ? langManager.getString("placeholder-pvp-enabled") : langManager.getString("placeholder-pvp-disabled");
                }
            }
        }
        return langManager.getString("feedback-error");
    }

}
