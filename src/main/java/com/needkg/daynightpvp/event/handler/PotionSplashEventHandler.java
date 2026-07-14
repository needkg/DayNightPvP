package com.needkg.daynightpvp.event.handler;

import org.bukkit.event.entity.PotionSplashEvent;

@FunctionalInterface
public interface PotionSplashEventHandler {

    void handle(PotionSplashEvent event);

}
