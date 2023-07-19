package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainGui {

    private final ItemUtils itemUtils;
    private final List<String> langHeads = new ArrayList<>();

    public MainGui() {
        itemUtils = new ItemUtils();
        langHeads.add("pt-BR, brazilianBanner");
        langHeads.add("en-US, euaBanner");
        langHeads.add("es-ES, spanishBanner");
    }

    public void open(Player player) {
        Inventory mainGui = Bukkit.createInventory(null, 9, GuiManager.guiTitle);

        String langSelected = ConfigManager.selectedLang;
        ItemStack langSelector = itemUtils.createCustomHead(LangManager.langButton, "langSelector", LangManager.langButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlYTJmODk0NzNhMWM0ZmRiOWE5YjQ5ZTI0MTJjZDk0YmRlZWQzMjc0MjdlZGVmNDQwZmY5MjRlOGRiOTA4OCJ9fX0=");

        for (String head : langHeads) {
            String[] part = head.split(", ");
            if (langSelected.equals(part[0])) {
                langSelector = itemUtils.createCustomBanner(LangManager.langButton, "langSelector", LangManager.langButtonDescription1, itemUtils.getBanner(part[1]));
            }
        }

        ItemStack reload = itemUtils.createCustomHead(LangManager.reloadButton, "reload", LangManager.reloadButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjk3ZDZkN2JlOTg1ZDA2MjJhNDhlOTA2OThlOTA3M2Y3ZmY4ODEzMjkyODEyZWJkMTczMGRiYTBlMDFjZjE4ZiJ9fX0=");
        ItemStack worlds = itemUtils.createCustomHead(LangManager.worldsButton, "worlds", LangManager.worldsButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=");
        ItemStack exit = itemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
        ItemStack panel = itemUtils.createItem(ChatColor.RED +"###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);

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
