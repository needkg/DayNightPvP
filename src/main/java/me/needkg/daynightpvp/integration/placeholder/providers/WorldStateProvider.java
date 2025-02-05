package me.needkg.daynightpvp.integration.placeholder.providers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.tasks.enums.WorldState;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.search.WorldCollectionSearcher;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldStateProvider extends PlaceholderExpansion {
    private static final String IDENTIFIER = "dnp";
    private static final String AUTHOR = "needkg";
    private static final String VERSION = "GENERIC";

    private final MessageManager messageManager;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final WorldStateManager worldStateManager;

    public WorldStateProvider(MessageManager messageManager, GlobalConfigurationManager globalConfigurationManager, WorldStateManager worldStateManager) {
        this.messageManager = messageManager;
        this.globalConfigurationManager = globalConfigurationManager;
        this.worldStateManager = worldStateManager;
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
    public String onPlaceholderRequest(Player player, @NotNull String placeholder) {
        if (player == null) {
            return messageManager.getMessage(Message.SYSTEM_ERROR);
        }

        if (placeholder.equalsIgnoreCase("state_pvp_current_world")) {
            return getPvpStateForWorld(player.getWorld());
        }

        if (placeholder.startsWith("state_pvp_world:")) {
            String worldName = placeholder.substring("state_pvp_world:".length());
            World world = Bukkit.getWorld(worldName);

            if (world != null) {
                return getPvpStateForWorld(world);
            }
        }

        return messageManager.getMessage(Message.SYSTEM_ERROR);
    }

    private String getPvpStateForWorld(World world) {
        if (!WorldCollectionSearcher.containsWorld(globalConfigurationManager.getEnabledWorlds(), world)) {
            return messageManager.getMessage(Message.SYSTEM_ERROR);
        }

        boolean pvpStatus = isPvpEnabled(world);
        return pvpStatus ? messageManager.getMessage(Message.PLACEHOLDER_PVP_ENABLED) : messageManager.getMessage(Message.PLACEHOLDER_PVP_DISABLED);
    }

    private boolean isPvpEnabled(World world) {
        return worldStateManager.getWorldState(world) == WorldState.NIGHT;
    }
}
