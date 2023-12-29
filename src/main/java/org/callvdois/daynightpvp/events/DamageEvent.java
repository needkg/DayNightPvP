package org.callvdois.daynightpvp.events;

import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.griefprevention.GriefManager;
import org.callvdois.daynightpvp.utils.WorldUtils;
import org.callvdois.daynightpvp.worldguard.AllowPvpOnDayFlag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageEvent implements Listener {

    private final GriefManager griefManager;
    private final AllowPvpOnDayFlag allowPvpOnDayFlag;

    public DamageEvent() {
        griefManager = new GriefManager();
        allowPvpOnDayFlag = new AllowPvpOnDayFlag();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player damagedPlayer = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getHitEntity() instanceof Player) || !(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player damagedPlayer = (Player) event.getHitEntity();
        Player damager = (Player) event.getEntity().getShooter();

        if (checkHooks(damagedPlayer, damager)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {

        if (event.getPotion().getShooter() instanceof Player) {

            Player damager = (Player) event.getPotion().getShooter();

            for (PotionEffect effectType : event.getPotion().getEffects()) {
                if (isPotionEffectHarmful(effectType.getType())) {
                    for (Player damagedPlayer : Bukkit.getServer().getOnlinePlayers()) {
                        if (event.getAffectedEntities().contains(damagedPlayer) && damagedPlayer != damager) {
                            if (checkHooks(damagedPlayer, damager)) {
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

    private boolean checkHooks(Player damagedPlayer,Player damager) {
        if (DayNightPvP.worldGuardIsPresent && allowPvpOnDayFlag.checkState(damagedPlayer) && allowPvpOnDayFlag.checkState(damager)) {
            return false;
        }
        if (DayNightPvP.griefIsPresent && !ConfigManager.griefPreventionPvpInLand && griefManager.verify(damagedPlayer, damager)) {
            return true;
        }
        if (WorldUtils.checkPlayerIsInWorld(damagedPlayer)) {
            return true;
        }
        return false;
    }

}
