package com.needkg.daynightpvp;

import java.util.Optional;
import java.util.Set;

public interface DnpWorldGateway {

    public Set<DnpWorld> findAll();

    public Optional<DnpWorld> findByName(String name);

}
