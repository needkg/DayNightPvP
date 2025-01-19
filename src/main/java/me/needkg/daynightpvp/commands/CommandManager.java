package me.needkg.daynightpvp.commands;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.utils.LoggingUtil;

public class CommandManager {

    public void register() {
        DayNightPvpCommand dayNightPvPCommand = new DayNightPvpCommand();

        LoggingUtil.sendVerboseMessage("Registering DayNightPvP command...");
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dayNightPvPCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dayNightPvPCommand);
    }
}
