package org.callvdois.daynightpvp.events;

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
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.griefprevention.GriefManager;
import org.callvdois.daynightpvp.utils.WorldUtils;
import org.callvdois.daynightpvp.worldguard.AllowPvpOnDayFlag;

public class DamageEvent implements Listener {

    private final GriefManager griefManager;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public DamageEvent() {
        griefManager = new GriefManager();
        configManager = new ConfigManager();
        langManager = new LangManager();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damagedPlayer) || !(event.getDamager() instanceof Player damager)) {
            return;
        }

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
            if (configManager.getBoolean("notify-players.chat.hit-another-player-during-the-day")) {
                damager.sendMessage(langManager.getString("notify-pvp-disabled"));
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
            if (configManager.getBoolean("notify-players.chat.hit-another-player-during-the-day")) {
                damager.sendMessage(langManager.getString("notify-pvp-disabled"));
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
                                if (configManager.getBoolean("notify-players.chat.hit-another-player-during-the-day")) {
                                    damager.sendMessage(langManager.getString("notify-pvp-disabled"));
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
        if (DayNightPvP.worldGuardIsPresent && AllowPvpOnDayFlag.checkState(damagedPlayer) && AllowPvpOnDayFlag.checkState(damager)) {
            return false;
        }
        if (DayNightPvP.griefIsPresent && !configManager.getBoolean("griefprevention.pvp-in-land") && griefManager.verify(damagedPlayer, damager)) {
            return true;
        }
        return WorldUtils.checkPlayerIsInWorld(damagedPlayer);
    }

}
