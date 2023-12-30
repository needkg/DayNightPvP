package org.callvdois.daynightpvp.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.ItemUtils;
import org.callvdois.daynightpvp.utils.SearchUtils;

import java.util.List;

public class WorldsGui {

    public static BukkitTask task;
    private Inventory inventory;
    public static String inventoryTitle;

    public WorldsGui() {
        inventoryTitle = "§c§l» DayNightPvP (Worlds)";
    }

    public void open(Player player) {
        inventory = Bukkit.createInventory(player, 27, inventoryTitle);

        ItemStack backButton = ItemUtils.createCustomHead(LangManager.backButton, "backButton", LangManager.backButtonDescription1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        ItemStack exitButton = ItemUtils.createCustomHead(LangManager.exitButton, "exitButton", LangManager.exitButtonDescription, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");

        inventory.setItem(25, backButton);
        inventory.setItem(26, exitButton);

        player.openInventory(inventory);
        updateTask();
    }

    public void updateTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                refreshGui(inventory);
            }
        }.runTaskTimer(DayNightPvP.getInstance(), 0L, 20L);
    }

    public String verifyAutomaticPvpStatus(List<String> list, String worldName) {
        if (SearchUtils.stringExistInList(list, worldName)) {
            return LangManager.onMessage;
        } else {
            return LangManager.offMessage;
        }
    }

    public String verifyTimeOnWorld(long time) {
        if (time > ConfigManager.autoPvpDayEnd) {
            return "night"; // adicionar no messages
        } else {
            return "day"; // adicionar no messages
        }
    }

    public ItemStack defineWorldItem(World world, String automaticPvpStatus, String timeStatus) {
        // adicionar no messages
        // tudo abaixo
        if (world.getEnvironment() == World.Environment.NETHER) {
            String buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}", ChatColor.AQUA + "nether");
            return ItemUtils.createCustomHead(world.getName(), world.getName(), buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzkzYmZjNDMxOTAwNzIzZjdmYTI4Nzg2NDk2MzgwMTdjZTYxNWQ4ZDhjYWI4ZDJmMDcwYTYxZWIxYWEwMGQwMiJ9fX0=");
        } else if (world.getEnvironment() == World.Environment.THE_END) {
            String buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", LangManager.worldButtonDescriptionNotSupported) + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}", ChatColor.AQUA + "nether");
            return ItemUtils.createCustomHead(world.getName(), world.getName(), buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlmMjFmNWQ4ODMzMTZmZDY1YTkzNjZmMzJhMzMwMTMxODJlMzM4MWRlYzIxYzE3Yzc4MzU1ZDliZjRmMCJ9fX0=");
        } else {
            String buttonDescription = LangManager.worldButtonDescriptionLine1.replace("{0}", automaticPvpStatus) + "|" + "§3§l» §7Status: §b" + timeStatus + "|" + LangManager.worldButtonDescriptionWorldType.replace("{0}", ChatColor.AQUA + "normal");
            return ItemUtils.createCustomHead(world.getName(), world.getName(), buttonDescription + "||" + LangManager.worldButtonDescriptionLine3, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
        }
    }

    public void refreshGui(Inventory inventory) {

        for (int i = 0; i <= 24; i++) {
            inventory.clear(i);
        }

        List<World> worldsInServer = Bukkit.getServer().getWorlds();
        int position = 0;

        for (World world : worldsInServer) {
            String worldName = world.getName();
            String automaticPvpStatus = verifyAutomaticPvpStatus(ConfigManager.worldList, worldName);
            String timeStatus = verifyTimeOnWorld(world.getTime());
            ItemStack worldItem = defineWorldItem(world, automaticPvpStatus, timeStatus);

            inventory.setItem(position, worldItem);
            position++;
        }

        ItemStack separatorGlass = ItemUtils.createItem(ChatColor.RED + "###", "nothing", " ", Material.GRAY_STAINED_GLASS_PANE);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, separatorGlass);
            }
        }
    }

}
