package me.needkg.daynightpvp.config;

public class ConfigManager extends AbstractConfigurationFile {

    private static final int LATEST_FILE_VERSION = 20;

    @Override
    protected String getFilePath() {
        return "config.yml";
    }

    @Override
    protected int getLatestFileVersion() {
        return LATEST_FILE_VERSION;
    }
} 