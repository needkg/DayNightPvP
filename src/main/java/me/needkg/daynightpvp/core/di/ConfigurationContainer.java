package me.needkg.daynightpvp.core.di;

import me.needkg.daynightpvp.configuration.config.*;
import me.needkg.daynightpvp.configuration.validators.ConfigurationValidator;

public class ConfigurationContainer {
    private final BossbarConfiguration bossbarConfiguration;
    private final DayNightDurationConfiguration dayNightDurationConfiguration;
    private final DifficultyConfiguration difficultyConfiguration;
    private final GeneralConfiguration generalConfiguration;
    private final IntegrationConfiguration integrationConfiguration;
    private final NotificationConfiguration notificationConfiguration;
    private final PvpConfiguration pvpConfiguration;
    private final DebugConfiguration debugConfiguration;

    public ConfigurationContainer(ConfigurationValidator validator) {
        this.bossbarConfiguration = new BossbarConfiguration(validator);
        this.dayNightDurationConfiguration = new DayNightDurationConfiguration(validator);
        this.difficultyConfiguration = new DifficultyConfiguration(validator);
        this.generalConfiguration = new GeneralConfiguration(validator);
        this.integrationConfiguration = new IntegrationConfiguration(validator, this);
        this.notificationConfiguration = new NotificationConfiguration(validator);
        this.pvpConfiguration = new PvpConfiguration(validator, this);
        this.debugConfiguration = new DebugConfiguration(validator);
    }

    public BossbarConfiguration getBossbarConfiguration() {
        return this.bossbarConfiguration;
    }

    public DayNightDurationConfiguration getDayNightDurationConfiguration() {
        return this.dayNightDurationConfiguration;
    }

    public DifficultyConfiguration getDifficultyConfiguration() {
        return this.difficultyConfiguration;
    }

    public GeneralConfiguration getGeneralConfiguration() {
        return this.generalConfiguration;
    }

    public IntegrationConfiguration getIntegrationConfiguration() {
        return this.integrationConfiguration;
    }

    public NotificationConfiguration getNotificationConfiguration() {
        return this.notificationConfiguration;
    }

    public PvpConfiguration getPvpConfiguration() {
        return this.pvpConfiguration;
    }

    public DebugConfiguration getDebugConfiguration() {
        return this.debugConfiguration;
    }
}