package me.needkg.daynightpvp.configuration;

import me.needkg.daynightpvp.configuration.base.AbstractConfigurationFile;
import me.needkg.daynightpvp.core.di.ConfigurationContainer;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;

import java.io.File;

public class LanguageManager extends AbstractConfigurationFile {

    private static final int LATEST_FILE_VERSION = 18;
    private final GeneralConfiguration generalConfiguration;

    public LanguageManager(ConfigurationContainer configurationContainer) {
        this.generalConfiguration = configurationContainer.getGeneralConfiguration();
    }

    @Override
    protected String getFilePath() {
        return "lang" + File.separator + generalConfiguration.getLanguage() + ".yml";
    }

    @Override
    protected int getLatestFileVersion() {
        return LATEST_FILE_VERSION;
    }
} 