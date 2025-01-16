package me.needkg.daynightpvp.listeners.damage;

import me.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener extends AbstractDamageManager implements Listener {

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
        return PlayerUtils.isRealPlayer(hitEntity)
                && projectile.getShooter() != null
                && PlayerUtils.isRealPlayer(projectile.getShooter());
    }
}
