package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;

public class WorldsGui {

    public static Inventory worldsGui;
    private final ItemStack back;
    private final ItemStack exit;

    public WorldsGui() {
        back = ItemUtils.createCustomHead(LangManager.backButton, "back", LangManager.backButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        exit = ItemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
    }

    public void open(Player player) {
        worldsGui = Bukkit.createInventory(null, 27, GuiManager.guiWorldsTitle);

        worldsGui.setItem(25, back);
        worldsGui.setItem(26, exit);

        player.openInventory(worldsGui);
    }
}
