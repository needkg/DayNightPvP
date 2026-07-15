package com.needkg.daynightpvp.event.handler;

import org.bukkit.event.entity.EntityExplodeEvent;

@FunctionalInterface
public interface EntityExplodeEventHandler {

    void handle(EntityExplodeEvent event);

}
