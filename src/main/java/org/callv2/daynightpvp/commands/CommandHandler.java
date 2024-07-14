package org.callv2.daynightpvp.commands;

import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.runnables.RunnableHandler;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;

public class CommandHandler {

    private final LangFile langFile;
    private final LoseMoneyOnDeath loseMoneyOnDeath;
    private final RunnableHandler runnableHandler;

    public CommandHandler(LangFile langFile, LoseMoneyOnDeath loseMoneyOnDeath, RunnableHandler runnableHandler) {
        this.langFile = langFile;
        this.loseMoneyOnDeath = loseMoneyOnDeath;
        this.runnableHandler = runnableHandler;
    }

    public void register() {

        DnpCommand dnpCommand = new DnpCommand(langFile, loseMoneyOnDeath, runnableHandler);

        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dnpCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dnpCommand);
    }
}
