package com.needkg.daynightpvp.config.world;

import java.util.Optional;

import com.needkg.daynightpvp.feature.AutoPvpFeature;

public record WorldConfig(
        Optional<AutoPvpFeature.Config> pvp,
        Optional<IntegrationsConfig> integration,
        Optional<NotificationsConfig> notifications) {

}
