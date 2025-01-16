package me.needkg.daynightpvp.listeners.damage;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.di.DependencyContainer;
import me.needkg.daynightpvp.griefprevention.GriefPreventionHandler;
import me.needkg.daynightpvp.utils.WorldUtils;
import me.needkg.daynightpvp.worldguard.flags.AllowDaytimePvpFlag;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDamageHandler {

    private final GriefPreventionHandler griefPreventionHandler;
    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;

    protected AbstractDamageHandler() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.griefPreventionHandler = new GriefPreventionHandler();
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
            && griefPreventionHandler.verify(victim, attacker);
    }
}
