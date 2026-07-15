package com.needkg.daynightpvp.config;

import java.util.Set;

public interface ResourceLoader {

    <T> T getValue(Class<T> type, String path);

    <T> T getValue(Class<T> type, String path, T defaultValue);

    <T> T getValue(Class<T> type, String path, ResourceLoader defaultReourceLoader);

    Set<String> getKeys(boolean deep);

}
