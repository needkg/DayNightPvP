package com.needkg.daynightpvp.util;

import org.bukkit.entity.Entity;

public final class EntityUtils {

    private EntityUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getWorldName(Entity entity) {
        return entity.getLocation().getWorld().getName();
    }

}
