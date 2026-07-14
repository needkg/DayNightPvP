package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.needkg.daynightpvp.event.handler.ProjectileHitEventHandler;

public class ProjectileHitEventListener implements Listener {

    private final Set<ProjectileHitEventHandler> projectileHitEventHandlers;

    public ProjectileHitEventListener(Set<ProjectileHitEventHandler> projectileHitEventHandlers) {
        this.projectileHitEventHandlers = projectileHitEventHandlers;
    }

    @EventHandler
    public void listen(ProjectileHitEvent event) {
        for (ProjectileHitEventHandler handler : projectileHitEventHandlers) {
            handler.handle(event);
        }
    }

}
