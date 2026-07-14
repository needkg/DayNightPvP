package me.needkg.daynightpvp.feature.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.needkg.daynightpvp.feature.gui.menus.Menu;

public class MenuManager {

    private final Map<UUID, Menu> openedMenus = new HashMap<>();
    private final MenuRenderer menuRenderer;

    public MenuManager(MenuRenderer menuRenderer) {
        this.menuRenderer = menuRenderer;
    }

    public void openMenu(Player player, Menu menu) {
        Inventory inventory = Bukkit.createInventory(player, menu.size(), menu.title());
        openedMenus.put(player.getUniqueId(), menu);
        player.openInventory(inventory);
        menuRenderer.render(menu, inventory);
    }

    public void closeMenu(Player player) {
        openedMenus.remove(player.getUniqueId());
        player.closeInventory();
    }

    public Menu getOpenMenu(Player player) {
        return openedMenus.get(player.getUniqueId());
    }

}
