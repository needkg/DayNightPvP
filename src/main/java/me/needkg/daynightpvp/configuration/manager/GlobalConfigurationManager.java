package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.emun.Global;
import me.needkg.daynightpvp.util.world.WorldValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GlobalConfigurationManager {
    private final ConfigurationAccess access;

    public GlobalConfigurationManager(ConfigurationAccess access) {
        this.access = access;
    }

    // Debug Configuration
    public boolean isDebugEnabled() {
        return Global.DEBUG.isEnabled(access);
    }

    public boolean isDebugVerbose() {
        return Global.DEBUG.isVerbose(access);
    }

    // General Configuration
    public boolean isUpdateCheckerEnabled() {
        return Global.GENERAL.isUpdateCheckerEnabled(access);
    }

    public String getLanguage() {
        return Global.GENERAL.getLanguage(access);
    }

    public Set<String> getWorldNames() {
        return Global.GENERAL.getWorldNames(access);
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