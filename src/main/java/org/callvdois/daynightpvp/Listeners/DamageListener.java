package org.callvdois.daynightpvp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.files.ConfigFile;
import org.callvdois.daynightpvp.files.LangFile;
import org.callvdois.daynightpvp.griefprevention.GriefPreventionHandler;
import org.callvdois.daynightpvp.utils.WorldUtils;
import org.callvdois.daynightpvp.worldguard.AllowDaytimePvpFlag;

public class DamageListener implements Listener {

    private final GriefPreventionHandler griefPreventionHandler;
    private final ConfigFile configFile;
    private final LangFile langFile;
    private final WorldUtils worldUtils;

    public DamageListener() {
        griefPreventionHandler = new GriefPreventionHandler();
        configFile = new ConfigFile();
        langFile = new LangFile();
        worldUtils = new WorldUtils();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damagedPlayer) || !(event.getDamager() instanceof Player damager)) {
            return;
        }

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
            if (configFile.getNotifyPlayersChatHitAnotherPlayerDuringTheDay()) {
                damager.sendMessage(langFile.getNotifyPvpDisabled());
            }
        }
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getHitEntity() instanceof Player damagedPlayer) || !(event.getEntity().getShooter() instanceof Player damager)) {
            return;
        }

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
            if (configFile.getNotifyPlayersChatHitAnotherPlayerDuringTheDay()) {
                damager.sendMessage(langFile.getNotifyPvpDisabled());
            }
        }

    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {

        if (event.getPotion().getShooter() instanceof Player damager) {

            for (PotionEffect effectType : event.getPotion().getEffects()) {
                if (isPotionEffectHarmful(effectType.getType())) {
                    for (Player damagedPlayer : Bukkit.getServer().getOnlinePlayers()) {
                        if (event.getAffectedEntities().contains(damagedPlayer) && damagedPlayer != damager) {
                            if (checkHooks(damagedPlayer, damager)) {
                                event.setIntensity(damagedPlayer, 0.0);
                                if (configFile.getNotifyPlayersChatHitAnotherPlayerDuringTheDay()) {
                                    damager.sendMessage(langFile.getNotifyPvpDisabled());
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
        if (DayNightPvP.griefIsPresent && !configFile.getGriefPreventionPvpInLandEnabled() && griefPreventionHandler.verify(damagedPlayer, damager)) {
            return true;
        }
        return worldUtils.checkPlayerIsInWorld(damagedPlayer);
    }

}
