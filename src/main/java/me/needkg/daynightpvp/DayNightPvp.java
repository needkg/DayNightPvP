package me.needkg.daynightpvp;

import me.needkg.daynightpvp.application.ApplicationLifecycle;
import me.needkg.daynightpvp.shared.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public final class DayNightPvp extends JavaPlugin {

    private ApplicationLifecycle applicationLifecycle;
    private Logger logger;

    @Override
    public void onEnable() {

        logger = new Logger(this);

        applicationLifecycle = new ApplicationLifecycle(this, logger);
        applicationLifecycle.init();
    }

    @Override
    public void onDisable() {
        applicationLifecycle.stop();
    }
}
