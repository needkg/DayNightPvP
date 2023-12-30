package org.callvdois.daynightpvp.commands;

import org.callvdois.daynightpvp.DayNightPvP;

public class RegisterCommand {
    public void register() {

        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(new DnpCommand());
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(new DnpTabCompleter());
    }
}
