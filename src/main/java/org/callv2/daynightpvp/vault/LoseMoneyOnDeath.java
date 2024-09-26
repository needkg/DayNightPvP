package org.callv2.daynightpvp.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.runnables.AutomaticPvp;

public class LoseMoneyOnDeath {

    private final ConfigFile configFile;
    private final LangFile langFile;

    public LoseMoneyOnDeath(ConfigFile configFile, LangFile langFile) {
        this.configFile = configFile;
        this.langFile = langFile;
    }

    public void loseMoneyOnDeath(Player killed, Player killer, World world, String percentage) {

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getLogger().severe("No Economy provider found! Disabling loseMoneyOnDeath functionality.");
            return;
        }

        Economy economy = rsp.getProvider();

        if (!percentage.isEmpty() && percentage.matches("[1-9][0-9]?|100")) {
            boolean shouldWithdraw = false;
            double currentBalance = economy.getBalance(killed);
            int parsedPercentage = Integer.parseInt(percentage);
            double amount = currentBalance * (parsedPercentage / 100.0);
            double amountRounded = Math.round(amount * 100.0) / 100.0;

            if (configFile.getVaultLoseMoneyOnDeathOnlyAtNight(world.getName())) {
                if (AutomaticPvp.nightWorlds.contains(world)) {
                    economy.withdrawPlayer(killed, amountRounded);
                    shouldWithdraw = true;
                }
            } else {
                economy.withdrawPlayer(killed, amountRounded);
                shouldWithdraw = true;
            }

            if (shouldWithdraw) {
                String money = Double.toString(amountRounded);
                String killedName = killed.getName();
                String killerName = killer.getName(); // Corrigido killerName para pegar o nome do killer
                killed.sendMessage(langFile.getFeedbackLoseMoney().replace("{0}", killerName).replace("{1}", money));
                if (configFile.getVaultLoseMoneyOnDeathKillerRewardMoney(world.getName())) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(langFile.getFeedbackWinMoney().replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }
}