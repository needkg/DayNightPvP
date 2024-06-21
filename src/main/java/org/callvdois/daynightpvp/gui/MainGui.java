package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;

public class MainGui {

    public static String inventoryTitle;
    private final LangManager langManager;

    public MainGui() {
        inventoryTitle = "§c§l» DayNightPvP";
        langManager = new LangManager();
    }

    public void open(Player player) {

        Inventory inventory = Bukkit.createInventory(player, 9, inventoryTitle);

        ItemStack worldsButton = ItemUtils.createCustomHead(langManager.getGuiWorldsButton(), "worldsButton", langManager.getGuiWorldsButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=");
        ItemStack separatorGlass = ItemUtils.createItem(ChatColor.RED + "###", "nothing", " ", Material.GRAY_STAINED_GLASS_PANE);
        ItemStack languageButton = ItemUtils.createItem(langManager.getGuiLanguagesButton(), "languageButton", langManager.getGuiLanguagesButtonDescription(), Material.PAPER);
        ItemStack reloadButon = ItemUtils.createCustomHead(langManager.getGuiReloadButton(), "reloadButton", langManager.getGuiReloadButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk3ZDZkN2JlOTg1ZDA2MjJhNDhlOTA2OThlOTA3M2Y3ZmY4ODEzMjkyODEyZWJkMTczMGRiYTBlMDFjZjE4ZiJ9fX0=");
        ItemStack exitButton = ItemUtils.createCustomHead(langManager.getGuiExitButton(), "exitButton", langManager.getGuiExitButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");

        inventory.setItem(0, worldsButton);
        inventory.setItem(1, separatorGlass);
        inventory.setItem(2, separatorGlass);
        inventory.setItem(3, separatorGlass);
        inventory.setItem(4, separatorGlass);
        inventory.setItem(5, separatorGlass);
        inventory.setItem(6, languageButton);
        inventory.setItem(7, reloadButon);
        inventory.setItem(8, exitButton);

        player.openInventory(inventory);
    }

}
