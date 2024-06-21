package org.callvdois.daynightpvp.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.gui.LanguageGui;
import org.callvdois.daynightpvp.gui.MainGui;
import org.callvdois.daynightpvp.gui.WorldGui;
import org.callvdois.daynightpvp.gui.WorldsGui;
import org.callvdois.daynightpvp.utils.ItemUtils;
import org.callvdois.daynightpvp.utils.PlayerUtils;
import org.callvdois.daynightpvp.utils.SearchUtils;
import org.callvdois.daynightpvp.utils.WorldUtils;

import java.io.File;

public class InventoryEvent implements Listener {

    private final FilesManager filesManager;
    private final LanguageGui languageGui;
    private final MainGui mainGui;
    private final WorldGui worldGui;
    private final WorldsGui worldsGui;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public InventoryEvent() {
        filesManager = new FilesManager();
        languageGui = new LanguageGui();
        mainGui = new MainGui();
        worldGui = new WorldGui();
        worldsGui = new WorldsGui();
        configManager = new ConfigManager();
        langManager = new LangManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        String title = event.getView().getTitle();
        if (title.equals(MainGui.inventoryTitle) || title.equals(WorldsGui.inventoryTitle) || title.equals(WorldGui.inventoryTitle)) {

            if (clickedItem == null) {
                return;
            }

            event.setCancelled(true);
            String itemID = ItemUtils.getID(clickedItem);


            Player player = (Player) event.getWhoClicked();
            String worldName = event.getInventory().getItem(4).getItemMeta().getDisplayName();

            switch (itemID) {
                case "worldsButton":
                    worldsGui.open(player);
                    break;
                case "reloadButton":
                    filesManager.reloadPlugin();
                    PlayerUtils.sendMessageToPlayer(player, langManager.getFeedbackReloadPlugin());
                    break;
                case "languageButton":
                    languageGui.open(player);
                    break;
                case "backButton":
                    mainGui.open(player);
                    break;
                case "backToWorldsButton":
                    worldsGui.open(player);
                    break;
                case "exitButton":
                    player.closeInventory();
                    break;
                case "dayButton":
                    WorldUtils.setTime(worldName, 1000);
                    PlayerUtils.playSoundToPlayer(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    break;
                case "nightButton":
                    WorldUtils.setTime(worldName, configManager.getDayNightPvpDayEnd());
                    PlayerUtils.playSoundToPlayer(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    break;
                case "setAutomaticPvpOn":
                    configManager.addWorld(worldName);
                    filesManager.reloadPlugin();
                    PlayerUtils.playSoundToPlayer(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    break;
                case "setAutomaticPvpOff":
                    configManager.removeWorld(worldName);
                    filesManager.reloadPlugin();
                    PlayerUtils.playSoundToPlayer(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    break;
            }
            if (SearchUtils.worldExistsInList(Bukkit.getWorlds(), itemID)) {
                World world = Bukkit.getWorld(itemID);
                if (world != null) {
                    if (world.getEnvironment().equals(World.Environment.NORMAL)) {
                        worldGui.open(player, world);
                    }
                }
            }

            File folder = new File(DayNightPvP.getInstance().getDataFolder() + "/lang");
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            if (SearchUtils.fileExistInListOfFiles(listOfFiles, itemID)) {
                PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);

                configManager.setValue("language", itemID);
                configManager.saveConfig();
                langManager.getLanguageFileSelected();

                player.sendMessage(langManager.getFeedbackSelectLang().replace("{0}", itemID));

                mainGui.open(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        if (title.equals(WorldGui.inventoryTitle)) {
            WorldGui.task.cancel();
        }
        if (title.equals(WorldsGui.inventoryTitle)) {
            WorldsGui.task.cancel();
        }
    }

}
