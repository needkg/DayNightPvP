package me.needkg.daynightpvp.listeners.damage.base;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.features.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.features.worldguard.flags.DaytimePvpFlag;
import me.needkg.daynightpvp.utils.WorldUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDamageListener {

    private final GriefPreventionManager griefPreventionManager;
    private final MessageManager messageManager;
    private final WorldConfigurationManager worldConfigurationManager;

    protected AbstractDamageListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.griefPreventionManager = container.getGriefPreventionManager();
        this.messageManager = container.getMessageManager();
        this.worldConfigurationManager = container.getWorldConfigurationManager();
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
            attacker.sendMessage(messageManager.getMessage(MessageType.COMBAT_RESTRICTION_SELF_IMMUNE));
            return true;
        }

        if (victim.hasPermission("dnp.immune")) {
            attacker.sendMessage(messageManager.getMessage(MessageType.COMBAT_RESTRICTION_PLAYER_IMMUNE));
            return true;
        }

        return false;
    }

    private boolean isWorldGuardAllowingPvP(@NotNull Player victim, @NotNull Player attacker) {
        return DayNightPvP.isWorldGuardPresent
                && DaytimePvpFlag.checkStateOnPosition(victim)
                && DaytimePvpFlag.checkStateOnPosition(attacker);
    }

    private boolean isPlayerInDayWorld(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        if (WorldUtil.isPlayerInDayWorld(victim)) {
            if (worldConfigurationManager.isNotificationsChatNoPvpWarn(worldName)) {
                attacker.sendMessage(messageManager.getMessage(MessageType.COMBAT_RESTRICTION_DAYTIME));
            }
            return true;
        }
        return false;
    }

    private boolean isGriefPreventionBlocking(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        return DayNightPvP.isGriefPresent
                && !worldConfigurationManager.isIntegrationsGriefPreventionPvpInClaims(worldName)
                && griefPreventionManager.isPlayerInClaim(victim, attacker);
    }
}