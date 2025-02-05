package me.needkg.daynightpvp.listener.base;

import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.integration.worldguard.flags.DaytimePvpFlag;
import me.needkg.daynightpvp.tasks.enums.WorldState;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.plugin.PluginValidator;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class DamageFilter {

    private final GriefPreventionManager griefPreventionManager;
    private final MessageManager messageManager;
    private final WorldConfigurationManager worldConfigurationManager;
    private final WorldStateManager worldStateManager;

    protected DamageFilter(GriefPreventionManager griefPreventionManager, MessageManager messageManager, WorldConfigurationManager worldConfigurationManager, WorldStateManager worldStateManager) {
        this.griefPreventionManager = griefPreventionManager;
        this.messageManager = messageManager;
        this.worldConfigurationManager = worldConfigurationManager;
        this.worldStateManager = worldStateManager;
    }

    public boolean shouldCancelDamage(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName, @NotNull World world) {
        if (attacker.hasPermission("dnp.bypass")) {
            return false;
        }

        if (isPlayerImmune(attacker, victim)) {
            return true;
        }

        if (isWorldGuardAllowingPvP(victim, attacker)) {
            return false;
        }

        if (isPlayerInDayWorld(world, attacker, worldName)) {
            return true;
        }

        return isGriefPreventionBlocking(victim, attacker, worldName);
    }

    private boolean isPlayerImmune(@NotNull Player attacker, @NotNull Player victim) {
        if (attacker.hasPermission("dnp.immune")) {
            attacker.sendMessage(messageManager.getMessage(Message.COMBAT_RESTRICTION_SELF_IMMUNE));
            return true;
        }

        if (victim.hasPermission("dnp.immune")) {
            attacker.sendMessage(messageManager.getMessage(Message.COMBAT_RESTRICTION_PLAYER_IMMUNE));
            return true;
        }

        return false;
    }

    private boolean isWorldGuardAllowingPvP(@NotNull Player victim, @NotNull Player attacker) {
        return PluginValidator.isWorldGuardPresent()
                && DaytimePvpFlag.checkStateOnPosition(victim)
                && DaytimePvpFlag.checkStateOnPosition(attacker);
    }

    private boolean isPlayerInDayWorld(@NotNull World world, @NotNull Player attacker, @NotNull String worldName) {
        if (worldStateManager.getWorldState(world) == WorldState.DAY) {
            if (worldConfigurationManager.isNotificationsChatNoPvpWarn(worldName)) {
                attacker.sendMessage(messageManager.getMessage(Message.COMBAT_RESTRICTION_DAYTIME));
            }
            return true;
        }
        return false;
    }

    private boolean isGriefPreventionBlocking(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        return PluginValidator.isGriefPresent()
                && !worldConfigurationManager.isIntegrationsGriefPreventionPvpInClaims(worldName)
                && griefPreventionManager.isPlayerInClaim(victim, attacker);
    }
}