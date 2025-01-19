package me.needkg.daynightpvp.listener.damage;

import me.needkg.daynightpvp.listener.base.AbstractDamageListener;
import me.needkg.daynightpvp.util.player.PlayerValidator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener extends AbstractDamageListener implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void onProjectileHitEvent(ProjectileHitEvent event) {
        Entity hitEntity = event.getHitEntity();
        Projectile projectile = event.getEntity();

        if (!isValidProjectileHit(hitEntity, projectile)) {
            return;
        }

        Player attacker = (Player) projectile.getShooter();
        Player victim = (Player) hitEntity;
        String worldName = victim.getWorld().getName();

        if (shouldCancelDamage(victim, attacker, worldName)) {
            event.setCancelled(true);
        }
    }

    private boolean isValidProjectileHit(Entity hitEntity, Projectile projectile) {
        return PlayerValidator.isPlayer(hitEntity)
                && projectile.getShooter() != null
                && PlayerValidator.isPlayer(projectile.getShooter());
    }
}
