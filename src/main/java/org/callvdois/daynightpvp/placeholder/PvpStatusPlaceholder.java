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

public class PvpStatusPlaceholder extends PlaceholderExpansion {

    private final LangManager langManager;
    private final ConfigManager configManager;

    public PvpStatusPlaceholder() {
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
            if (SearchUtils.worldExistsInList(configManager.getDayNightPvpWorlds(), world.getName())) {
                long time = world.getTime();
                pvpStatus = time >= configManager.getDayNightPvpDayEnd();
            } else {
                return langManager.getFeedbackError();
            }
            return pvpStatus ? langManager.getPlaceholderPvpEnabled() : langManager.getPlaceholderPvpDisabled();
        }

        if (params.startsWith("pvpstatus_")) {
            boolean pvpStatus;
            String worldName = params.substring("pvpstatus_".length());
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                if (SearchUtils.worldExistsInList(configManager.getDayNightPvpWorlds(), world.getName())) {
                    long time = world.getTime();
                    pvpStatus = time >= configManager.getDayNightPvpDayEnd();
                    return pvpStatus ? langManager.getPlaceholderPvpEnabled() : langManager.getPlaceholderPvpDisabled();
                }
            }
        }
        return langManager.getFeedbackError();
    }

}
