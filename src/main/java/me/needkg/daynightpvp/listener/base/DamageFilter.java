package me.needkg.daynightpvp.listener.base;

import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.integration.worldguard.flags.DaytimePvpFlag;
import me.needkg.daynightpvp.utis.plugin.PluginValidator;
import me.needkg.daynightpvp.utis.world.WorldStateChecker;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class DamageFilter {

    private final GriefPreventionManager griefPreventionManager;
    private final MessageManager messageManager;
    private final WorldConfigurationManager worldConfigurationManager;

    protected DamageFilter(GriefPreventionManager griefPreventionManager, MessageManager messageManager, WorldConfigurationManager worldConfigurationManager) {
        this.griefPreventionManager = griefPreventionManager;
        this.messageManager = messageManager;
        this.worldConfigurationManager = worldConfigurationManager;
    }

    public boolean shouldCancelDamage(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        if (attacker.hasPermission("dnp.bypass")) {
            return false;
        }

        if (isPlayerImmune(attacker, victim)) {
            return true;
        }

        if (isWorldGuardAllowingPvP(victim, attacker)) {
            return false;
        }

        if (isPlayerInDayWorld(victim, attacker, worldName)) {
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

    private boolean isPlayerInDayWorld(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        if (WorldStateChecker.isInDayWorld(victim)) {
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