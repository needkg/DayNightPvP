package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.needkg.daynightpvp.event.PluginReloadEvent;
import com.needkg.daynightpvp.event.handler.PluginReloadEventHandler;

public class PluginReloadListener implements Listener {

    private final Set<PluginReloadEventHandler> pluginReloadEventHandlers;

    public PluginReloadListener(Set<PluginReloadEventHandler> pluginReloadEventHandlers) {
        this.pluginReloadEventHandlers = pluginReloadEventHandlers;
    }

    @EventHandler
    public void listen(PluginReloadEvent event) {
        for (PluginReloadEventHandler handler : pluginReloadEventHandlers) {
            handler.handle(event);
        }
    }

}
