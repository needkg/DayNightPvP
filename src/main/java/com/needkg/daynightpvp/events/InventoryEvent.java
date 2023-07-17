package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.config.StartupFiles;
import com.needkg.daynightpvp.gui.GuiManager;
import com.needkg.daynightpvp.placeholder.RegisterPlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryEvent implements Listener {

    private final GuiManager guiManager;
    private final FilesManager filesManager;
    private final ConfigManager configManager;
    private final LangManager langManager;
    private final RegisterEvents registerEvents;
    private final RegisterPlaceHolder registerPlaceHolder;
    private final StartupFiles startupFiles;

    public InventoryEvent() {
        guiManager = new GuiManager();
        filesManager = new FilesManager();
        configManager = new ConfigManager();
        langManager = new LangManager();
        registerEvents = new RegisterEvents();
        registerPlaceHolder = new RegisterPlaceHolder();
        startupFiles = new StartupFiles();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        ItemStack clickedItem = event.getCurrentItem();
        if (title.equals(GuiManager.guiTitle) && clickedItem != null) {
            event.setCancelled(true);
            ItemMeta itemMeta = clickedItem.getItemMeta();
            String itemID = itemMeta.getLocalizedName();
            Player player = (Player) event.getWhoClicked();

            switch (itemID) {
                case "worlds":
                case "backToWorlds":
                    guiManager.worldsGui(player);
                    break;
                case "reload":
                    filesManager.reloadAllFiles(DayNightPvP.plugin);
                    player.sendMessage(LangManager.reloadedConfig);
                    registerEvents.register();
                    registerPlaceHolder.register();
                    break;
                case "langSelector":
                    guiManager.langGui(player);
                    break;
                case "back":
                    guiManager.mainGui(player);
                    break;
                case "exit":
                    player.closeInventory();
                    break;
                case "day":
                    String worldName5 = event.getInventory().getItem(4).getItemMeta().getDisplayName();
                    Bukkit.getWorld(worldName5).setTime(1000);
                    guiManager.updateWorldGui(guiManager.worldGui, worldName5);
                    break;
                case "night":
                    String worldName6 = event.getInventory().getItem(4).getItemMeta().getDisplayName();
                    Bukkit.getWorld(worldName6).setTime(ConfigManager.dayEnd+20);
                    guiManager.updateWorldGui(guiManager.worldGui, worldName6);
                    break;
                case "dnpServiceOn":
                    String worldName = event.getInventory().getItem(4).getItemMeta().getDisplayName();
                    addWorld(worldName);
                    GuiManager.worldsDNPServiceOn.add(worldName);
                    guiManager.updateWorldGui(guiManager.worldGui, worldName);
                    break;
                case "dnpServiceOff":
                    String worldName2 = event.getInventory().getItem(4).getItemMeta().getDisplayName();
                    removeWorld(worldName2);
                    GuiManager.worldsDNPServiceOn.remove(worldName2);
                    guiManager.updateWorldGui(guiManager.worldGui, worldName2);
                    break;
                case "pvpManyallyOff":
                    String worldName3 = event.getInventory().getItem(4).getItemMeta().getDisplayName();
                    GuiManager.worldsPvpManually.add(worldName3);
                    Bukkit.getWorld(worldName3).setPVP(false);
                    GuiManager.worldsDNPServiceOn.removeAll(GuiManager.worldsPvpManually);
                    setWorlds(GuiManager.worldsDNPServiceOn);
                    guiManager.updateWorldGui(guiManager.worldGui, worldName3);
                    break;
                case "pvpManyallyOn":
                    String worldName4 = event.getInventory().getItem(4).getItemMeta().getDisplayName();
                    GuiManager.worldsPvpManually.add(worldName4);
                    Bukkit.getWorld(worldName4).setPVP(true);
                    GuiManager.worldsDNPServiceOn.removeAll(GuiManager.worldsPvpManually);
                    setWorlds(GuiManager.worldsDNPServiceOn);
                    guiManager.updateWorldGui(guiManager.worldGui, worldName4);
                    break;
                default:
                    if (stringExistsInList(StartupFiles.langFiles, itemID)) {
                        ConfigManager.configFileConfig.set("lang", itemID);
                        configManager.saveConfig();
                        langManager.selectLangFile(DayNightPvP.plugin);
                        filesManager.reloadAllFiles(DayNightPvP.plugin);
                        player.sendMessage(LangManager.langSelected.replace("{0}", itemID));
                    }
                    if (worldExistsInList(Bukkit.getWorlds(), itemID)) {
                        guiManager.worldGui(player, itemID);
                    }
                    break;
            }
        }
    }

    public boolean stringExistsInList(List<String> list, String searchString) {
        return list.stream().anyMatch(element -> element.contains(searchString));
    }

    public boolean worldExistsInList(List<World> list, String searchString) {
        return list.stream().map(World::getName).anyMatch(name -> name.contains(searchString));
    }

    public void addWorld(String world) {
        List<String> worldList = ConfigManager.configFileConfig.getStringList("worlds");
        worldList.add(world);
        ConfigManager.configFileConfig.set("worlds", worldList);
        configManager.saveConfig();
        filesManager.reloadAllFiles(DayNightPvP.plugin);
    }

    public void removeWorld(String world) {
        List<String> worldList = ConfigManager.configFileConfig.getStringList("worlds");
        worldList.remove(world);
        ConfigManager.configFileConfig.set("worlds", worldList);
        configManager.saveConfig();
        filesManager.reloadAllFiles(DayNightPvP.plugin);
    }

    public void setWorlds(List<String> list) {
        ConfigManager.configFileConfig.set("worlds", list);
        configManager.saveConfig();
        filesManager.reloadAllFiles(DayNightPvP.plugin);
    }

}
