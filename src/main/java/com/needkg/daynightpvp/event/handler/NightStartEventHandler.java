package com.needkg.daynightpvp.event.handler;

import com.needkg.daynightpvp.event.NightStartEvent;

@FunctionalInterface
public interface NightStartEventHandler {

    void handle(NightStartEvent event);

}