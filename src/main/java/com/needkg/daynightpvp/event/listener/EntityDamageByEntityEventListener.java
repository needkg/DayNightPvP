package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.needkg.daynightpvp.event.handler.EntityDamageByEntityEventHandler;

public class EntityDamageByEntityEventListener implements Listener {

    private final Set<EntityDamageByEntityEventHandler> entityDamageByEntityEventHandlers;

    public EntityDamageByEntityEventListener(Set<EntityDamageByEntityEventHandler> entityDamageByEntityEventHandlers) {
        this.entityDamageByEntityEventHandlers = entityDamageByEntityEventHandlers;
    }

    @EventHandler
    public void listen(EntityDamageByEntityEvent event) {
        for (EntityDamageByEntityEventHandler handler : entityDamageByEntityEventHandlers) {
            handler.handle(event);
        }
    }

}
