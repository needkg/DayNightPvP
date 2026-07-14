package me.needkg.daynightpvp.feature.command;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.command.executors.MainCommand;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;

public class CommandInitializer implements Initializable {

    private final JavaPlugin plugin;

    public CommandInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        MainCommand mainCommand = new MainCommand();
        PluginCommand command = plugin.getCommand(mainCommand.name());
        command.setExecutor(mainCommand);
    }

}
