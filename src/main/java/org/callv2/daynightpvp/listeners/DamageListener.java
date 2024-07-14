package org.callv2.daynightpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.griefprevention.GriefPreventionHandler;
import org.callv2.daynightpvp.utils.PlayerUtils;
import org.callv2.daynightpvp.utils.WorldUtils;
import org.callv2.daynightpvp.worldguard.AllowDaytimePvpFlag;

public class DamageListener implements Listener {

    private final GriefPreventionHandler griefPreventionHandler;
    private final boolean notifyPlayersChatHitAnotherPlayerDuringDay;
    private final String notifyPvpDisabled;
    private final boolean griefPreventionPvpInLandEnabled;

    public DamageListener(ConfigFile configFile, LangFile langFile) {
        this.griefPreventionHandler = new GriefPreventionHandler();

        this.notifyPlayersChatHitAnotherPlayerDuringDay = configFile.getNotifyPlayersChatHitAnotherPlayerDuringTheDay();
        this.notifyPvpDisabled = langFile.getNotifyPvpDisabled();
        this.griefPreventionPvpInLandEnabled = configFile.getGriefPreventionPvpInLandEnabled();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!PlayerUtils.isRealPlayer(event.getEntity())) {
            return;
        }

        if (!PlayerUtils.isRealPlayer(event.getDamager())) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player damagedPlayer = (Player) event.getEntity();

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
            if (notifyPlayersChatHitAnotherPlayerDuringDay) {
                damager.sendMessage(notifyPvpDisabled);
            }
        }
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {

        if (!PlayerUtils.isRealPlayer(event.getEntity())) {
            return;
        }

        if (!PlayerUtils.isRealPlayer(event.getEntity().getShooter())) {
            return;
        }

        Player damager = (Player) event.getEntity().getShooter();
        Player damagedPlayer = (Player) event.getEntity();

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
            if (notifyPlayersChatHitAnotherPlayerDuringDay) {
                damager.sendMessage(notifyPvpDisabled);
            }
        }

    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (PlayerUtils.isRealPlayer(event.getPotion().getShooter())) {
            Player damager = (Player) event.getPotion().getShooter();
            for (PotionEffect effectType : event.getPotion().getEffects()) {
                if (isPotionEffectHarmful(effectType.getType())) {
                    for (Player damagedPlayer : Bukkit.getServer().getOnlinePlayers()) {
                        if (event.getAffectedEntities().contains(damagedPlayer) && damagedPlayer != damager) {
                            if (checkHooks(damagedPlayer, damager)) {
                                event.setIntensity(damagedPlayer, 0.0);
                                if (notifyPlayersChatHitAnotherPlayerDuringDay) {
                                    damager.sendMessage(notifyPvpDisabled);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private boolean isPotionEffectHarmful(PotionEffectType effectType) {
        return effectType != null && (effectType.equals(PotionEffectType.HARM)
                || effectType.equals(PotionEffectType.POISON)
                || effectType.equals(PotionEffectType.BAD_OMEN)
        );
    }

    private boolean checkHooks(Player damagedPlayer, Player damager) {
        if (damager.hasPermission("dnp.bypasspvp")) {
            return false;
        }
        if (DayNightPvP.worldGuardIsPresent && AllowDaytimePvpFlag.checkStateOnPosition(damagedPlayer) && AllowDaytimePvpFlag.checkStateOnPosition(damager)) {
            return false;
        }
        if (DayNightPvP.griefIsPresent && !griefPreventionPvpInLandEnabled && griefPreventionHandler.verify(damagedPlayer, damager)) {
            return true;
        }
        return WorldUtils.checkPlayerIsInWorld(damagedPlayer);
    }

}
