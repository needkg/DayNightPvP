package me.needkg.daynightpvp.configuration;

import me.needkg.daynightpvp.configuration.base.AbstractConfigurationFile;
import me.needkg.daynightpvp.configuration.settings.GeneralConfiguration;

public class LanguageManager extends AbstractConfigurationFile {

    private static final int LATEST_FILE_VERSION = 17;
    private final GeneralConfiguration generalConfiguration;

    public LanguageManager(GeneralConfiguration generalConfiguration) {
        this.generalConfiguration = generalConfiguration;
    }

    @Override
    protected String getFilePath() {
        return "lang/" + generalConfiguration.getLanguage() + ".yml";
    }

    @Override
    protected int getLatestFileVersion() {
        return LATEST_FILE_VERSION;
    }
} 