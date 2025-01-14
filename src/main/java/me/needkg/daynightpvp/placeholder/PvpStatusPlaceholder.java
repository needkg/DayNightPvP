package me.needkg.daynightpvp.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.utils.SearchUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PvpStatusPlaceholder extends PlaceholderExpansion {

    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;

    public PvpStatusPlaceholder() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldSettings = container.getWorldSettings();
        this.messageSettings = container.getMessageSettings();
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

        if (player == null) return messageSettings.getFeedbackError();

        if (params.equalsIgnoreCase("pvp_status_current_world")) {
            boolean pvpStatus;

            World world = player.getWorld();
            if (SearchUtils.containsWorldName(worldSettings.getWorldNames(), world.getName())) {
                long time = world.getTime();
                pvpStatus = time >= worldSettings.getAutomaticPvpDayEnd(world.getName());
            } else {
                return messageSettings.getFeedbackError();
            }
            return pvpStatus ? messageSettings.getPlaceholderPvpEnabled() : messageSettings.getPlaceholderPvpDisabled();
        }

        if (params.startsWith("pvp_status_world")) {
            boolean pvpStatus;
            String worldName = params.substring("pvp_status_world:".length());
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                if (SearchUtils.containsWorldName(worldSettings.getWorldNames(), world.getName())) {
                    long time = world.getTime();
                    pvpStatus = time >= worldSettings.getAutomaticPvpDayEnd(world.getName());
                    return pvpStatus ? messageSettings.getPlaceholderPvpEnabled() : messageSettings.getPlaceholderPvpDisabled();
                }
            }
        }

        return messageSettings.getFeedbackError();
    }

}
