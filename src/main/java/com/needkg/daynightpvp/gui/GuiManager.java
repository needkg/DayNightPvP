package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.commands.GetHead;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    public void mainGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "§eDayNightPvP §7Gui");

        ItemStack reload = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzA1ZWI3ZTA0ODg2ODMyNTRlZGI2NzgyYzVkYjhkMjdjZDA3OGU3ODRkZTJkY2RjZDI1Y2UwODY0ZGZhYmQzOCJ9fX0=");
        ItemMeta reloadMeta = reload.getItemMeta();
        reloadMeta.setDisplayName("Recarregar");
        List<String> reloadLore = new ArrayList<>();
        reloadLore.add("Clique para recarregar os arquivos de configuração");
        reloadMeta.setLore(reloadLore);
        reloadMeta.setLocalizedName("reload");
        reload.setItemMeta(reloadMeta);

        ItemStack worlds = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=");
        ItemMeta worldsMeta = worlds.getItemMeta();
        worldsMeta.setDisplayName("Mundos");
        List<String> worldsLore = new ArrayList<>();
        worldsLore.add("Clique para ver os mundos");
        worldsMeta.setLore(worldsLore);
        worldsMeta.setLocalizedName("worlds");
        worlds.setItemMeta(worldsMeta);

        ItemStack lang = new ItemStack(Material.PAPER);
        ItemMeta langMeta = lang.getItemMeta();
        langMeta.setDisplayName("Linguagens");
        List<String> langLore = new ArrayList<>();
        langLore.add("Clique para vers as linguagens disponíveis");
        langMeta.setLore(langLore);
        langMeta.setLocalizedName("langSelector");
        lang.setItemMeta(langMeta);

        ItemStack exit = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=");
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName("Sair");
        List<String> exitLore = new ArrayList<>();
        exitLore.add("Clique para sair");
        exitMeta.setLore(exitLore);
        exitMeta.setLocalizedName("exit");
        exit.setItemMeta(exitMeta);

        ItemStack panel = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta panelMeta = exit.getItemMeta();
        panelMeta.setDisplayName("Vazio");
        panel.setItemMeta(panelMeta);

        gui.setItem(0, worlds);
        gui.setItem(1, panel);
        gui.setItem(2, panel);
        gui.setItem(3, panel);
        gui.setItem(4, panel);
        gui.setItem(5, panel);
        gui.setItem(6, lang);
        gui.setItem(7, reload);
        gui.setItem(8, exit);

        player.openInventory(gui);
    }

    public void langGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "§eDayNightPvP §7Gui");

        ItemStack ptBr = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE0NjQ3NWQ1ZGNjODE1ZjZjNWYyODU5ZWRiYjEwNjExZjNlODYxYzBlYjE0ZjA4ODE2MWIzYzBjY2IyYjBkOSJ9fX0=");
        ItemMeta ptBrMeta = ptBr.getItemMeta();
        ptBrMeta.setDisplayName("Português Brasil");
        List<String> ptBrLore = new ArrayList<>();
        ptBrLore.add("Carregar o arquivo pt-BR.yml");
        ptBrMeta.setLore(ptBrLore);
        ptBrMeta.setLocalizedName("pt-BR");
        ptBr.setItemMeta(ptBrMeta);

        ItemStack enUs = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM3ZmM4NDFhZDIwNjA4ZTc2MWQ0MTMxOTEyM2NkYjU1ODU2YTBlYmMzODA0NzAzODU2ZTVkODZhMTM1MzQ1In19fQ==");
        ItemMeta enUsMeta = enUs.getItemMeta();
        enUsMeta.setDisplayName("English");
        List<String> enUsLore = new ArrayList<>();
        enUsLore.add("Carregar o arquivo en-US.yml");
        enUsMeta.setLore(enUsLore);
        enUsMeta.setLocalizedName("en-US");
        enUs.setItemMeta(enUsMeta);

        ItemStack esEs = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUyNzAxYzY4NzFiYWUwM2MxYzM3NzA1ZDQzNDlmZTA4OGNhOTcwOWJmZGUwN2NhMGYyNzMwNDY5Y2I0ZTQifX19");
        ItemMeta esEsMeta = esEs.getItemMeta();
        esEsMeta.setDisplayName("Spanish");
        List<String> esEsLore = new ArrayList<>();
        esEsLore.add("Carregar o arquivo es-ES.yml");
        esEsMeta.setLore(esEsLore);
        esEsMeta.setLocalizedName("es-ES");
        esEs.setItemMeta(esEsMeta);

        ItemStack back = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RjOWU0ZGNmYTQyMjFhMWZhZGMxYjViMmIxMWQ4YmVlYjU3ODc5YWYxYzQyMzYyMTQyYmFlMWVkZDUifX19");
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("Voltar");
        List<String> backLore = new ArrayList<>();
        backLore.add("Voltar ao menu principal");
        backMeta.setLore(backLore);
        backMeta.setLocalizedName("back");
        back.setItemMeta(backMeta);

        ItemStack exit = GetHead.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=");
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName("Sair");
        List<String> exitLore = new ArrayList<>();
        exitLore.add("Clique para sair");
        exitMeta.setLore(exitLore);
        exitMeta.setLocalizedName("exit");
        exit.setItemMeta(exitMeta);

        ItemStack panel = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta panelMeta = exit.getItemMeta();
        panelMeta.setDisplayName("Vazio");
        panel.setItemMeta(panelMeta);

        gui.setItem(0, ptBr);
        gui.setItem(1, enUs);
        gui.setItem(2, esEs);
        gui.setItem(3, panel);
        gui.setItem(4, panel);
        gui.setItem(5, panel);
        gui.setItem(6, panel);
        gui.setItem(7, back);
        gui.setItem(8, exit);

        player.openInventory(gui);
    }
}
