package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.config.StartupFiles;
import com.needkg.daynightpvp.gui.*;
import com.needkg.daynightpvp.placeholder.RegisterPlaceHolder;
import com.needkg.daynightpvp.utils.ItemUtils;
import com.needkg.daynightpvp.utils.PlayerInteract;
import com.needkg.daynightpvp.utils.SearchUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class InventoryEvent implements Listener {

    private final GuiManager guiManager;
    private final FilesManager filesManager;
    private final ConfigManager configManager;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    private final LangGui langGui;
    private final MainGui mainGui;
    private final WorldGui worldGui;
    private final WorldsGui worldsGui;
    private final SearchUtils searchUtils;
    private final LangManager langManager;
    private final ItemUtils itemUtils;

    public InventoryEvent() {
        guiManager = new GuiManager();
        filesManager = new FilesManager();
        configManager = new ConfigManager();
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        langGui = new LangGui();
        mainGui = new MainGui();
        worldGui = new WorldGui();
        worldsGui = new WorldsGui();
        searchUtils = new SearchUtils();
        langManager = new LangManager();
        itemUtils = new ItemUtils();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        ItemStack clickedItem = event.getCurrentItem();
        if ((title.equals(GuiManager.guiTitle) || title.equals(GuiManager.guiWorldTitle) || title.equals(GuiManager.guiWorldsTitle)) && clickedItem != null) {
            event.setCancelled(true);
            ItemMeta itemMeta = clickedItem.getItemMeta();
            String itemID = itemMeta.getLocalizedName();
            if (itemID.equals("")) {
                itemID = "null";
            }
            Player player = (Player) event.getWhoClicked();
            String worldName = event.getInventory().getItem(4).getItemMeta().getDisplayName();

            switch (itemID) {
                case "worlds":
                case "backToWorlds":
                    worldsGui.open(player);
                    updateWorldsGUI(WorldsGui.worldsGui);
                    break;
                case "reload":
                    filesManager.reloadPlugin(DayNightPvP.plugin);
                    player.sendMessage(LangManager.reloadedConfig);
                    registerEvents.register();
                    registerPlaceHolder.register();
                    break;
                case "langSelector":
                    langGui.open(player);
                    break;
                case "back":
                    mainGui.open(player);
                    break;
                case "exit":
                    player.closeInventory();
                    break;
                case "day":
                    Bukkit.getWorld(worldName).setTime(1000);
                    PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    break;
                case "night":
                    Bukkit.getWorld(worldName).setTime(ConfigManager.dayEnd+20);
                    PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    break;
                case "dnpServiceOn":
                    addWorld(worldName);
                    PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    GuiManager.worldsDNPServiceOn.add(worldName);
                    break;
                case "dnpServiceOff":
                    removeWorld(worldName);
                    PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    GuiManager.worldsDNPServiceOn.remove(worldName);
                    break;
                case "pvpManyallyOff":
                    PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    setPvP(worldName, false);
                    break;
                case "pvpManyallyOn":
                    PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                    setPvP(worldName, true);
                    break;
            }
            if (searchUtils.stringInList(StartupFiles.langFiles, "lang/" + itemID + ".yml")) {
                PlayerInteract.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
                ConfigManager.configFileConfig.set("lang", itemID);
                configManager.saveConfig();
                langManager.selectLangFile(DayNightPvP.plugin);
                filesManager.reloadPlugin(DayNightPvP.plugin);
                player.sendMessage(LangManager.langSelected.replace("{0}", itemID));
                mainGui.open(player);
            }
            if (searchUtils.worldExistsInList(Bukkit.getWorlds(), itemID)) {
                worldGui.open(player, itemID);
                updateWorldGUI(itemID, WorldGui.worldGui);
            }
        }
    }


    private BukkitTask updateWorldGUITask;
    private void updateWorldGUI(String world, Inventory inventory) {
        updateWorldGUITask = new BukkitRunnable() {
            @Override
            public void run() {
                guiManager.updateWorldGui(inventory, world);
            }
        }.runTaskTimer(DayNightPvP.plugin, 0L, 20L);
    }

    private BukkitTask updateWorldsGUITask;
    private void updateWorldsGUI(Inventory inventory) {
        updateWorldsGUITask = new BukkitRunnable() {
            @Override
            public void run() {
                guiManager.updateWorldsGui(inventory);
            }
        }.runTaskTimer(DayNightPvP.plugin, 0L, 20L);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        if (title.equals(GuiManager.guiWorldTitle)) {
            updateWorldGUITask.cancel();
        }
        if (title.equals(GuiManager.guiWorldsTitle)) {
            updateWorldsGUITask.cancel();
        }
    }

    private void setPvP(String worldName, boolean pvp) {
        GuiManager.worldsPvpManually.add(worldName);
        Bukkit.getWorld(worldName).setPVP(pvp);
        GuiManager.worldsDNPServiceOn.removeAll(GuiManager.worldsPvpManually);
        setWorlds(GuiManager.worldsDNPServiceOn);
        guiManager.updateWorldGui(WorldGui.worldGui, worldName);
    }

    private void addWorld(String world) {
        List<String> worldList = ConfigManager.configFileConfig.getStringList("worlds");
        worldList.add(world);
        ConfigManager.configFileConfig.set("worlds", worldList);
        configManager.saveConfig();
        filesManager.reloadPlugin(DayNightPvP.plugin);
    }

    private void removeWorld(String world) {
        List<String> worldList = ConfigManager.configFileConfig.getStringList("worlds");
        worldList.remove(world);
        ConfigManager.configFileConfig.set("worlds", worldList);
        configManager.saveConfig();
        filesManager.reloadPlugin(DayNightPvP.plugin);
    }

    private void setWorlds(List<String> list) {
        ConfigManager.configFileConfig.set("worlds", list);
        configManager.saveConfig();
        filesManager.reloadPlugin(DayNightPvP.plugin);
    }

}
