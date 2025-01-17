package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validators.ConfigurationValidator;
import me.needkg.daynightpvp.core.di.ConfigurationContainer;

public class PvpConfiguration {
    private final ConfigurationValidator validator;
    private final GeneralConfiguration generalConfiguration;

    public PvpConfiguration(ConfigurationValidator validator, ConfigurationContainer configurationContainer) {
        this.validator = validator;
        this.generalConfiguration = configurationContainer.getGeneralConfiguration();
    }

    public boolean getPvpAutomaticEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".pvp.automatic.enabled", true);
    }

    public int getPvpAutomaticDayEnd(String worldName) {
        return validator.getInt("worlds." + worldName + ".pvp.automatic.day-end", 12000, 1, 24000);
    }

    public boolean getPvpKeepInventoryOnPvp(String worldName) {
        if (generalConfiguration.getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".pvp.keep-inventory-on-pvp", false);
        }
        return false;
    }

}
