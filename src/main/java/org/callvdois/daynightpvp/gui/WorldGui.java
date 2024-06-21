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

public class WorldGui {

    public static BukkitTask task;
    public static String inventoryTitle;
    private final Inventory inventory;
    private final LangManager langManager;
    private final ConfigManager configManager;

    public WorldGui() {
        langManager = new LangManager();
        configManager = new ConfigManager();
        inventoryTitle = "§c§l» DayNightPvP (World)";
        inventory = Bukkit.createInventory(null, 18, inventoryTitle);
    }

    public void open(Player player, World world) {

        ItemStack backButton = ItemUtils.createCustomHead(langManager.getGuiBackButton(), "backToWorldsButton", langManager.getGuiBackButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MWFiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
        ItemStack exitButton = ItemUtils.createCustomHead(langManager.getGuiExitButton(), "exitButton", langManager.getGuiExitButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkxOWQxNTk0YmY4MDlkYjdiNDRiMzc4MmJmOTBhNjlmNDQ5YTg3Y2U1ZDE4Y2I0MGViNjUzZmRlYzI3MjIifX19");
        ItemStack dayButton = ItemUtils.createCustomHead(langManager.getGuiDayButton(), "dayButton", langManager.getGuiDayButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg5MDQyMDgyYmI3YTc2MThiNzg0ZWU3NjA1YTEzNGM1ODgzNGUyMWUzNzRjODg4OTM3MTYxMDU3ZjZjNyJ9fX0=");
        ItemStack nightButton = ItemUtils.createCustomHead(langManager.getGuiNightButton(), "nightButton", langManager.getGuiNightButtonDescription(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDdkNjhiYjE0NGUxNTlmZmRiMGJiMmFiZGQ1ODNmZjM4OWFlNzEwNjgyY2E3N2U2NTM1MzkzYWUyMjEzN2EifX19");
        ItemStack separatorGlass = ItemUtils.createItem(ChatColor.RED + "###", "nothing", " ", Material.GRAY_STAINED_GLASS_PANE);

        if (world.getEnvironment() == World.Environment.NORMAL) {
            inventory.setItem(3, dayButton);
            inventory.setItem(5, nightButton);
        }

        inventory.setItem(9, backButton);
        inventory.setItem(17, exitButton);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, separatorGlass);
            }
        }

        player.openInventory(inventory);
        updateTask(world, inventory);
    }

    public void updateTask(World world, Inventory inventory) {

        task = new BukkitRunnable() {
            @Override
            public void run() {
                refreshGui(inventory, world);
            }
        }.runTaskTimer(DayNightPvP.getInstance(), 0L, 10L);
    }

    public String verifyTimeOnWorld(long time) {
        if (time >= configManager.getDayNightPvpDayEnd()) {
            return langManager.getGuiWorldButtonDescriptionNight();
        } else {
            return langManager.getGuiWorldButtonDescriptionDay();
        }
    }

    public String verifyAutomaticPvpStatus(List<String> list, String worldName) {
        if (SearchUtils.stringExistInList(list, worldName)) {
            return langManager.getStateEnabled();
        } else {
            return langManager.getStateDisabled();
        }
    }

    public ItemStack defineAutomaticPvpPanel(String worldName) {
        if (SearchUtils.stringExistInList(configManager.getDayNightPvpWorlds(), worldName)) {
            return ItemUtils.createItem(langManager.getGuiDayNightPvpButton(), "setAutomaticPvpOff", langManager.getActionButtonClickToDisable(), Material.GREEN_STAINED_GLASS_PANE);
        } else {
            return ItemUtils.createItem(langManager.getGuiDayNightPvpButton(), "setAutomaticPvpOn", langManager.getActionButtonClickToEnable(), Material.RED_STAINED_GLASS_PANE);
        }
    }

    public ItemStack defineWorldItem(World world, String automaticPvpStatus, String timeStatus) {
        if (world.getEnvironment() == World.Environment.NORMAL) {
            return ItemUtils.createCustomHead(world.getName(), world.getName(), langManager.getGuiWorldButtonDescriptionDayNightPvp().replace("{0}", automaticPvpStatus) + "|" + "§3§l» §7Status: §b" + timeStatus, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
        } else {
            return ItemUtils.createCustomHead(world.getName(), world.getName(), langManager.getGuiWorldButtonDescriptionDayNightPvp().replace("{0}", langManager.getGuiWorldButtonDescriptionNotSupported()) + "|" + "§3§l» §7Status: §b" + timeStatus, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIyODM5ZDVjN2ZjMDY3ODA2MmYxYzZjOGYyN2IzMzIwOTQzODRlM2JiNWM0YjVlYmQxNjc2YjI3OWIwNmJmIn19fQ==");
        }
    }

    public void refreshGui(Inventory inventory, World world) {
        String worldName = world.getName();
        String automaticPvpStatus = verifyAutomaticPvpStatus(configManager.getDayNightPvpWorlds(), worldName);

        String timeStatus = verifyTimeOnWorld(world.getTime());
        ItemStack worldItem = defineWorldItem(world, automaticPvpStatus, timeStatus);

        ItemStack automaticPvpPanel = defineAutomaticPvpPanel(worldName);

        inventory.setItem(13, automaticPvpPanel);
        inventory.setItem(4, worldItem);
    }

}
