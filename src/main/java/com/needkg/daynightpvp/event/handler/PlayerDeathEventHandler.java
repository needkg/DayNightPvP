package com.needkg.daynightpvp.event.handler;

import org.bukkit.event.entity.PlayerDeathEvent;

@FunctionalInterface
public interface PlayerDeathEventHandler {

    void handle(PlayerDeathEvent event);

}
