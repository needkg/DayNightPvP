package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.needkg.daynightpvp.event.handler.PlayerDeathEventHandler;

public class PlayerDeathEventListener implements Listener {

    private final Set<PlayerDeathEventHandler> playerDeathEventHandlers;

    public PlayerDeathEventListener(Set<PlayerDeathEventHandler> playerDeathEventHandlers) {
        this.playerDeathEventHandlers = playerDeathEventHandlers;
    }

    @EventHandler
    public void listen(PlayerDeathEvent event) {
        for (PlayerDeathEventHandler handler : playerDeathEventHandlers) {
            handler.handle(event);
        }
    }

}
