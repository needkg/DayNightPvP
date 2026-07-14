package me.needkg.daynightpvp.feature.command.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.needkg.daynightpvp.feature.command.DnpCommand;
import me.needkg.daynightpvp.feature.gui.MenuManager;
import me.needkg.daynightpvp.feature.gui.menus.MainMenu;

public class MainCommand extends DnpCommand {

    private final MenuManager menuManager;
    private final MainMenu mainMenu;

    public MainCommand(MenuManager menuManager, MainMenu mainMenu) {
        this.menuManager = menuManager;
        this.mainMenu = mainMenu;
    }


    @Override
    public String name() {
        return "daynightpvp";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command");
            return false;
        }

        menuManager.openMenu(player, mainMenu);
        return true;
    }
    
}
