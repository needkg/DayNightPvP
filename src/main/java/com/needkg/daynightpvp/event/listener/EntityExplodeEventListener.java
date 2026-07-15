package com.needkg.daynightpvp.event.listener;

import com.needkg.daynightpvp.event.handler.EntityExplodeEventHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.Set;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeEventListener implements Listener {

    private final Set<EntityExplodeEventHandler> entityExplodeEventHandlers;

    public EntityExplodeEventListener(Set<EntityExplodeEventHandler> entityExplodeEventHandlers) {
        this.entityExplodeEventHandlers = entityExplodeEventHandlers;
    }

    @EventHandler
    public void listen(EntityExplodeEvent event) {
        for (EntityExplodeEventHandler handler : entityExplodeEventHandlers) {
            handler.handle(event);
        }
    }

}
