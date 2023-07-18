package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.events.InventoryEvent;
import com.needkg.daynightpvp.utils.ItemUtils;
import com.needkg.daynightpvp.utils.SearchUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WorldGui {

    private final ItemUtils itemUtils;
    private final GuiManager guiManager;
    public static Inventory worldGui;

    public WorldGui() {
        itemUtils = new ItemUtils();
        guiManager = new GuiManager();
    }

    public void open(Player player, String worldName) {
        worldGui = Bukkit.createInventory(null, 18, GuiManager.guiWorldTitle);

        ItemStack back = itemUtils.createCustomHead(LangManager.backButton, "backToWorlds", LangManager.backButtonDescription2, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJiYTgxYjQ3ZDVlZTA2YjQ4NGVhOWJkZjIyOTM0ZTZhYmNhNWU0Y2VkN2JlMzkwNWQ2YWU2ZWNkNmZjZWEyYSJ9fX0=");
        ItemStack exit = itemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQwYTE0MjA4NDRjZTIzN2E0NWQyZTdlNTQ0ZDEzNTg0MWU5ZjgyZDA5ZTIwMzI2N2NmODg5NmM4NTE1ZTM2MCJ9fX0=");
        ItemStack day = itemUtils.createCustomHead(LangManager.dayButton, "day", LangManager.dayButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg5MDQyMDgyYmI3YTc2MThiNzg0ZWU3NjA1YTEzNGM1ODgzNGUyMWUzNzRjODg4OTM3MTYxMDU3ZjZjNyJ9fX0=");
        ItemStack night = itemUtils.createCustomHead(LangManager.nightButton, "night", LangManager.nightButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdkNjhiYjE0NGUxNTlmZmRiMGJiMmFiZGQ1ODNmZjM4OWFlNzEwNjgyY2E3N2U2NTM1MzkzYWUyMjEzN2EifX19");

        guiManager.updateWorldGui(worldGui, worldName);

        World world = Bukkit.getWorld(worldName);

        if (!(world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END)) {
            worldGui.setItem(3, day);
            worldGui.setItem(5, night);
        }

        worldGui.setItem(9, back);
        worldGui.setItem(17, exit);

        ItemStack panelRed = itemUtils.createItem(ChatColor.RED +"###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);

        for (int slot = 0; slot < worldGui.getSize(); slot++) {
            if (worldGui.getItem(slot) == null) {
                worldGui.setItem(slot, panelRed);
            }
        }

        player.openInventory(worldGui);
    }

}
