package com.needkg.daynightpvp.command;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import com.needkg.daynightpvp.ApplicationLifecycle;
import com.needkg.daynightpvp.event.PluginReloadEvent;
import com.needkg.daynightpvp.event.handler.PluginReloadEventHandler;

public class DaynighPvpCommand extends AbstractCommand {

    private static final String NAME = "daynightpvp";

    private final DaynighPvpCommand.Language language;

    public DaynighPvpCommand(DaynighPvpCommand.Language language) {
        super(NAME);
        this.language = language;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args) {

        /// TODO futura gui

        return true;
    }

    @Override
    protected void setExecutor(PluginCommand command) {
        command.setExecutor(this);
    }

    public record Language() {
    }

}