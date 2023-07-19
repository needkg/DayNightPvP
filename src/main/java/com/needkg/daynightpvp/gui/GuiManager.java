package com.needkg.daynightpvp.gui;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.ItemUtils;
import com.needkg.daynightpvp.utils.SearchUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    private ItemUtils itemUtils;
    private SearchUtils searchUtils;
    public static String guiTitle;
    public static String guiWorldTitle;
    public static String guiWorldsTitle;
    public static List<String> worldsPvpManually = new ArrayList<>();
    public static List<String> worldsDNPServiceOn = new ArrayList<>();

    public GuiManager() {
        itemUtils = new ItemUtils();
        searchUtils = new SearchUtils();
        guiTitle = "§#8f00ff» §#8800ffD§#8100ffa§#7a00ffy§#7300ffN§#6c00ffi§#6601ffg§#5f01ffh§#5801fft§#5101ffP§#4a01ffv§#4301ffP";
        guiWorldTitle = "§#8f00ff» §#8a00ffD§#8500ffa§#8000ffy§#7b00ffN§#7500ffi§#7000ffg§#6b00ffh§#6600fft§#6100ffP§#5c00ffv§#5700ffP §#5200ff(§#4d00ffW§#4700ffo§#4200ffr§#3d00ffl§#3800ffd§#3300ff)";
        guiWorldsTitle = "§#8f00ff» §#8a00ffD§#8500ffa§#8000ffy§#7c00ffN§#7700ffi§#7200ffg§#6d00ffh§#6800fft§#6300ffP§#5f00ffv§#5a00ffP §#5500ff(§#5000ffW§#4b00ffo§#4600ffr§#4200ffl§#3d00ffd§#3800ffs§#3300ff)";
    }

    public void updateWorldGui(Inventory worldGui, String worldName) {
        String dnpServiceStatus;
        ItemStack dnpServicePanel;
        if (searchUtils.stringInList(worldsDNPServiceOn, worldName)) {
            dnpServiceStatus = LangManager.onMessage;
            dnpServicePanel = itemUtils.createItem(LangManager.settingsButton1, "dnpServiceOff", LangManager.settingsButtonDescription1, Material.GREEN_STAINED_GLASS_PANE);
        } else {
            dnpServiceStatus = LangManager.offMessage;
            dnpServicePanel = itemUtils.createItem(LangManager.settingsButton1, "dnpServiceOn", LangManager.settingsButtonDescription2, Material.RED_STAINED_GLASS_PANE);
        }
        ItemStack worldPvpManuallyPanel;
        if (Bukkit.getWorld(worldName).getPVP()) {
            worldPvpManuallyPanel = itemUtils.createItem(LangManager.settingsButton2, "pvpManyallyOff", LangManager.settingsButtonDescription1, Material.GREEN_STAINED_GLASS_PANE);
        } else {
            worldPvpManuallyPanel = itemUtils.createItem(LangManager.settingsButton2, "pvpManyallyOn", LangManager.settingsButtonDescription2, Material.RED_STAINED_GLASS_PANE);
        }
        String worldPvpStatus;
        if (Bukkit.getWorld(worldName).getPVP()) {
            worldPvpStatus = LangManager.onMessage;
        } else {
            worldPvpStatus = LangManager.offMessage;
        }
        World world = Bukkit.getWorld(worldName);
        ItemStack worldItem;
        if ((world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END)) {
            worldItem = itemUtils.createCustomHead(worldName, worldName, LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + LangManager.worldButtonDescriptionLine2.replace("{0}", worldPvpStatus), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
            worldGui.setItem(13, worldPvpManuallyPanel);
        } else {
            worldItem = itemUtils.createCustomHead(worldName, worldName, LangManager.worldButtonDescriptionLine1.replace("{0}", dnpServiceStatus) + "|" + LangManager.worldButtonDescriptionLine2.replace("{0}", worldPvpStatus), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
            worldGui.setItem(12, dnpServicePanel);
            worldGui.setItem(14, worldPvpManuallyPanel);
        }

        worldGui.setItem(4, worldItem);
    }

    public void updateWorldsGui(Inventory inventory) {
        List<World> worldList = Bukkit.getServer().getWorlds();

        int position = 0;
        for (World world : worldList) {
            String worldName = world.getName();
            String dnpServiceStatus;
            String pvpStatus;
            GuiManager.worldsDNPServiceOn = ConfigManager.worldList;
            if (searchUtils.stringInList(GuiManager.worldsDNPServiceOn, worldName)) {
                dnpServiceStatus = LangManager.onMessage;
            } else {
                dnpServiceStatus = LangManager.offMessage;
            }
            if (world.getPVP()) {
                pvpStatus = LangManager.onMessage;
            } else {
                pvpStatus = LangManager.offMessage;
            }
            String buttonDescription;
            ItemStack worldItem;
            if (world.getEnvironment() == World.Environment.NETHER) {
                buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + LangManager.worldButtonDescriptionLine2.replace("{0}", pvpStatus) + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}",ChatColor.AQUA + "nether");
                worldItem = itemUtils.createCustomHead(worldName, worldName, buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzkzYmZjNDMxOTAwNzIzZjdmYTI4Nzg2NDk2MzgwMTdjZTYxNWQ4ZDhjYWI4ZDJmMDcwYTYxZWIxYWEwMGQwMiJ9fX0=");
            } else if (world.getEnvironment() == World.Environment.THE_END) {
                buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + LangManager.worldButtonDescriptionLine2.replace("{0}", pvpStatus) + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}",ChatColor.AQUA + "the_end");
                worldItem = itemUtils.createCustomHead(worldName, worldName, buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlmMjFmNWQ4ODMzMTZmZDY1YTkzNjZmMzJhMzMwMTMxODJlMzM4MWRlYzIxYzE3Yzc4MzU1ZDliZjRmMCJ9fX0=");
            } else {
                buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", dnpServiceStatus) + "|" + LangManager.worldButtonDescriptionLine2.replace("{0}", pvpStatus + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}",ChatColor.AQUA + "normal"));
                worldItem = itemUtils.createCustomHead(worldName, worldName, buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
            }

            inventory.setItem(position, worldItem);
            position++;
        }

        ItemStack panel = itemUtils.createItem(ChatColor.RED +"###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);

        for (int slot = 0; slot < WorldsGui.worldsGui.getSize(); slot++) {
            if (WorldsGui.worldsGui.getItem(slot) == null) {
                WorldsGui.worldsGui.setItem(slot, panel);
            }
        }
    }

}
