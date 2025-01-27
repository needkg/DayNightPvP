package me.needkg.daynightpvp.listener.damage;

    
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.listener.base.DamageFilter;
import me.needkg.daynightpvp.utis.player.PlayerValidator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener extends DamageFilter implements Listener {


    public EntityDamageListener(GriefPreventionManager griefPreventionManager, MessageManager messageManager, WorldConfigurationManager worldConfigurationManager) {
        super(griefPreventionManager, messageManager, worldConfigurationManager);

    }

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
