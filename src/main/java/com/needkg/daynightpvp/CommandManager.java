package com.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.command.ReloadPluginCommand;
import com.needkg.daynightpvp.command.factory.ReloadPluginFactory;
import com.needkg.daynightpvp.config.ResourceLoader;

public class CommandManager {

    private final JavaPlugin plugin;
    private final ApplicationLifecycle applicationLifecycle;

    public CommandManager(JavaPlugin plugin, ApplicationLifecycle applicationLifecycle) {
        this.plugin = plugin;
        this.applicationLifecycle = applicationLifecycle;
    }

    public void initialize(
            ResourceLoader resourceLoaderLanguage,
            ResourceLoader defaultResourceLoaderLanguage) {

        ReloadPluginCommand reloadPluginCommand = ReloadPluginFactory.create(
                applicationLifecycle,
                resourceLoaderLanguage,
                defaultResourceLoaderLanguage);

    }

}