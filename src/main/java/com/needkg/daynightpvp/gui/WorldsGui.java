package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.ItemUtils;
import com.needkg.daynightpvp.utils.SearchUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WorldsGui {

    private final ItemUtils itemUtils;
    public static Inventory worldsGui;

    public WorldsGui() {
        itemUtils = new ItemUtils();
    }

    public void open(Player player) {
        worldsGui = Bukkit.createInventory(null, 27, GuiManager.guiWorldsTitle);

        ItemStack back = itemUtils.createCustomHead(LangManager.backButton, "back", LangManager.backButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        ItemStack exit = itemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");

        worldsGui.setItem(25, back);
        worldsGui.setItem(26, exit);

        player.openInventory(worldsGui);
    }
}
