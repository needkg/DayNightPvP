package com.needkg.daynightpvp.commands;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.gui.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewCommand implements CommandExecutor {

    private final GuiManager guiManager;

    public NewCommand() {
        guiManager = new GuiManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
            return false;
        }
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage("§8[§e☀§8] §aDayNightPvP §8- §7v" + DayNightPvP.plugin.getDescription().getVersion());
            return false;
        }
        Player player = (Player) sender;
        guiManager.mainGui(player);
        return true;
    }

}
