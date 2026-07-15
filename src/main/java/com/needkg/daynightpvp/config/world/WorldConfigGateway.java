package com.needkg.daynightpvp.config.world;

import java.util.Map;
import java.util.Optional;

public interface WorldConfigGateway {

    public Map<String, WorldConfig> findAll();

    public Optional<WorldConfig> findByName(String name);

}
