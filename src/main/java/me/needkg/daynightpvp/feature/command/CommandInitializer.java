package me.needkg.daynightpvp.feature.command;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.needkg.daynightpvp.feature.command.commands.MainCommand;
import me.needkg.daynightpvp.feature.gui.MenuManager;
import me.needkg.daynightpvp.feature.gui.menus.MainMenu;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;

public class CommandInitializer implements Initializable {

    private final JavaPlugin plugin;
    private final MenuManager menuManager;
    private final MainMenu mainMenu;

    public CommandInitializer(JavaPlugin plugin, MenuManager menuManager, MainMenu mainMenu) {
        this.plugin = plugin;
        this.menuManager = menuManager;
        this.mainMenu = mainMenu;
    }

    @Override
    public void init() {
        MainCommand mainCommand = new MainCommand(menuManager, mainMenu);
        PluginCommand command = plugin.getCommand(mainCommand.name());
        command.setExecutor(mainCommand);
    }

}
