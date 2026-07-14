package me.needkg.daynightpvp.integration.bstats;

import me.needkg.daynightpvp.shared.lifecycle.Initializable;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MetricsInitializer implements Initializable {

    private final JavaPlugin plugin;
    private final int pluginId;
    private static final int PLUGIN_ID = 17990;

    public MetricsInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginId = PLUGIN_ID;
    }

    @Override
    public void init() {
        new Metrics(plugin, pluginId);
    }

}
