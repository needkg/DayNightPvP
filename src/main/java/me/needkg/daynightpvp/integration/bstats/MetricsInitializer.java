package me.needkg.daynightpvp.integration.bstats;

import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MetricsInitializer implements Initializable {

    private final JavaPlugin plugin;
    private final int pluginId;

    public MetricsInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginId = 17990;
    }

    @Override
    public void init() {
        new Metrics(plugin, pluginId);
    }

}
