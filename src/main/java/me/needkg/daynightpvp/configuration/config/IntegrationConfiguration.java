package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;
import me.needkg.daynightpvp.core.di.ConfigurationContainer;

public class IntegrationConfiguration {
    private final ConfigurationValidator validator;
    private final GeneralConfiguration generalConfiguration;

    public IntegrationConfiguration(ConfigurationValidator validator, ConfigurationContainer configurationContainer) {
        this.validator = validator;
        this.generalConfiguration = configurationContainer.getGeneralConfiguration();
    }

    public boolean getIntegrationsVaultLoseMoneyEnabled(String worldName) {
        if (generalConfiguration.getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".integrations.vault.lose-money.enabled", false);
        }
        return false;
    }

    public boolean getIntegrationsVaultLoseMoneyOnlyAtNight(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".integrations.vault.lose-money.only-at-night", true);
    }

    public boolean getIntegrationsVaultLoseMoneyRewardKiller(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".integrations.vault.lose-money.reward-killer", true);
    }

    public boolean getIntegrationsGriefPreventionPvpInClaims(String worldName) {
        if (generalConfiguration.getWorldNames().contains(worldName)) {
            return validator.getBoolean("worlds." + worldName + ".integrations.grief-prevention.pvp-in-claims", false);
        }
        return false;
    }

}
