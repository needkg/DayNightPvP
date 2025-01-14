package com.needkg.daynightpvp.listeners;

import com.needkg.daynightpvp.config.settings.MessageSettings;
import com.needkg.daynightpvp.config.settings.WorldSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.griefprevention.GriefPreventionHandler;
import com.needkg.daynightpvp.utils.PlayerUtils;
import com.needkg.daynightpvp.utils.WorldUtils;
import com.needkg.daynightpvp.worldguard.AllowDaytimePvpFlag;

public class DamageListener implements Listener {

    private final GriefPreventionHandler griefPreventionHandler;
    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;
    //private final String notifyPvpDisabled;
    //private final String notifyPlayerImmune;
    //private final String notifySelfImmune;

    public DamageListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.griefPreventionHandler = new GriefPreventionHandler();
        this.worldSettings = container.getWorldSettings();
        this.messageSettings = container.getMessageSettings();
        //this.notifyPvpDisabled = langFile.getNotifyPvpDisabled();
        //this.notifyPlayerImmune = langFile.getNotifyPlayerImmune();
        //this.notifySelfImmune = langFile.getNotifySelfImmune();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (!PlayerUtils.isRealPlayer(event.getDamager())) {
            return;
        }

        if (!PlayerUtils.isRealPlayer(event.getEntity())) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        if (checkHooks(damaged, damager, damaged.getWorld().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onProjectileHitEvent(ProjectileHitEvent event) {

        if (!PlayerUtils.isRealPlayer(event.getHitEntity())) {
            return;
        }

        if (!PlayerUtils.isRealPlayer(event.getEntity().getShooter())) {
            return;
        }

        Player damager = (Player) event.getEntity().getShooter();
        Player damagedPlayer = (Player) event.getHitEntity();

        if (checkHooks(damagedPlayer, damager, damagedPlayer.getWorld().getName())) {
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPotionSplash(PotionSplashEvent event) {
        if (PlayerUtils.isRealPlayer(event.getPotion().getShooter())) {
            Player damager = (Player) event.getPotion().getShooter();
            for (PotionEffect effectType : event.getPotion().getEffects()) {
                if (isPotionEffectHarmful(effectType.getType())) {
                    for (Player damagedPlayer : Bukkit.getServer().getOnlinePlayers()) {
                        if (event.getAffectedEntities().contains(damagedPlayer) && damagedPlayer != damager) {
                            if (checkHooks(damagedPlayer, damager, damagedPlayer.getWorld().getName())) {
                                event.setIntensity(damagedPlayer, 0.0);
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

    private boolean checkHooks(Player damagedPlayer, Player damager, String worldName) {
        if (damager.hasPermission("dnp.bypass")) {
            return false;
        }
        if (damager.hasPermission("dnp.immune")) {
            //damager.sendMessage(notifySelfImmune);
            damager.sendMessage(messageSettings.getNotifySelfImmune());
            return true;
        }
        if (damagedPlayer.hasPermission("dnp.immune")) {
            //damager.sendMessage(notifyPlayerImmune);
            damager.sendMessage(messageSettings.getNotifyPlayerImmune());
            return true;
        }
        if (DayNightPvP.worldGuardIsPresent && AllowDaytimePvpFlag.checkStateOnPosition(damagedPlayer) && AllowDaytimePvpFlag.checkStateOnPosition(damager)) {
            return false;
        }
        if (WorldUtils.isPlayerInWorld(damagedPlayer)) {
            if (worldSettings.getNotifyPlayersChatHitAnotherPlayerDuringDay(worldName)) {
                //damager.sendMessage(notifyPvpDisabled);
                damager.sendMessage(messageSettings.getNotifyPvpDisabled());
            }
            return true;
        }
        if (DayNightPvP.griefIsPresent && !worldSettings.getGriefPreventionPvpInLand(worldName) && griefPreventionHandler.verify(damagedPlayer, damager)) {
            return true;
        }
        return false;
    }

}
