package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.commands.GetHead;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    public static String guiTitle;
    public static List<String> worldsPvpManually = new ArrayList<>();
    public static List<String> worldsDNPServiceOn;

    public GuiManager() {
        guiTitle = "§3DayNightPvP §8Gui";
    }

    public void mainGui(Player player) {
        Inventory mainGui = Bukkit.createInventory(null, 9, guiTitle);

        ItemStack reload = createItemHead("Reload", "reload", "Clique para recarregar os arquivos de configuração", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDc1ZDNkYjAzZGMyMWU1NjNiMDM0MTk3ZGE0MzViNzllY2ZlZjRiOGUyZWNkYjczMGUzNzBjMzE2NjI5ZDM2ZiJ9fX0=");
        ItemStack worlds = createItemHead("Mundos", "worlds", "Clique para acessar as configurações dos mundos", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=");
        ItemStack langSelector = createItemHead("Linguagens", "langSelector", "Ver todas as linguagens", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmM4ZWExZjUxZjI1M2ZmNTE0MmNhMTFhZTQ1MTkzYTRhZDhjM2FiNWU5YzZlZWM4YmE3YTRmY2I3YmFjNDAifX19");
        ItemStack exit = createItemHead("Sair", "exit", "Clique para sair", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=");
        ItemStack panel = createItem("Nada", "nada", "Nada", Material.RED_STAINED_GLASS_PANE);

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

    public void langGui(Player player) {
        Inventory langGui = Bukkit.createInventory(null, 9, guiTitle);

        ItemStack ptBr = createItemHead("Português", "pt-BR", "Carrega a linguagem \"pt-BR.yml\"", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE0NjQ3NWQ1ZGNjODE1ZjZjNWYyODU5ZWRiYjEwNjExZjNlODYxYzBlYjE0ZjA4ODE2MWIzYzBjY2IyYjBkOSJ9fX0=");
        ItemStack enUs = createItemHead("English", "en-US", "Carregar a linguagem \"en-US.yml\"", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM3ZmM4NDFhZDIwNjA4ZTc2MWQ0MTMxOTEyM2NkYjU1ODU2YTBlYmMzODA0NzAzODU2ZTVkODZhMTM1MzQ1In19fQ==");
        ItemStack esEs = createItemHead("Spanish", "es-ES", "Carregar a linguagem \"es-ES.yml\"", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTUyNzAxYzY4NzFiYWUwM2MxYzM3NzA1ZDQzNDlmZTA4OGNhOTcwOWJmZGUwN2NhMGYyNzMwNDY5Y2I0ZTQifX19");
        ItemStack back = createItemHead("Voltar", "back", "Voltar ao menu principal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RjOWU0ZGNmYTQyMjFhMWZhZGMxYjViMmIxMWQ4YmVlYjU3ODc5YWYxYzQyMzYyMTQyYmFlMWVkZDUifX19");
        ItemStack exit = createItemHead("Sair", "exit", "Clique para sair", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=");
        ItemStack panel = createItem("Nada", "nada", "Nada", Material.RED_STAINED_GLASS_PANE);

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

    public boolean stringExistsInList(List<String> list, String searchString) {
        for (String element : list) {
            if (element.equals(searchString)) {
                return true;
            }
        }
        return false;
    }


    public void worldsGui(Player player) {
        Inventory worldsGui = Bukkit.createInventory(null, 27, guiTitle);

        List<World> worldList = Bukkit.getServer().getWorlds();

        for (World world : worldList) {
            String worldName = world.getName();
            String dnpServiceStatus;
            String pvpStatus;
            worldsDNPServiceOn = ConfigManager.worlds;
            if (stringExistsInList(worldsDNPServiceOn, worldName)) {
                dnpServiceStatus = LangManager.onMessage;
            } else {
                dnpServiceStatus = LangManager.offMessage;
            }
            if (world.getPVP()) {
                pvpStatus = LangManager.onMessage;
            } else {
                pvpStatus = LangManager.offMessage;
            }
            ItemStack worldItem = createItemHead(worldName, worldName, "DNPService: " + dnpServiceStatus +"|PvP: " + pvpStatus + "| |Clique para ver configurações", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");

            worldsGui.addItem(worldItem);
        }

        ItemStack back = createItemHead("Voltar", "back", "Voltar ao menu principal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RjOWU0ZGNmYTQyMjFhMWZhZGMxYjViMmIxMWQ4YmVlYjU3ODc5YWYxYzQyMzYyMTQyYmFlMWVkZDUifX19");
        ItemStack exit = createItemHead("Sair", "exit", "Clique para sair", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=");

        worldsGui.setItem(25, back);
        worldsGui.setItem(26, exit);

        ItemStack panel = createItem("Nada", "nada", "Nada", Material.RED_STAINED_GLASS_PANE);

        for (int slot = 0; slot < worldsGui.getSize(); slot++) {
            if (worldsGui.getItem(slot) == null) {
                worldsGui.setItem(slot, panel);
            }
        }

        player.openInventory(worldsGui);
    }

    public Inventory worldGui;

    public void worldGui(Player player, String worldName) {
        worldGui = Bukkit.createInventory(null, 18, guiTitle);

        ItemStack back = createItemHead("Voltar", "backToWorlds", "Voltar ao menu principal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RjOWU0ZGNmYTQyMjFhMWZhZGMxYjViMmIxMWQ4YmVlYjU3ODc5YWYxYzQyMzYyMTQyYmFlMWVkZDUifX19");
        ItemStack exit = createItemHead("Sair", "exit", "Clique para sair", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQxYTNjOTY1NjIzNDg1MjdkNTc5OGYyOTE2MDkyODFmNzJlMTZkNjExZjFhNzZjMGZhN2FiZTA0MzY2NSJ9fX0=");
        ItemStack day = createItemHead("Dia", "day", "Deixar de dia", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg5MDQyMDgyYmI3YTc2MThiNzg0ZWU3NjA1YTEzNGM1ODgzNGUyMWUzNzRjODg4OTM3MTYxMDU3ZjZjNyJ9fX0=");
        ItemStack night = createItemHead("noite", "night", "Deixar de noite", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdkNjhiYjE0NGUxNTlmZmRiMGJiMmFiZGQ1ODNmZjM4OWFlNzEwNjgyY2E3N2U2NTM1MzkzYWUyMjEzN2EifX19");

        updateWorldGui(worldGui, worldName);

        worldGui.setItem(3, day);
        worldGui.setItem(5, night);
        worldGui.setItem(9, back);
        worldGui.setItem(17, exit);

        ItemStack panelRed = createItem("Nada", "nada", "Nada", Material.GRAY_STAINED_GLASS_PANE);

        for (int slot = 0; slot < worldGui.getSize(); slot++) {
            if (worldGui.getItem(slot) == null) {
                worldGui.setItem(slot, panelRed);
            }
        }

        player.openInventory(worldGui);
    }

    public void updateWorldGui(Inventory worldGui, String worldName) {
        String dnpServiceStatus;
        ItemStack dnpServicePanel;
        if (stringExistsInList(worldsDNPServiceOn, worldName)) {
            dnpServiceStatus = LangManager.onMessage;
            dnpServicePanel = createItem("DNPService", "dnpServiceOff", "Clique para desativar", Material.GREEN_STAINED_GLASS_PANE);
        } else {
            dnpServiceStatus = LangManager.offMessage;
            dnpServicePanel = createItem("DNPService", "dnpServiceOn", "Clique para ativar", Material.RED_STAINED_GLASS_PANE);
        }
        ItemStack worldPvpManuallyPanel;
        if (Bukkit.getWorld(worldName).getPVP()) {
            worldPvpManuallyPanel = createItem("PvP", "pvpManyallyOff", "Clique para desativar", Material.GREEN_STAINED_GLASS_PANE);
        } else {
            worldPvpManuallyPanel = createItem("PvP", "pvpManyallyOn", "Clique para ativar", Material.RED_STAINED_GLASS_PANE);
        }
        String worldPvpStatus;
        if (Bukkit.getWorld(worldName).getPVP()) {
            worldPvpStatus = LangManager.onMessage;
        } else {
            worldPvpStatus = LangManager.offMessage;
        }

        ItemStack worldItem = createItemHead(worldName, null, "DNPService: " + dnpServiceStatus + "|PvP: " + worldPvpStatus, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");

        worldGui.setItem(4, worldItem);
        worldGui.setItem(12, dnpServicePanel);
        worldGui.setItem(14, worldPvpManuallyPanel);
    }


    public ItemStack createItemHead(String name, String id, String description, String url) {
        ItemStack item = GetHead.getHead(url);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        itemMeta.setLocalizedName(id);
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack createItem(String name, String id, String description, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        itemMeta.setLocalizedName(id);
        item.setItemMeta(itemMeta);
        return item;
    }

}
