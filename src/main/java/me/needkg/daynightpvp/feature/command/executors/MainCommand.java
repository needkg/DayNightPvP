package me.needkg.daynightpvp.feature.command.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import me.needkg.daynightpvp.feature.command.DnpCommand;

public class MainCommand extends DnpCommand {

    @Override
    public String name() {
        return "daynightpvp";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String @NotNull [] args) {
        throw new UnsupportedOperationException("Unimplemented method 'onCommand'");
    }
    
}
