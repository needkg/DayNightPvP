package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.config.StartupFiles;
import com.needkg.daynightpvp.gui.GuiManager;
import org.apache.commons.codec.language.bm.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class InventoryEvent implements Listener {

    private final GuiManager guiManager;
    private final FilesManager filesManager;
    private final ConfigManager configManager;
    private final LangManager langManager;

    public InventoryEvent() {
        guiManager = new GuiManager();
        filesManager = new FilesManager();
        configManager = new ConfigManager();
        langManager = new LangManager();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory != null && clickedInventory.getType() == InventoryType.CHEST) {
            String title = event.getView().getTitle();
            if (title.equals("§eDayNightPvP §7Gui")) {
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();
                ItemMeta itemMeta = clickedItem.getItemMeta();
                String itemID = itemMeta.getLocalizedName();
                Player player = (Player) event.getWhoClicked();
                if (itemID.equals("langSelector")) {
                    guiManager.langGui(player);
                }
                if (stringExistsInList(StartupFiles.langFiles, itemID)) {
                    ConfigManager.configFileConfig.set("lang", itemID);
                    configManager.saveConfig();
                    langManager.selectLangFile(DayNightPvP.plugin);
                    filesManager.reloadAllFiles(DayNightPvP.plugin);
                    player.sendMessage(LangManager.langSelected.replace("{0}", itemID));
                }
            }
        }
    }

    public boolean stringExistsInList(List<String> list, String searchString) {
        return list.stream().anyMatch(element -> element.contains(searchString));
    }
}
