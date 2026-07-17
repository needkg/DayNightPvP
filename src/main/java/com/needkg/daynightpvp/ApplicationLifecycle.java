package com.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.command.ReloadPluginCommand;

public class ApplicationLifecycle {

    private final JavaPlugin plugin;
    private final FeatureManager featureManager;
    private final CommandManager commandManager;

    ApplicationLifecycle(JavaPlugin plugin) {
        this.plugin = plugin;
        this.featureManager = new FeatureManager(plugin);
        this.commandManager = new CommandManager(plugin, this);
    }
    

    public void startup() {

        final var resourceManager = new ResourceManager();

        final var pluginResource = resourceManager.load(plugin, "config.yml", false);
        final var languageResource = resourceManager.load(
                plugin,
                "lang/" + pluginResource.getValue(String.class, "lang", "en") + ".yml",
                false);
        final var defaultLanguageResource = resourceManager.load(plugin, "lang/en.yml", false);
        final var worldsResource = resourceManager.load(plugin, "worlds.yml", false);

        featureManager.initialize(
                pluginResource,
                languageResource,
                defaultLanguageResource);

        commandManager.initialize(
            languageResource,
            defaultLanguageResource);

    }

    public void reload() {
        shutdown();
        startup();
    }

    public void shutdown() {
        featureManager.shutdown();
    }

}
