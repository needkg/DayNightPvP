package me.needkg.daynightpvp.configuration.manager;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import me.needkg.daynightpvp.configuration.enums.Global;
import me.needkg.daynightpvp.utils.world.WorldValidator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GlobalConfigurationManager {
    private final ConfigurationAccess access;

    public GlobalConfigurationManager(ConfigurationAccess access) {
        this.access = access;
    }

    // Debug Configuration
    public boolean isDebugEnabled() {
        return Global.DEBUG.isEnabled(access);
    }

    public boolean isDebugVerbose() {
        return Global.DEBUG.isVerbose(access);
    }

    // General Configuration
    public boolean isUpdateCheckerEnabled() {
        return Global.GENERAL.isUpdateCheckerEnabled(access);
    }

    public String getLanguage() {
        return Global.GENERAL.getLanguage(access);
    }

    public Set<String> getWorldNames() {
        return Global.GENERAL.getWorldNames(access);
    }

    @NotNull
    public List<World> getEnabledWorlds() {
        return getWorldNames().stream()
                .filter(WorldValidator::exists)
                .map(Bukkit::getWorld)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
} 