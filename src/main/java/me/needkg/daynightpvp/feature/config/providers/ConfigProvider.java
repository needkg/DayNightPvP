package me.needkg.daynightpvp.feature.config.providers;

import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.lifecycle.Reloadable;

public interface ConfigProvider<T> extends Initializable, Reloadable {
    T get();
}
