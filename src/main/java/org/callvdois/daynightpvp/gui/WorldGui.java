package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;

public class WorldGui {

    public static Inventory worldGui;
    private final ItemStack back;
    private final ItemStack exit;
    private final ItemStack day;
    private final ItemStack night;
    private final ItemStack panelGray;

    public WorldGui() {
        back = ItemUtils.createCustomHead(LangManager.backButton, "backToWorlds", LangManager.backButtonDescription2, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        exit = ItemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
        day = ItemUtils.createCustomHead(LangManager.dayButton, "day", LangManager.dayButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg5MDQyMDgyYmI3YTc2MThiNzg0ZWU3NjA1YTEzNGM1ODgzNGUyMWUzNzRjODg4OTM3MTYxMDU3ZjZjNyJ9fX0=");
        night = ItemUtils.createCustomHead(LangManager.nightButton, "night", LangManager.nightButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdkNjhiYjE0NGUxNTlmZmRiMGJiMmFiZGQ1ODNmZjM4OWFlNzEwNjgyY2E3N2U2NTM1MzkzYWUyMjEzN2EifX19");
        panelGray = ItemUtils.createItem(ChatColor.RED + "###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);
    }

    public void open(Player player, World world) {
        worldGui = Bukkit.createInventory(null, 18, GuiManager.guiWorldTitle);

        if (!(world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END)) {
            worldGui.setItem(3, day);
            worldGui.setItem(5, night);
        }

        worldGui.setItem(9, back);
        worldGui.setItem(17, exit);

        for (int slot = 0; slot < worldGui.getSize(); slot++) {
            if (worldGui.getItem(slot) == null) {
                worldGui.setItem(slot, panelGray);
            }
        }

        player.openInventory(worldGui);
    }

}
