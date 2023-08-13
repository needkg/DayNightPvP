package com.needkg.daynightpvp.commands;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.gui.MainGui;
import com.needkg.daynightpvp.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DnpCommand implements CommandExecutor {

    private final MainGui mainGui;
    private final FilesManager filesManager;

    public DnpCommand() {
        mainGui = new MainGui();
        filesManager = new FilesManager();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (sender.hasPermission("dnp.admin")) {
                if (args.length == 0) {
                    mainGui.open(player);
                }
                if (args.length == 1) {
                    if (args[0].equals("reload")) {
                        filesManager.reloadPlugin(DayNightPvP.plugin);
                        PlayerUtils.sendMessageToPlayer(player, LangManager.reloadedConfig);
                    }
                }
                return true;
            } else {
                PlayerUtils.sendMessageToPlayer(player, "§8[§e☀§8] §9DayNightPvP §8- §7v" + DayNightPvP.plugin.getDescription().getVersion());
                return false;
            }
        } else {
            sender.sendMessage("This command can only be executed by players.");
            return false;
        }
    }

}
