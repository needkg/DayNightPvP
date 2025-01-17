package me.needkg.daynightpvp.listeners.damage.base;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.config.IntegrationConfiguration;
import me.needkg.daynightpvp.configuration.config.NotificationConfiguration;
import me.needkg.daynightpvp.configuration.message.NotificationMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.feature.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.feature.worldguard.flags.DaytimePvpFlag;
import me.needkg.daynightpvp.util.WorldUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDamageListener {

    private final GriefPreventionManager griefPreventionManager;
    private final IntegrationConfiguration integrationConfiguration;
    private final NotificationConfiguration notificationConfiguration;
    private final NotificationMessages notificationMessages;

    protected AbstractDamageListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.griefPreventionManager = container.getGriefPreventionManager();
        this.integrationConfiguration = container.getConfigurationContainer().getIntegrationConfiguration();
        this.notificationConfiguration = container.getConfigurationContainer().getNotificationConfiguration();
        this.notificationMessages = container.getMessageContainer().getNotifications();
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
            attacker.sendMessage(notificationMessages.getSelfImmune());
            return true;
        }

        if (victim.hasPermission("dnp.immune")) {
            attacker.sendMessage(notificationMessages.getPlayerImmune());
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
            if (notificationConfiguration.getNotificationsChatNoPvpWarn(worldName)) {
                attacker.sendMessage(notificationMessages.getDaytimeDisabled());
            }
            return true;
        }
        return false;
    }

    private boolean isGriefPreventionBlocking(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        return DayNightPvP.isGriefPresent
                && !integrationConfiguration.getIntegrationsGriefPreventionPvpInClaims(worldName)
                && griefPreventionManager.verify(victim, attacker);
    }
}