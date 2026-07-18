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

public class ReloadPluginCommand extends AbstractCommand implements PluginReloadEventHandler {

    private static final String NAME = "daynightpvp";

    private final ApplicationLifecycle applicationLifecycle;
    private final ReloadPluginCommand.Language language;

    public ReloadPluginCommand(ReloadPluginCommand.Language language, ApplicationLifecycle applicationLifecycle) {
        super(NAME);
        this.language = language;
        this.applicationLifecycle = applicationLifecycle;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args) {

        applicationLifecycle.reload();
        Bukkit.getPluginManager().callEvent(new PluginReloadEvent(sender));

        return true;
    }

    @Override
    public void handle(PluginReloadEvent event) {
        event.getSender().sendMessage(language.success());
    }

    public record Language(String success) {
    }

}
