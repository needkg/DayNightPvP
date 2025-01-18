package me.needkg.daynightpvp.api.placeholder.providers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;
import me.needkg.daynightpvp.configuration.message.PlaceholderMessages;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.tasks.WorldStateController;
import me.needkg.daynightpvp.utils.SearchUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldStateProvider extends PlaceholderExpansion {
    private static final String IDENTIFIER = "dnp";
    private static final String AUTHOR = "needkg";
    private static final String VERSION = "GENERIC";

    private final GeneralConfiguration generalConfiguration;
    private final SystemMessages systemMessages;
    private final PlaceholderMessages placeholderMessages;

    public WorldStateProvider() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.generalConfiguration = container.getConfigurationContainer().getGeneralConfiguration();
        this.systemMessages = container.getMessageContainer().getSystem();
        this.placeholderMessages = container.getMessageContainer().getPlaceholders();
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
            return systemMessages.getErrorMessage();
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

        return systemMessages.getErrorMessage();
    }

    private String getPvpStateForWorld(World world) {
        if (!SearchUtil.containsWorldName(generalConfiguration.getWorldNames(), world.getName())) {
            return systemMessages.getErrorMessage();
        }

        boolean pvpStatus = isPvpEnabled(world);
        return pvpStatus ? placeholderMessages.getPvpEnabledMessage() : placeholderMessages.getPvpDisabledMessage();
    }

    private boolean isPvpEnabled(World world) {
        //long time = world.getTime();
        //return time >= worldSettings.getAutomaticPvpDayEnd(world.getName());
        return WorldStateController.nightWorlds.contains(world);
    }
}
