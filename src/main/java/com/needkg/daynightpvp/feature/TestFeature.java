package com.needkg.daynightpvp.feature;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.needkg.daynightpvp.event.handler.EntityDamageByEntityEventHandler;
import com.needkg.daynightpvp.event.handler.PotionSplashEventHandler;
import com.needkg.daynightpvp.event.handler.ProjectileHitEventHandler;

public class TestFeature
        implements EntityDamageByEntityEventHandler, PotionSplashEventHandler, ProjectileHitEventHandler {

    private Boolean enabled;
    private Boolean onlyNight;
    private Boolean rewardKiller;

    @Override
    public void handle(ProjectileHitEvent event) {
        // TODO Auto-generated method stub
        System.out.println(" ''TestFeature''' ProjectileHitEvent handled");
    }

    @Override
    public void handle(PotionSplashEvent event) {
        // TODO Auto-generated method stub
        System.out.println("''TestFeature''' PotionSplashEvent handled");
    }

    @Override
    public void handle(EntityDamageByEntityEvent event) {
        // TODO Auto-generated method stub
        System.out.println("''TestFeature''' EntityDamageByEntityEvent handled");
    }
}
