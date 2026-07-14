package me.needkg.daynightpvp.feature.gui.components;

import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;

public interface Button {
    Component name();
    int slot();
    ItemStack render();
}
