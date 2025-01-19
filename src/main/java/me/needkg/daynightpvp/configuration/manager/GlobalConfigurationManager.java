package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.type.GlobalConfigurationType;
import me.needkg.daynightpvp.utils.WorldUtil;
import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GlobalConfigurationManager {
    private final ConfigurationAccess reader;

    public GlobalConfigurationManager(ConfigurationAccess reader) {
        this.reader = reader;
    }

    // Debug Configuration
    public boolean isDebugEnabled() {
        return GlobalConfigurationType.DEBUG.isEnabled(reader);
    }

    public boolean isDebugVerbose() {
        return GlobalConfigurationType.DEBUG.isVerbose(reader);
    }

    // General Configuration
    public boolean isUpdateCheckerEnabled() {
        return GlobalConfigurationType.GENERAL.isUpdateCheckerEnabled(reader);
    }

    public String getLanguage() {
        return GlobalConfigurationType.GENERAL.getLanguage(reader);
    }

    public Set<String> getWorldNames() {
        return GlobalConfigurationType.GENERAL.getWorldNames(reader);
    }

    public List<String> getValidWorlds() {
        Set<String> worldNames = getWorldNames();
        List<String> validWorlds = new ArrayList<>();
        for (String worldName : worldNames) {
            if (WorldUtil.isWorldValid(worldName)) {
                validWorlds.add(worldName);
            }
        }
        return validWorlds;
    }
} 