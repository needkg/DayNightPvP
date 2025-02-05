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
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener extends DamageFilter implements Listener {

    public ProjectileHitListener(GriefPreventionManager griefPreventionManager, MessageManager messageManager, WorldConfigurationManager worldConfigurationManager, WorldStateManager worldStateManager) {
        super(griefPreventionManager, messageManager, worldConfigurationManager, worldStateManager);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Entity hitEntity = event.getHitEntity();
        Projectile projectile = event.getEntity();

        if (!isValidProjectileHit(hitEntity, projectile)) {
            return;
        }

        Player attacker = (Player) projectile.getShooter();
        Player victim = (Player) hitEntity;
        World world = victim.getWorld();
        String worldName = world.getName();

        if (shouldCancelDamage(victim, attacker, worldName, world)) {
            event.setCancelled(true);
        }
    }

    private boolean isValidProjectileHit(Entity hitEntity, Projectile projectile) {
        return PlayerValidator.isPlayer(hitEntity)
                && projectile.getShooter() != null
                && PlayerValidator.isPlayer(projectile.getShooter());
    }
}
