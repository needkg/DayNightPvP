package me.needkg.daynightpvp.integration.bstats;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MetricsInitializer {

    private final JavaPlugin plugin;
    private final int pluginId = 19067;

    public MetricsInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        new Metrics(plugin, pluginId);
    }

}
