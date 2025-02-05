package me.needkg.daynightpvp.listener.damage;


import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.integration.griefprevention.GriefPreventionManager;
import me.needkg.daynightpvp.listener.base.DamageFilter;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.player.PlayerValidator;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener extends DamageFilter implements Listener {


    public EntityDamageListener(GriefPreventionManager griefPreventionManager, MessageManager messageManager, WorldConfigurationManager worldConfigurationManager, WorldStateManager worldStateManager) {
        super(griefPreventionManager, messageManager, worldConfigurationManager, worldStateManager);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!isValidPvPInteraction(event.getDamager(), event.getEntity())) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();
        World world = victim.getWorld();
        String worldName = world.getName();

        if (shouldCancelDamage(victim, attacker, worldName, world)) {
            event.setCancelled(true);
        }
    }

    private boolean isValidPvPInteraction(Entity attacker, Entity victim) {
        return PlayerValidator.isPlayer(attacker) && PlayerValidator.isPlayer(victim);
    }
}
