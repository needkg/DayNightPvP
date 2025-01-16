package me.needkg.daynightpvp.api.placeholder.provider;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.task.WorldStateController;
import me.needkg.daynightpvp.util.SearchUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldStateProvider extends PlaceholderExpansion {
    private static final String IDENTIFIER = "dnp";
    private static final String AUTHOR = "needkg";
    private static final String VERSION = "GENERIC";

    private final WorldConfiguration worldConfiguration;
    private final MessageConfiguration messageConfiguration;

    public WorldStateProvider() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldConfiguration = container.getWorldSettings();
        this.messageConfiguration = container.getMessageSettings();
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
            return messageConfiguration.getFeedbackError();
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

        return messageConfiguration.getFeedbackError();
    }

    private String getPvpStateForWorld(World world) {
        if (!SearchUtil.containsWorldName(worldConfiguration.getWorldNames(), world.getName())) {
            return messageConfiguration.getFeedbackError();
        }

        boolean pvpStatus = isPvpEnabled(world);
        return pvpStatus ? messageConfiguration.getPlaceholderPvpEnabled() : messageConfiguration.getPlaceholderPvpDisabled();
    }

    private boolean isPvpEnabled(World world) {
        //long time = world.getTime();
        //return time >= worldSettings.getAutomaticPvpDayEnd(world.getName());
        return WorldStateController.nightWorlds.contains(world);
    }
}
