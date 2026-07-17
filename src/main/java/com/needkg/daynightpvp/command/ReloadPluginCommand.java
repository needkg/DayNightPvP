package com.needkg.daynightpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import com.needkg.daynightpvp.ApplicationLifecycle;

public class ReloadPluginCommand extends AbstractCommand {

    private final ApplicationLifecycle applicationLifecycle;
    private final ReloadPluginCommand.Language language;

    public ReloadPluginCommand(
            ApplicationLifecycle applicationLifecycle,
            ReloadPluginCommand.Language language) {
        this.applicationLifecycle = applicationLifecycle;
        this.language = language;
    }

    private static final String NAME = "daynightpvp reload";

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String @NotNull [] args) {

        applicationLifecycle.reload();
        sender.sendMessage(language.success());

        return true;
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    protected void setExecutor(PluginCommand command) {
        command.setExecutor(this);
    }

    public record Language(String success) {
    }

}
