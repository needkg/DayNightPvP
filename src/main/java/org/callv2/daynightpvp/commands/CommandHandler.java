package org.callv2.daynightpvp.commands;

import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;

public class CommandHandler {

    private final LangFile langFile;
    private final ConfigFile configFile;
    private final RunnableHandler runnableHandler;
    private final LoseMoneyOnDeath loseMoneyOnDeath;

    public CommandHandler(LangFile langFile, ConfigFile configFile, RunnableHandler runnableHandler, LoseMoneyOnDeath loseMoneyOnDeath) {
        this.langFile = langFile;
        this.configFile = configFile;
        this.runnableHandler = runnableHandler;
        this.loseMoneyOnDeath = loseMoneyOnDeath;
    }

    public void register() {

        DnpCommand dnpCommand = new DnpCommand(langFile, configFile, runnableHandler, loseMoneyOnDeath);

        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dnpCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dnpCommand);
    }
}
