package me.needkg.daynightpvp.command.manager;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.command.impl.DayNightPvpCommand;
import me.needkg.daynightpvp.util.logging.Logger;

public class CommandManager {

    public void register() {
        DayNightPvpCommand dayNightPvPCommand = new DayNightPvpCommand();

        Logger.verbose("Registering DayNightPvP command...");
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dayNightPvPCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dayNightPvPCommand);
    }
} 