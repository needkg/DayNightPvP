package me.needkg.daynightpvp.feature.config.providers;

public interface ConfigProvider<T> {
    T get();
    void reload();
}
