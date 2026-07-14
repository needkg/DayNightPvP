package com.needkg.daynightpvp.feature;

import org.bukkit.event.entity.PlayerDeathEvent;

import com.needkg.daynightpvp.event.handler.PlayerDeathEventHandler;

public class MoneyLossFeature implements PlayerDeathEventHandler {

    private Boolean enabled;
    private Boolean onlyNight;
    private Boolean rewardKiller;

    @Override
    public void handle(PlayerDeathEvent event) {
        // TODO Auto-generated method stub
        System.out.println("PlayerDeathEvent handled");
    }

}
