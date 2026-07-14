package me.needkg.daynightpvp.feature.gui.menus;

import java.util.List;

import org.bukkit.event.inventory.InventoryType;

import me.needkg.daynightpvp.feature.gui.components.Button;
import net.kyori.adventure.text.Component;

public interface Menu {
    Component title();
    InventoryType type();
    int size();
    List<Button> buttons();
}
