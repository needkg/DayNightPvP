package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class MainGui {

    private final List<String> langHeads = new ArrayList<>();
    private ItemStack reload;
    private ItemStack worlds;
    private ItemStack exit ;
    private ItemStack panel;

    public MainGui() {
        langHeads.add("pt-BR, brazilianBanner");
        langHeads.add("en-US, euaBanner");
        langHeads.add("es-ES, spanishBanner");

        panel = ItemUtils.createItem(ChatColor.RED +"###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);
    }

    public void open(Player player) {

        reload = ItemUtils.createCustomHead(LangManager.reloadButton, "reload", LangManager.reloadButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk3ZDZkN2JlOTg1ZDA2MjJhNDhlOTA2OThlOTA3M2Y3ZmY4ODEzMjkyODEyZWJkMTczMGRiYTBlMDFjZjE4ZiJ9fX0=");
        worlds = ItemUtils.createCustomHead(LangManager.worldsButton, "worlds", LangManager.worldsButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=");
        exit = ItemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");

        Inventory mainGui = Bukkit.createInventory(null, 9, GuiManager.guiTitle);
        String langSelected = ConfigManager.selectedLang;

        ItemStack langSelector = new ItemStack(Material.STONE);
        for (String head : langHeads) {
            String[] part = head.split(", ");
            if (langSelected.equals(part[0])) {
                langSelector = ItemUtils.createCustomBanner(LangManager.langButton, "langSelector", LangManager.langButtonDescription1, ItemUtils.getBanner(part[1]));
            }
        }
        if (langSelector.equals(new ItemStack(Material.STONE))) {
            langSelector = ItemUtils.createCustomBanner(LangManager.langButton, "langSelector", LangManager.langButtonDescription1, ItemUtils.getBanner("customLanguageBanner"));
        }

        mainGui.setItem(0, worlds);
        mainGui.setItem(1, panel);
        mainGui.setItem(2, panel);
        mainGui.setItem(3, panel);
        mainGui.setItem(4, panel);
        mainGui.setItem(5, panel);
        mainGui.setItem(6, langSelector);
        mainGui.setItem(7, reload);
        mainGui.setItem(8, exit);

        player.openInventory(mainGui);
    }

}
