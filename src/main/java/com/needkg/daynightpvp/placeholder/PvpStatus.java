package com.needkg.daynightpvp.placeholder;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.PluginUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PvpStatus extends PlaceholderExpansion {


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
        return DayNightPvP.plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {

        if (params.equalsIgnoreCase("current_world_pvpstatus")) {
            boolean pvpEnabled = player.getWorld().getPVP();
            return pvpEnabled ? LangManager.onMessage : LangManager.offMessage;
        }

        if (params.startsWith("pvpstatus_")) {
            String worldName = params.substring("pvpstatus_".length());
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                boolean pvpEnabled = world.getPVP();
                return pvpEnabled ? LangManager.onMessage : LangManager.offMessage;
            }
        }
        return null;
    }

}
