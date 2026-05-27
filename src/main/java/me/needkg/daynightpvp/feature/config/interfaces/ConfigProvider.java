package me.needkg.daynightpvp.feature.config.interfaces;

public interface ConfigProvider<T> {
    T get();
    void reload();
}
