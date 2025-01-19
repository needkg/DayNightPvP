package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.emun.Global;
import me.needkg.daynightpvp.util.world.WorldValidator;

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
        return Global.DEBUG.isEnabled(reader);
    }

    public boolean isDebugVerbose() {
        return Global.DEBUG.isVerbose(reader);
    }

    // General Configuration
    public boolean isUpdateCheckerEnabled() {
        return Global.GENERAL.isUpdateCheckerEnabled(reader);
    }

    public String getLanguage() {
        return Global.GENERAL.getLanguage(reader);
    }

    public Set<String> getWorldNames() {
        return Global.GENERAL.getWorldNames(reader);
    }

    public List<String> getValidWorlds() {
        Set<String> worldNames = getWorldNames();
        List<String> validWorlds = new ArrayList<>();
        for (String worldName : worldNames) {
            if (WorldValidator.exists(worldName)) {
                validWorlds.add(worldName);
            }
        }
        return validWorlds;
    }
} 