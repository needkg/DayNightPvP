package com.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

public final class DayNightPvp extends JavaPlugin {

    private ApplicationLifecycle lifecycle = new ApplicationLifecycle(this);

    @Override
    public void onEnable() {
        lifecycle.startup();
    }

    @Override
    public void onDisable() {
        lifecycle.shutdown();
    }

}
