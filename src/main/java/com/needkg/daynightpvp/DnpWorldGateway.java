package com.needkg.daynightpvp;

import java.util.Optional;
import java.util.Set;

import com.needkg.daynightpvp.config.WorldConfig;

public interface DnpWorldGateway {

    public Set<WorldConfig> findAll();

    public Optional<WorldConfig> findByName(String name);

}
