package com.needkg.daynightpvp.event.listener;

import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

import com.needkg.daynightpvp.event.handler.PotionSplashEventHandler;

public class PotionSplashEventListener implements Listener {

    private final Set<PotionSplashEventHandler> potionSplashEventHandlers;

    public PotionSplashEventListener(Set<PotionSplashEventHandler> potionSplashEventHandlers) {
        this.potionSplashEventHandlers = potionSplashEventHandlers;
    }

    @EventHandler
    public void listen(PotionSplashEvent event) {
        for (PotionSplashEventHandler handler : potionSplashEventHandlers) {
            handler.handle(event);
        }
    }

}
