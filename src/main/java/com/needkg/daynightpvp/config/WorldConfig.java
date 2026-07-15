package com.needkg.daynightpvp.config;

import java.util.Optional;

import com.needkg.daynightpvp.feature.AutoPvpFeature;
import com.needkg.daynightpvp.feature.MoneyLossFeature;

public record WorldConfig(
        Optional<AutoPvpFeature.Config> pvp,
        Optional<IntegrationsConfig> inetegration) {

}
