package com.needkg.daynightpvp.feature;

import org.bukkit.event.entity.PlayerDeathEvent;

import com.needkg.daynightpvp.event.handler.PlayerDeathEventHandler;

public class MoneyLossFeature implements PlayerDeathEventHandler {

    @Override
    public void handle(PlayerDeathEvent event) {
        // TODO Auto-generated method stub
        System.out.println("PlayerDeathEvent handled");
    }

    public record Config(
            Boolean enabled,
            Boolean onlyNight,
            Boolean rewardKiller) {

    }

}
