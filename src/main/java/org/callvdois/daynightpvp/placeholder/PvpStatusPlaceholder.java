package org.callvdois.daynightpvp.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.files.ConfigFile;
import org.callvdois.daynightpvp.files.LangFile;
import org.callvdois.daynightpvp.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

public class PvpStatusPlaceholder extends PlaceholderExpansion {

    private final LangFile langFile;
    private final ConfigFile configFile;
    private final SearchUtils searchUtils;

    public PvpStatusPlaceholder() {
        langFile = new LangFile();
        configFile = new ConfigFile();
        searchUtils = new SearchUtils();
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
            if (searchUtils.worldExistsInWorldList(configFile.getDayNightPvpWorlds(), world.getName())) {
                long time = world.getTime();
                pvpStatus = time >= configFile.getDayNightPvpDayEnd();
            } else {
                return langFile.getFeedbackError();
            }
            return pvpStatus ? langFile.getPlaceholderPvpEnabled() : langFile.getPlaceholderPvpDisabled();
        }

        if (params.startsWith("pvpstatus_")) {
            boolean pvpStatus;
            String worldName = params.substring("pvpstatus_".length());
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                if (searchUtils.worldExistsInWorldList(configFile.getDayNightPvpWorlds(), world.getName())) {
                    long time = world.getTime();
                    pvpStatus = time >= configFile.getDayNightPvpDayEnd();
                    return pvpStatus ? langFile.getPlaceholderPvpEnabled() : langFile.getPlaceholderPvpDisabled();
                }
            }
        }
        return langFile.getFeedbackError();
    }

}
