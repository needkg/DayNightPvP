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

        final var resourceManager = new ResourceManager(plugin);

        final var configResource = resourceManager.initializeConfigYml();
        final var languageResource = resourceManager.initializeLanguageYml(
            configResource.getValue(
                String.class, 
                "language",
                "en"));

        final var worldsResource = resourceManager.initializeWorldsYml();

        featureManager.initialize(
                worldsResource,
                languageResource);

        commandManager.initialize(languageResource);

    }

    public void reload() {
        shutdown();
        startup();
    }

    public void shutdown() {
        featureManager.shutdown();
    }

}
