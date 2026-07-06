package me.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.config.loader.ConfigLoader;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.services.GlobalSettingsService;
import me.needkg.daynightpvp.feature.config.services.MessagesConfigService;

public final class DayNightPvp extends JavaPlugin {

    private GlobalSettingsService globalSettingsService;
    private MessagesConfigService messagesConfigService;

    @Override
    public void onEnable() {

        ConfigLoader configLoader = new ConfigLoader(this);

        ResourceFile configResource = configLoader.initializeFile("config.yml");
        globalSettingsService = new GlobalSettingsService(configResource);

        ResourceFile messagesResource = configLoader.initializeFile("lang/" + globalSettingsService.get().language() + ".yml");
        messagesConfigService = new MessagesConfigService(messagesResource);

    }

    @Override
    public void onDisable() {
    }
}
