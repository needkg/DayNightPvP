package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validators.ConfigurationValidator;
import me.needkg.daynightpvp.utils.WorldUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GeneralConfiguration {
    private final ConfigurationValidator validator;

    public GeneralConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
    }

    public boolean getUpdateChecker() {
        return validator.getBoolean("update-checker", true);
    }

    public String getLanguage() {
        return validator.getString("language", "en-US");
    }

    public Set<String> getWorldNames() {
        return validator.getConfigSection("worlds");
    }

    public List<String> getValidWorldNames() {
        Set<String> worldNames = getWorldNames();
        List<String> validWorldNames = new ArrayList<>();
        for (String worldName : worldNames) {
            if (WorldUtil.isWorldValid(worldName)) {
                validWorldNames.add(worldName);
            }
        }
        return validWorldNames;
    }
} 