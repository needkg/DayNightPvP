package com.needkg.daynightpvp.event.handler;

import com.needkg.daynightpvp.event.PluginReloadEvent;

@FunctionalInterface
public interface PluginReloadEventHandler {

    void handle(PluginReloadEvent event);

}
