package com.needkg.daynightpvp;

import java.util.Optional;

import com.needkg.daynightpvp.feature.DayNightFeatureConfig;

public record DnpWorld(
        Boolean enabled,
        Optional<DayNightFeatureConfig> dayNight) {

}
