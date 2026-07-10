package me.needkg.daynightpvp.shared.logging;

import org.bukkit.plugin.java.JavaPlugin;

public final class Logger {

    private final JavaPlugin plugin;

    public Logger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void info(String message) {
        plugin.getSLF4JLogger().info(message);
    }

    public void warn(String message) {
        plugin.getSLF4JLogger().warn(message);
    }

    public void error(String message) {
        plugin.getSLF4JLogger().error(message);
    }

    public void error(String message, Throwable throwable) {
        plugin.getSLF4JLogger().error(message, throwable);
    }
}