package org.callv2.daynightpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;

public class ListenersHandler {

    private final ConfigFile configFile;
    private final LangFile langFile;
    private final LoseMoneyOnDeath loseMoneyOnDeath;

    public ListenersHandler(ConfigFile configFile, LangFile langFile, LoseMoneyOnDeath loseMoneyOnDeath) {
        this.configFile = configFile;
        this.langFile = langFile;
        this.loseMoneyOnDeath = loseMoneyOnDeath;
    }

    public void register() {
        registerJoinListener();
        registerEntityListener();
        registerDeathListener();
    }

    public void unregisterAll() {
        HandlerList.unregisterAll(DayNightPvP.getInstance());
    }

    private void registerJoinListener() {
        if (configFile.getUpdateChecker()) {
            Bukkit.getPluginManager().registerEvents(new JoinListener(configFile), DayNightPvP.getInstance());
        }
    }

    private void registerEntityListener() {
        Bukkit.getPluginManager().registerEvents(new DamageListener(configFile, langFile), DayNightPvP.getInstance());
    }

    private void registerDeathListener() {
        boolean vaultLoseMoneyOnDeath = configFile.getVaultLoseMoneyOnDeathEnabled();
        boolean keepInventoryWhenKilledByPlayer = configFile.getPvpKeepInventoryWhenKilledByPlayer();

        if (vaultLoseMoneyOnDeath || keepInventoryWhenKilledByPlayer) {
            Bukkit.getPluginManager().registerEvents(new DeathListener(configFile, loseMoneyOnDeath), DayNightPvP.getInstance());
        }
    }

}
