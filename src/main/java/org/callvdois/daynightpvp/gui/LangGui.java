package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;

public class LangGui {

    private final ItemStack ptBr;
    private final ItemStack enUs;
    private final ItemStack esEs;
    private final ItemStack ruRu;
    private final ItemStack back;
    private final ItemStack exit;
    private final ItemStack panel;

    public LangGui() {
        ptBr = ItemUtils.createCustomBanner(LangManager.portugueseButton, "pt-BR", LangManager.langButtonDescription2.replace("{0}", "pt-BR"), new ItemStack(Material.PAPER));
        enUs = ItemUtils.createCustomBanner(LangManager.englishButton, "en-US", LangManager.langButtonDescription2.replace("{0}", "en-US"), new ItemStack(Material.PAPER));
        esEs = ItemUtils.createCustomBanner(LangManager.spanishButton, "es-ES", LangManager.langButtonDescription2.replace("{0}", "es-ES"), new ItemStack(Material.PAPER));
        ruRu = ItemUtils.createCustomBanner(LangManager.spanishButton, "es-ES", LangManager.langButtonDescription2.replace("{0}", "es-ES"), new ItemStack(Material.PAPER));
        back = ItemUtils.createCustomHead(LangManager.backButton, "back", LangManager.backButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        exit = ItemUtils.createCustomHead(LangManager.exitButton, "exit", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
        panel = ItemUtils.createItem(ChatColor.RED +"###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);
    }

    public void open(Player player) {
        Inventory langGui = Bukkit.createInventory(null, 9, GuiManager.guiTitle);

        langGui.setItem(0, ptBr);
        langGui.setItem(1, enUs);
        langGui.setItem(2, esEs);
        langGui.setItem(3, ruRu);
        langGui.setItem(4, panel);
        langGui.setItem(5, panel);
        langGui.setItem(6, panel);
        langGui.setItem(7, back);
        langGui.setItem(8, exit);

        player.openInventory(langGui);
    }

}
