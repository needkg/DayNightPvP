package me.needkg.daynightpvp.listeners.damage;

import me.needkg.daynightpvp.listeners.damage.base.AbstractDamageListener;
import me.needkg.daynightpvp.utils.PlayerUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public class PotionSplashListener extends AbstractDamageListener implements Listener {

    private static final Set<PotionEffectType> HARMFUL_EFFECTS = Set.of(
            PotionEffectType.HARM,
            PotionEffectType.POISON,
            PotionEffectType.BAD_OMEN
    );

    @EventHandler(priority = EventPriority.HIGH)
    public void onPotionSplash(PotionSplashEvent event) {
        ThrownPotion potion = event.getPotion();

        if (!isValidPotionAttack(potion)) {
            return;
        }

        Player attacker = (Player) potion.getShooter();

        if (!hasHarmfulEffect(potion.getEffects())) {
            return;
        }

        handlePotionDamage(event, attacker);
    }

    private boolean isValidPotionAttack(ThrownPotion potion) {
        return potion.getShooter() != null && PlayerUtil.isPlayerInstance(potion.getShooter());
    }

    private boolean hasHarmfulEffect(Iterable<PotionEffect> effects) {
        for (PotionEffect effect : effects) {
            if (HARMFUL_EFFECTS.contains(effect.getType())) {
                return true;
            }
        }
        return false;
    }

    private void handlePotionDamage(PotionSplashEvent event, Player attacker) {
        for (LivingEntity entity : event.getAffectedEntities()) {
            if (!(entity instanceof Player victim) || victim == attacker) {
                continue;
            }

            if (shouldCancelDamage(victim, attacker, victim.getWorld().getName())) {
                event.setIntensity(victim, 0.0);
            }
        }
    }
}
