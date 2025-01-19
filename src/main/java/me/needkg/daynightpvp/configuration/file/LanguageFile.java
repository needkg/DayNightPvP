package me.needkg.daynightpvp.configuration.file;

import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;

import java.io.File;

public class LanguageFile extends YamlFileBase {

    private static final int LATEST_FILE_VERSION = 18;
    private final GlobalConfigurationManager globalConfigurationManager;

    public LanguageFile(GlobalConfigurationManager globalConfigurationManager) {
        this.globalConfigurationManager = globalConfigurationManager;
    }

    @Override
    protected String getFilePath() {
        return "lang" + File.separator + globalConfigurationManager.getLanguage() + ".yml";
    }

    @Override
    protected int getLatestFileVersion() {
        return LATEST_FILE_VERSION;
    }
} 