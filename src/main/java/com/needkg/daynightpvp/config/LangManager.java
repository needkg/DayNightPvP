package com.needkg.daynightpvp.config;

import com.needkg.daynightpvp.config.settings.GeneralSettings;

public class LangManager extends AbstractConfigurationFile {

    private static final int LATEST_FILE_VERSION = 17;
    private final GeneralSettings generalSettings;

    public LangManager(GeneralSettings generalSettings) {
        this.generalSettings = generalSettings;
    }

    @Override
    protected String getFilePath() {
        return "lang/" + generalSettings.getLanguage() + ".yml";
    }

    @Override
    protected int getLatestFileVersion() {
        return LATEST_FILE_VERSION;
    }
} 