package com.needkg.daynightpvp.config.world;

import java.util.Optional;
import java.util.Set;

public interface WorldConfigGateway {

    public Set<WorldConfig> findAll();

    public Optional<WorldConfig> findByName(String name);

}
