package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.gui.*;
import com.needkg.daynightpvp.utils.*;
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

public class InventoryEvent implements Listener {

    private final FilesManager filesManager;
    private final LangGui langGui;
    private final MainGui mainGui;
    private final WorldGui worldGui;
    private final WorldsGui worldsGui;
    private final GuiManager guiManager;

    public InventoryEvent() {
        filesManager = new FilesManager();
        langGui = new LangGui();
        mainGui = new MainGui();
        worldGui = new WorldGui();
        worldsGui = new WorldsGui();
        guiManager = new GuiManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        String title = event.getView().getTitle();
        if (title.equals(GuiManager.guiTitle) || title.equals(GuiManager.guiWorldsTitle) || title.equals(GuiManager.guiWorldTitle)) {

            if (clickedItem == null){
                return;
            }

            event.setCancelled(true);
            ItemMeta itemMeta = clickedItem.getItemMeta();
            String itemID = itemMeta.getLocalizedName();



            Player player = (Player) event.getWhoClicked();
            String worldName = event.getInventory().getItem(4).getItemMeta().getDisplayName();

            switch (itemID) {
                case "worlds":
                case "backToWorlds":
                    worldsGui.open(player);
                    guiManager.updateWorldsGUI(WorldsGui.worldsGui);
                    break;
                case "reload":
                    PlayerUtils.sendMessageToPlayer(player, LangManager.reloadedConfig);
                    filesManager.reloadPlugin(DayNightPvP.plugin);
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
                    WorldUtils.setTime(worldName, 1000);
                    PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                    break;
                case "night":
                    WorldUtils.setTime(worldName, ConfigManager.autoPvpDayEnd);
                    PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                    break;
                case "dnpServiceOn":
                    ConfigUtils.addWorldToList(worldName);
                    filesManager.reloadPlugin(DayNightPvP.plugin);
                    PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                    GuiManager.worldsDNPServiceOn.add(worldName);
                    break;
                case "dnpServiceOff":
                    ConfigUtils.removeWorldToList(worldName);
                    filesManager.reloadPlugin(DayNightPvP.plugin);
                    PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                    GuiManager.worldsDNPServiceOn.remove(worldName);
                    break;
                case "pvpManyallyOff":
                    PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                    setPvpManually(worldName, false);
                    break;
                case "pvpManyallyOn":
                    PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                    setPvpManually(worldName, true);
                    break;
            }
            if (SearchUtils.stringInList(FilesManager.langFiles, "lang/" + itemID + ".yml")) {
                PlayerUtils.playSoundToPlayer(player, Sound.BLOCK_NOTE_BLOCK_HAT);
                ConfigUtils.setValue(ConfigManager.configFileConfig, "lang", itemID);
                ConfigUtils.updateConfig();
                LangUtils.selectLangFile(DayNightPvP.plugin);
                filesManager.reloadPlugin(DayNightPvP.plugin);
                player.sendMessage(LangManager.langSelected.replace("{0}", itemID));
                mainGui.open(player);
            }
            if (SearchUtils.worldExistsInList(Bukkit.getWorlds(), itemID)) {
                World world = Bukkit.getWorld(itemID);
                assert world != null;
                worldGui.open(player, world);
                guiManager.updateWorldGUI(world, WorldGui.worldGui);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        if (title.equals(GuiManager.guiWorldTitle)) {
            GuiManager.updateWorldGUITask.cancel();
        }
        if (title.equals(GuiManager.guiWorldsTitle)) {
            GuiManager.updateWorldsGUITask.cancel();
        }
    }

    private void setPvpManually(String worldName, boolean pvp) {
        GuiManager.worldsPvpManually.add(worldName);
        World world = Bukkit.getWorld(worldName);
        assert world != null;
        world.setPVP(pvp);
        GuiManager.worldsDNPServiceOn.removeAll(GuiManager.worldsPvpManually);
        ConfigUtils.setWorlds(GuiManager.worldsDNPServiceOn);
        filesManager.reloadPlugin(DayNightPvP.plugin);
    }

}
