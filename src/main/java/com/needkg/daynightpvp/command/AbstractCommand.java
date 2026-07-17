package com.needkg.daynightpvp.command;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import com.needkg.daynightpvp.exceptions.CommandHierarchyException;

public abstract class AbstractCommand implements CommandExecutor {

    private final String name;
    private Optional<AbstractCommand> parentCommand;
    private final Set<AbstractCommand> subCommands;

    protected AbstractCommand(String name) {
        this.name = name;
        this.parentCommand = Optional.empty();
        this.subCommands = new HashSet<>();
    }

    public void addSubCommand(AbstractCommand subCommand) {

        if (subCommand.parentCommand.isPresent()) {
            throw new CommandHierarchyException(subCommand, this);
        }

        subCommand.parentCommand = Optional.of(this);
        subCommands.add(subCommand);

    }

    public void register(JavaPlugin plugin) {

        var command = plugin.getCommand(getName());

        setExecutor(command);

        if (command != null) {
            setExecutor(command);
        }

    }

    public String getName() {

        if (parentCommand.isPresent())
            return parentCommand.get().getName() + " " + name;

        return name;
    }

    protected abstract void setExecutor(PluginCommand command);

    // protected abstract void setTabCompleter(PluginCommand command); ///TODO
    // implementar tab completer

}
