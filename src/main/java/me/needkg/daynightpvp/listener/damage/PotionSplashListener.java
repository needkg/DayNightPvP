package me.needkg.daynightpvp.listener.damage;

import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.listener.base.DamageFilter;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.player.PlayerValidator;
import org.bukkit.World;
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

public class PotionSplashListener extends DamageFilter implements Listener {

    public PotionSplashListener(GriefPreventionManager griefPreventionManager, MessageManager messageManager, WorldConfigurationManager worldConfigurationManager, WorldStateManager worldStateManager) {
        super(griefPreventionManager, messageManager, worldConfigurationManager, worldStateManager);
    }

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
        return potion.getShooter() != null && PlayerValidator.isPlayer(potion.getShooter());
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

            World world = victim.getWorld();
            String worldName = world.getName();

            if (shouldCancelDamage(victim, attacker, worldName, world)) {
                event.setIntensity(victim, 0.0);
            }
        }
    }
}
