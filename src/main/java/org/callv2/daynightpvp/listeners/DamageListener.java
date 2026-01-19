package org.callv2.daynightpvp.listeners;

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
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.claims.ClaimHandler;
import org.callv2.daynightpvp.claims.ClaimVerifier;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.PlayerUtils;
import org.callv2.daynightpvp.utils.WorldUtils;
import org.callv2.daynightpvp.worldguard.AllowDaytimePvpFlag;

public class DamageListener implements Listener {

    private final ClaimVerifier claimVerifier;
    private final ConfigFile configFile;
    private final String notifyPvpDisabled;
    private final String notifyPlayerImmune;
    private final String notifySelfImmune;

    public DamageListener(ConfigFile configFile, LangFile langFile) {
        this.claimVerifier = new ClaimVerifier();
        this.configFile = configFile;
        this.notifyPvpDisabled = langFile.getNotifyPvpDisabled();
        this.notifyPlayerImmune = langFile.getNotifyPlayerImmune();
        this.notifySelfImmune = langFile.getNotifySelfImmune();
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
            damager.sendMessage(notifySelfImmune);
            return true;
        }
        if (damagedPlayer.hasPermission("dnp.immune")) {
            damager.sendMessage(notifyPlayerImmune);
            return true;
        }
        if (DayNightPvP.worldGuardIsPresent && AllowDaytimePvpFlag.checkStateOnPosition(damagedPlayer) && AllowDaytimePvpFlag.checkStateOnPosition(damager)) {
            return false;
        }
        if (WorldUtils.isPlayerInWorld(damagedPlayer)) {
            if (configFile.getNotifyPlayersChatHitAnotherPlayerDuringDay(worldName)) {
                damager.sendMessage(notifyPvpDisabled);
            }
            return true;
        }

        return !configFile.getGriefPreventionPvpInLand(worldName) && claimVerifier.verify(damagedPlayer, damager);
    }

}
