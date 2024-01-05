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
    public static String inventoryTitle;
    private final LangManager langManager;
    private final ConfigManager configManager;
    private Inventory inventory;

    public WorldsGui() {
        inventoryTitle = "§c§l» DayNightPvP (Worlds)";
        langManager = new LangManager();
        configManager = new ConfigManager();
    }

    public void open(Player player) {
        inventory = Bukkit.createInventory(player, 27, inventoryTitle);

        ItemStack backButton = ItemUtils.createCustomHead(langManager.getString("gui-back-button"), "backButton", langManager.getString("gui-back-button-description"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        ItemStack exitButton = ItemUtils.createCustomHead(langManager.getString("gui-exit-button"), "exitButton", langManager.getString("gui-exit-button-description"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");

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
            return langManager.getString("state-enabled");
        } else {
            return langManager.getString("state-disabled");
        }
    }

    public String verifyTimeOnWorld(long time) {
        if (time > configManager.getInt("daynightpvp.day-end")) {
            return langManager.getString("gui-world-button-description-night");
        } else {
            return langManager.getString("gui-world-button-description-day");
        }
    }

    public ItemStack defineWorldItem(World world, String automaticPvpStatus, String timeStatus) {
        if (world.getEnvironment() == World.Environment.NETHER) {
            String buttonDescription = langManager.getString("gui-world-button-description-daynightpvp").replace("{0}", langManager.getString("gui-world-button-description-not-supported")) + "|" + langManager.getString("gui-world-button-description-type").replace("{0}", ChatColor.AQUA + "nether");
            return ItemUtils.createCustomHead(world.getName(), world.getName(), buttonDescription + "||" + langManager.getString("action-button-click-to-see-settings"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzkzYmZjNDMxOTAwNzIzZjdmYTI4Nzg2NDk2MzgwMTdjZTYxNWQ4ZDhjYWI4ZDJmMDcwYTYxZWIxYWEwMGQwMiJ9fX0=");
        } else if (world.getEnvironment() == World.Environment.THE_END) {
            String buttonDescription = langManager.getString("gui-world-button-description-daynightpvp").replace("{0}", langManager.getString("gui-world-button-description-not-supported")) + "|" + langManager.getString("gui-world-button-description-type").replace("{0}", ChatColor.AQUA + "the_end");
            return ItemUtils.createCustomHead(world.getName(), world.getName(), buttonDescription + "||" + langManager.getString("action-button-click-to-see-settings"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTlmMjFmNWQ4ODMzMTZmZDY1YTkzNjZmMzJhMzMwMTMxODJlMzM4MWRlYzIxYzE3Yzc4MzU1ZDliZjRmMCJ9fX0=");
        } else {
            String buttonDescription = langManager.getString("gui-world-button-description-daynightpvp").replace("{0}", automaticPvpStatus) + "|" + langManager.getString("gui-world-button-description-time").replace("{0}", timeStatus) + "|" + langManager.getString("gui-world-button-description-type").replace("{0}", ChatColor.AQUA + "overworld");
            return ItemUtils.createCustomHead(world.getName(), world.getName(), buttonDescription + "||" + langManager.getString("action-button-click-to-see-settings"), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
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
            String automaticPvpStatus = verifyAutomaticPvpStatus(configManager.getList("daynightpvp.worlds"), worldName);
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
