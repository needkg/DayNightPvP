package com.needkg.daynightpvp.event.handler;

import org.bukkit.event.entity.ProjectileHitEvent;

@FunctionalInterface
public interface ProjectileHitEventHandler {

    void handle(ProjectileHitEvent event);

}
