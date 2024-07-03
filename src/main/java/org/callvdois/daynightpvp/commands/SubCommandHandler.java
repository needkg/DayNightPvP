package org.callvdois.daynightpvp.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommandHandler {

    public abstract void execute(CommandSender sender, String[] args);
}