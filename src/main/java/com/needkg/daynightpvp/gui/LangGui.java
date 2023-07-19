package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.ItemUtils;
import org.apache.commons.codec.language.bm.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class LangGui {

    private final ItemUtils itemUtils;

    public LangGui() {
        itemUtils = new ItemUtils();
    }

    public void open(Player player) {
        Inventory langGui = Bukkit.createInventory(null, 9, GuiManager.guiTitle);

        ItemStack ptBr = itemUtils.createCustomBanner(LangManager.portugueseButton, "pt-BR", LangManager.langButtonDescription2.replace("{0}", "pt-BR"), itemUtils.getBanner("brazilianBanner"));
        //ItemStack ptBr = itemUtils.createCustomHead(LangManager.portugueseButton, "pt-BR", LangManager.langButtonDescription2.replace("{0}", "pt-BR"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE0NjQ3NWQ1ZGNjODE1ZjZjNWYyODU5ZWRiYjEwNjExZjNlODYxYzBlYjE0ZjA4ODE2MWIzYzBjY2IyYjBkOSJ9fX0=");
        //ItemStack enUs = itemUtils.createCustomHead(LangManager.englishButton, "en-US", LangManager.langButtonDescription2.replace("{0}", "en-US"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM3ZmM4NDFhZDIwNjA4ZTc2MWQ0MTMxOTEyM2NkYjU1ODU2YTBlYmMzODA0NzAzODU2ZTVkODZhMTM1MzQ1In19fQ==");
        ItemStack enUs = itemUtils.createCustomBanner(LangManager.englishButton, "en-US", LangManager.langButtonDescription2.replace("{0}", "en-US"), itemUtils.getBanner("euaBanner"));
        //ItemStack esEs = itemUtils.createCustomHead(LangManager.spanishButton, "es-ES", LangManager.langButtonDescription2.replace("{0}", "es-ES"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUyNzAxYzY4NzFiYWUwM2MxYzM3NzA1ZDQzNDlmZTA4OGNhOTcwOWJmZGUwN2NhMGYyNzMwNDY5Y2I0ZTQifX19");
        ItemStack esEs = itemUtils.createCustomBanner(LangManager.spanishButton, "es-ES", LangManager.langButtonDescription2.replace("{0}", "es-ES"), itemUtils.getBanner("spanishBanner"));
        ItemStack back = itemUtils.createCustomHead(LangManager.backButton, "back", LangManager.backButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        ItemStack exit = itemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
        ItemStack panel = itemUtils.createItem(ChatColor.RED +"###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);

        langGui.setItem(0, ptBr);
        langGui.setItem(1, enUs);
        langGui.setItem(2, esEs);
        langGui.setItem(3, panel);
        langGui.setItem(4, panel);
        langGui.setItem(5, panel);
        langGui.setItem(6, panel);
        langGui.setItem(7, back);
        langGui.setItem(8, exit);

        player.openInventory(langGui);
    }

}
