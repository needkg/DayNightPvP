package me.needkg.daynightpvp.listener.damage;

import me.needkg.daynightpvp.listener.base.AbstractDamageListener;
import me.needkg.daynightpvp.util.player.PlayerValidator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener extends AbstractDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!isValidPvPInteraction(event.getDamager(), event.getEntity())) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();
        String worldName = victim.getWorld().getName();

        if (shouldCancelDamage(victim, attacker, worldName)) {
            event.setCancelled(true);
        }
    }

    private boolean isValidPvPInteraction(Entity attacker, Entity victim) {
        return PlayerValidator.isPlayer(attacker) && PlayerValidator.isPlayer(victim);
    }
}
