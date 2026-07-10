package me.needkg.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.config.loader.ConfigLoader;
import me.needkg.daynightpvp.feature.config.models.ResourceFile;
import me.needkg.daynightpvp.feature.config.services.GlobalSettingsService;
import me.needkg.daynightpvp.feature.config.services.MessagesConfigService;
import me.needkg.daynightpvp.shared.logging.Logger;

public final class DayNightPvp extends JavaPlugin {
    
    private Logger logger;
    private GlobalSettingsService globalSettingsService;
    private MessagesConfigService messagesConfigService;

    @Override
    public void onEnable() {

        logger = new Logger(this);

        logger.info("Loading resources files...");
        ConfigLoader configLoader = new ConfigLoader(this);

        logger.info("Loading configuration file (1/2)");
        ResourceFile configResource = configLoader.initializeFile("config.yml");
        globalSettingsService = new GlobalSettingsService(configResource);

        logger.info("Loading language file (2/2)");
        ResourceFile messagesResource = configLoader.initializeFile("lang/" + globalSettingsService.get().language() + ".yml");
        messagesConfigService = new MessagesConfigService(messagesResource);

        logger.info("DayNightPvP enabled!");

    }

    @Override
    public void onDisable() {
    }
}
