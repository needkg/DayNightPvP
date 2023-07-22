package com.needkg.daynightpvp.events;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player damagedPlayer = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();
        if (GriefPrevention.instance.dataStore.getClaimAt(damagedPlayer.getLocation(), true, null) != null ||
                GriefPrevention.instance.dataStore.getClaimAt(damager.getLocation(), true, null) != null) {
            event.setCancelled(true);
        }
    }

}
