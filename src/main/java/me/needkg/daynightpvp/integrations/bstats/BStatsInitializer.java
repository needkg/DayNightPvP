package me.needkg.daynightpvp.integrations.bstats;

import me.needkg.daynightpvp.shared.lifecycle.Initializable;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BStatsInitializer implements Initializable {

    private final JavaPlugin plugin;
    private final int pluginId;
    private static final int PLUGIN_ID = 17990;

    public BStatsInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginId = PLUGIN_ID;
    }

    @Override
    public void init() {
        new Metrics(plugin, pluginId);
    }

}
