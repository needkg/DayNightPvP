package me.needkg.daynightpvp.listeners.damage;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.utils.WorldUtils;
import me.needkg.daynightpvp.worldguard.flags.AllowDaytimePvpFlag;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDamageManager {

    private final GriefPreventionManager griefPreventionManager;
    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;

    protected AbstractDamageManager() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.griefPreventionManager = new GriefPreventionManager();
        this.worldSettings = container.getWorldSettings();
        this.messageSettings = container.getMessageSettings();
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
            attacker.sendMessage(messageSettings.getNotifySelfImmune());
            return true;
        }
        
        if (victim.hasPermission("dnp.immune")) {
            attacker.sendMessage(messageSettings.getNotifyPlayerImmune());
            return true;
        }
        
        return false;
    }

    private boolean isWorldGuardAllowingPvP(@NotNull Player victim, @NotNull Player attacker) {
        return DayNightPvP.isWorldGuardPresent 
            && AllowDaytimePvpFlag.checkStateOnPosition(victim)
            && AllowDaytimePvpFlag.checkStateOnPosition(attacker);
    }

    private boolean isPlayerInDayWorld(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        if (WorldUtils.isPlayerInDayWorld(victim)) {
            if (worldSettings.getNotifyPlayersChatHitAnotherPlayerDuringDay(worldName)) {
                attacker.sendMessage(messageSettings.getNotifyPvpDisabled());
            }
            return true;
        }
        return false;
    }

    private boolean isGriefPreventionBlocking(@NotNull Player victim, @NotNull Player attacker, @NotNull String worldName) {
        return DayNightPvP.isGriefPresent 
            && !worldSettings.getGriefPreventionPvpInLand(worldName)
                && griefPreventionManager.verify(victim, attacker);
    }
}
