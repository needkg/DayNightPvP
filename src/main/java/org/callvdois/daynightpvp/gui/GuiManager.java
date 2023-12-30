package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.service.TimeChecker;
import org.callvdois.daynightpvp.utils.ItemUtils;
import org.callvdois.daynightpvp.utils.SearchUtils;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    public static String guiTitle;
    public static String guiWorldTitle;
    public static String guiWorldsTitle;
    public static BukkitTask updateWorldGUITask;
    public static List<String> worldsPvpManually = new ArrayList<>();
    public static List<String> worldsDNPServiceOn = new ArrayList<>();
    public static BukkitTask updateWorldsGUITask;
    private ItemStack dnpServicePanel;
    private ItemStack worldPvpManuallyPanel;
    private ItemStack worldItem;

    public GuiManager() {
        guiTitle = "§c§l» DayNightPvP";
        guiWorldTitle = "§c§l» DayNightPvP (World)";
        guiWorldsTitle = "§c§l» DayNightPvP (Worlds)";

        dnpServicePanel = new ItemStack(Material.STONE);
        worldPvpManuallyPanel = new ItemStack(Material.STONE);
        worldItem = new ItemStack(Material.STONE);
    }

    public void updateWorldGUI(World world, Inventory inventory) {

        updateWorldGUITask = new BukkitRunnable() {
            @Override
            public void run() {
                updateWorldGui(inventory, world);
            }
        }.runTaskTimer(DayNightPvP.getInstance(), 0L, 20L);
    }

    public void updateWorldsGUI(Inventory inventory) {
        updateWorldsGUITask = new BukkitRunnable() {
            @Override
            public void run() {
                updateWorldsGui(inventory);
            }
        }.runTaskTimer(DayNightPvP.getInstance(), 0L, 20L);
    }

    public void updateWorldGui(Inventory worldGui, World world) {

        String worldName = world.getName();
        String dnpServiceStatus;
        String worldPvpStatus;

        if (SearchUtils.stringInList(worldsDNPServiceOn, worldName)) {
            dnpServiceStatus = LangManager.onMessage;
            dnpServicePanel = ItemUtils.createItem(LangManager.settingsButton1, "dnpServiceOff", LangManager.settingsButtonDescription1, Material.GREEN_STAINED_GLASS_PANE);
        } else {
            dnpServiceStatus = LangManager.offMessage;
            dnpServicePanel = ItemUtils.createItem(LangManager.settingsButton1, "dnpServiceOn", LangManager.settingsButtonDescription2, Material.RED_STAINED_GLASS_PANE);
        }
        if (TimeChecker.worldsPvpOn.contains(world)) {
            worldPvpStatus = "night";
            worldPvpManuallyPanel = ItemUtils.createItem(LangManager.settingsButton2, "pvpManyallyOff", LangManager.settingsButtonDescription1, Material.GREEN_STAINED_GLASS_PANE);
        } else {
            worldPvpStatus = "day";
            worldPvpManuallyPanel = ItemUtils.createItem(LangManager.settingsButton2, "pvpManyallyOn", LangManager.settingsButtonDescription2, Material.RED_STAINED_GLASS_PANE);
        }
        if ((world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END)) {
            worldItem = ItemUtils.createCustomHead(worldName, worldName, LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + "§3§l» §7Status: §b" + worldPvpStatus, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
            worldGui.setItem(13, worldPvpManuallyPanel);
        } else {
            worldItem = ItemUtils.createCustomHead(worldName, worldName, LangManager.worldButtonDescriptionLine1.replace("{0}", dnpServiceStatus) + "|" + "§3§l» §7Status: §b" + worldPvpStatus, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
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
            worldsDNPServiceOn = ConfigManager.worldList;
            if (SearchUtils.stringInList(worldsDNPServiceOn, worldName)) {
                dnpServiceStatus = LangManager.onMessage;
            } else {
                dnpServiceStatus = LangManager.offMessage;
            }
            if (TimeChecker.worldsPvpOn.contains(world)) {
                pvpStatus = "night";
            } else {
                pvpStatus = "day";
            }
            String buttonDescription;
            ItemStack worldItem;
            if (world.getEnvironment() == World.Environment.NETHER) {
                buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + "§3§l» §7Status: §b" + pvpStatus + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}", ChatColor.AQUA + "nether");
                worldItem = ItemUtils.createCustomHead(worldName, worldName, buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzkzYmZjNDMxOTAwNzIzZjdmYTI4Nzg2NDk2MzgwMTdjZTYxNWQ4ZDhjYWI4ZDJmMDcwYTYxZWIxYWEwMGQwMiJ9fX0=");
            } else if (world.getEnvironment() == World.Environment.THE_END) {
                buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + "§3§l» §7Status: §b" + pvpStatus + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}", ChatColor.AQUA + "the_end");
                worldItem = ItemUtils.createCustomHead(worldName, worldName, buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlmMjFmNWQ4ODMzMTZmZDY1YTkzNjZmMzJhMzMwMTMxODJlMzM4MWRlYzIxYzE3Yzc4MzU1ZDliZjRmMCJ9fX0=");
            } else {
                buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", dnpServiceStatus) + "|" + "§3§l» §7Status: §b" + pvpStatus + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}", ChatColor.AQUA + "normal");
                worldItem = ItemUtils.createCustomHead(worldName, worldName, buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
            }

            inventory.setItem(position, worldItem);
            position++;
        }

        ItemStack panel = ItemUtils.createItem(ChatColor.RED + "###", "nada", " ", Material.GRAY_STAINED_GLASS_PANE);

        for (int slot = 0; slot < WorldsGui.worldsGui.getSize(); slot++) {
            if (WorldsGui.worldsGui.getItem(slot) == null) {
                WorldsGui.worldsGui.setItem(slot, panel);
            }
        }
    }

}
