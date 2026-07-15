package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.needkg.daynightpvp.event.NightStartEvent;
import com.needkg.daynightpvp.event.handler.NightStartEventHandler;

public class NightStartEventListener implements Listener {

    private final Set<NightStartEventHandler> nightStartEventHandlers;

    public NightStartEventListener(Set<NightStartEventHandler> nightStartEventHandlers) {
        this.nightStartEventHandlers = nightStartEventHandlers;
    }

    @EventHandler
    public void listen(NightStartEvent event) {
        for (NightStartEventHandler handler : nightStartEventHandlers) {
            handler.handle(event);
        }
    }

}