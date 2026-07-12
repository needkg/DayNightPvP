package me.needkg.daynightpvp.shared.lifecycle;

public interface Loadable<T, K> {
    T load(K key);
}
