package org.callvdois.daynightpvp.commands.dnp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.callvdois.daynightpvp.commands.SubCommandHandler;
import org.callvdois.daynightpvp.commands.dnp.reload.DnpReloadCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DnpCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommandHandler> subCommands = new HashMap<>();

    public DnpCommand() {
        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.put("reload", new DnpReloadCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String commandLabel, String[] args) {

        if (args.length == 0) {

            sender.sendMessage("Mostra todos os comandos.");
            return false;
        }

        SubCommandHandler dnpSubCommand = subCommands.get(args[0]);

        if (dnpSubCommand == null) {
            sender.sendMessage("Subcomando não encontrado!");
            return false;
        }

        dnpSubCommand.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
