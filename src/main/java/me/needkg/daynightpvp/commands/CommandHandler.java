package me.needkg.daynightpvp.commands;

import me.needkg.daynightpvp.DayNightPvP;

public class CommandHandler {

    public void register() {
        DnpCommand dnpCommand = new DnpCommand();
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dnpCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dnpCommand);
    }
}
