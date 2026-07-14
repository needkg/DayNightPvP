package me.needkg.daynightpvp.feature.gui;

import org.bukkit.inventory.Inventory;

import me.needkg.daynightpvp.feature.gui.components.Button;
import me.needkg.daynightpvp.feature.gui.menus.Menu;

public class MenuRenderer {
    
    public void render(Menu menu, Inventory inventory) {
        for (Button button : menu.buttons()) {
            inventory.setItem(button.slot(), button.render());
        }
    }

}
