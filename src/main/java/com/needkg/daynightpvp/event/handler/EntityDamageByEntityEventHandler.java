package com.needkg.daynightpvp.event.handler;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

@FunctionalInterface
public interface EntityDamageByEntityEventHandler {

    void handle(EntityDamageByEntityEvent event);

}
