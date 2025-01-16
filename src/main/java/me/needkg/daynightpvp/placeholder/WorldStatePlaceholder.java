package me.needkg.daynightpvp.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.runnables.WorldStateController;
import me.needkg.daynightpvp.utils.SearchUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldStatePlaceholder extends PlaceholderExpansion {
    private static final String IDENTIFIER = "dnp";
    private static final String AUTHOR = "needkg";
    private static final String VERSION = "GENERIC";

    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;

    public WorldStatePlaceholder() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldSettings = container.getWorldSettings();
        this.messageSettings = container.getMessageSettings();
    }

    @Override
    public @NotNull String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public @NotNull String getAuthor() {
        return AUTHOR;
    }

    @Override
    public @NotNull String getVersion() {
        return VERSION;
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
        if (player == null) {
            return messageSettings.getFeedbackError();
        }

        if (params.equalsIgnoreCase("state_pvp_current_world")) {
            return getPvpStateForWorld(player.getWorld());
        }

        if (params.startsWith("state_pvp_world:")) {
            String worldName = params.substring("state_pvp_world:".length());
            World world = Bukkit.getWorld(worldName);

            if (world != null) {
                return getPvpStateForWorld(world);
            }
        }

        return messageSettings.getFeedbackError();
    }

    private String getPvpStateForWorld(World world) {
        if (!SearchUtils.containsWorldName(worldSettings.getWorldNames(), world.getName())) {
            return messageSettings.getFeedbackError();
        }

        boolean pvpStatus = isPvpEnabled(world);
        return pvpStatus ? messageSettings.getPlaceholderPvpEnabled() : messageSettings.getPlaceholderPvpDisabled();
    }

    private boolean isPvpEnabled(World world) {
        //long time = world.getTime();
        //return time >= worldSettings.getAutomaticPvpDayEnd(world.getName());
        return WorldStateController.nightWorlds.contains(world);
    }
}
