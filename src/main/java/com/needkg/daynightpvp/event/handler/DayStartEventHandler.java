package com.needkg.daynightpvp.event.handler;

import com.needkg.daynightpvp.event.DayStartEvent;

@FunctionalInterface
public interface DayStartEventHandler {

    void handle(DayStartEvent event);

}