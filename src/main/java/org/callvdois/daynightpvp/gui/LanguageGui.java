package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;

import java.io.File;

public class LanguageGui {

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 18, MainGui.inventoryTitle);

        File folder = new File(DayNightPvP.getInstance().getDataFolder() + "/lang");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        int position = 0;

        for (File file: listOfFiles) {
            String[] name = file.getName().split("\\.");
            ItemStack item = ItemUtils.createItem(ChatColor.YELLOW + name[0], name[0], LangManager.langButtonDescription2.replace("{0}", ChatColor.YELLOW + name[0]), Material.PAPER);
            inventory.setItem(position, item);

            position++;
        }

        ItemStack backButton = ItemUtils.createCustomHead(LangManager.backButton, "backButton", LangManager.backButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        ItemStack exitButton = ItemUtils.createCustomHead(LangManager.exitButton, "exitButton", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
        ItemStack separatorGlass = ItemUtils.createItem(ChatColor.RED + "###", "nothing", " ", Material.GRAY_STAINED_GLASS_PANE);

        inventory.setItem(16, backButton);
        inventory.setItem(17, exitButton);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, separatorGlass);
            }
        }

        player.openInventory(inventory);
    }

}
