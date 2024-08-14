package org.callv2.daynightpvp.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

public class PvpStatusPlaceholder extends PlaceholderExpansion {

    private final LangFile langFile;
    private final ConfigFile configFile;

    public PvpStatusPlaceholder(LangFile langFile, ConfigFile configFile) {
        this.langFile = langFile;
        this.configFile = configFile;
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
        return "GENERIC";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (params.equalsIgnoreCase("pvp_status_current_world")) {
            boolean pvpStatus;

            World world = player.getWorld();
            if (SearchUtils.worldExistsInWorldListSetString(configFile.getWorlds(), world.getName())) {
                long time = world.getTime();
                pvpStatus = time >= configFile.getAutomaticPvpDayEnd(world.getName());
            } else {
                return langFile.getFeedbackError();
            }
            return pvpStatus ? langFile.getPlaceholderPvpEnabled() : langFile.getPlaceholderPvpDisabled();
        }

        if (params.startsWith("pvp_status_world")) {
            boolean pvpStatus;
            String worldName = params.substring("pvp_status_world:".length());
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                if (SearchUtils.worldExistsInWorldListSetString(configFile.getWorlds(), world.getName())) {
                    long time = world.getTime();
                    pvpStatus = time >= configFile.getAutomaticPvpDayEnd(world.getName());
                    return pvpStatus ? langFile.getPlaceholderPvpEnabled() : langFile.getPlaceholderPvpDisabled();
                }
            }
        }

        return langFile.getFeedbackError();
    }

}
