package org.callvdois.daynightpvp.commands;

import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.commands.dnp.DnpCommand;

public class CommandHandler {

    public void register() {
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(new DnpCommand());
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(new DnpCommand());
    }
}
