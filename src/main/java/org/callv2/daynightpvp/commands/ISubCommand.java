package org.callv2.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface ISubCommand {

    void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);

    default List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        return new ArrayList<>();
    }

}