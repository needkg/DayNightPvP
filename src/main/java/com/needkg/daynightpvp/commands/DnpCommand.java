package com.needkg.daynightpvp.commands;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.gui.MainGui;
import com.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DnpCommand implements CommandExecutor {

    private final MainGui mainGui;

    public DnpCommand() {
        mainGui = new MainGui();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (sender.hasPermission("dnp.admin")) {
                mainGui.open(player);
                return true;
            } else {
                PlayerUtils.sendMessageToPlayer(player, "§8[§e☀§8] §x§2§4§f§f§0§0D§x§2§0§f§f§1§8a§x§1§d§f§f§3§0y§x§1§9§f§e§4§7N§x§1§6§f§e§5§fi§x§1§2§f§e§7§7g§x§0§e§f§e§8§fh§x§0§b§f§e§a§7t§x§0§7§f§d§b§eP§x§0§4§f§d§d§6v§x§0§0§f§d§e§eP §8- §7v" + DayNightPvP.plugin.getDescription().getVersion());
                return false;
            }
        } else {
            sender.sendMessage("This command can only be executed by players.");
            return false;
        }
    }

}
