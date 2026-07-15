package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.needkg.daynightpvp.event.DayStartEvent;
import com.needkg.daynightpvp.event.handler.DayStartEventHandler;

public class DayStartEventListener implements Listener {

    private final Set<DayStartEventHandler> dayStartEventHandlers;

    public DayStartEventListener(Set<DayStartEventHandler> dayStartEventHandlers) {
        this.dayStartEventHandlers = dayStartEventHandlers;
    }

    @EventHandler
    public void listen(DayStartEvent event) {
        for (DayStartEventHandler handler : dayStartEventHandlers) {
            handler.handle(event);
        }
    }

}