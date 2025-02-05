package me.needkg.daynightpvp.configuration.file;

public class ConfigurationFile extends ResourceFile {

    private static final int LATEST_FILE_VERSION = 23;

    @Override
    protected String getFilePath() {
        return "config.yml";
    }

    @Override
    protected int getLatestFileVersion() {
        return LATEST_FILE_VERSION;
    }
} 