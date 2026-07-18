package com.needkg.daynightpvp;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.command.ReloadPluginCommand;
import com.needkg.daynightpvp.command.factory.ReloadPluginFactory;
import com.needkg.daynightpvp.config.ResourceLoader;
import com.needkg.daynightpvp.event.listener.PluginReloadEventListener;

public class CommandManager {

    private final JavaPlugin plugin;
    private final ApplicationLifecycle applicationLifecycle;

    public CommandManager(JavaPlugin plugin, ApplicationLifecycle applicationLifecycle) {
        this.plugin = plugin;
        this.applicationLifecycle = applicationLifecycle;
    }

    public void initialize(
            ResourceLoader resourceLoaderLanguage) {

        ReloadPluginCommand reloadPluginCommand = new ReloadPluginCommand(
                ReloadPluginFactory.getLanguageConfig(resourceLoaderLanguage),
                applicationLifecycle);

        reloadPluginCommand.register(plugin);

        var pluginReloadEventListener = new PluginReloadEventListener(Set.of(reloadPluginCommand));

        Bukkit.getPluginManager().registerEvents(pluginReloadEventListener, plugin);

    }

}