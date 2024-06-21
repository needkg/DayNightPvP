package org.callvdois.daynightpvp.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.SearchUtils;

import java.util.List;

public class LoseMoneyOnDeath {

    public static void loseMoneyOnDeath(Player killed, Player killer, String world, List<String> worldList, String percentage) {
        ConfigManager configManager = new ConfigManager();
        LangManager langManager = new LangManager();
        boolean onlyNight = configManager.getVaultLoseMoneyOnDeathOnlyAtNight();
        boolean onlyConfiguredWorlds = configManager.getVaultLoseMoneyOnDeathOnlyInConfiguredWorlds();

        Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        if (killed != null && !percentage.isEmpty() && percentage.matches("[1-9][0-9]?|100")) {
            boolean shouldWithdraw = false;
            double currentBalance = economy.getBalance(killed);
            int parsedPercentage = Integer.parseInt(percentage);
            double amount = currentBalance * (parsedPercentage / 100.0);
            double amountRounded = Math.round(amount * 100.0) / 100.0;

            if (onlyNight) {
                if (Bukkit.getWorld(world).getPVP()) {
                    if (onlyConfiguredWorlds) {
                        if (SearchUtils.stringExistInList(worldList, world)) {
                            economy.withdrawPlayer(killed, amountRounded);
                            shouldWithdraw = true;
                            // noite e configurado
                        }
                    } else {
                        shouldWithdraw = true;
                        economy.withdrawPlayer(killed, amountRounded);
                        // noite e qualquer mundo
                    }
                }
            } else if (onlyConfiguredWorlds) {
                if (SearchUtils.stringExistInList(worldList, world)) {
                    shouldWithdraw = true;
                    economy.withdrawPlayer(killed, amountRounded);
                    // dia/noite e configurado
                }
            } else {
                shouldWithdraw = true;
                economy.withdrawPlayer(killed, amountRounded);
                // dia/noite e qualquer
            }
            if (shouldWithdraw) {
                String money = Double.toString(amountRounded);
                String killedName = killed.getName();
                String killerName = killed.getName();
                killed.sendMessage(langManager.getFeedbackLoseMoney().replace("{0}", killerName).replace("{1}", money));
                if (configManager.getVaultLoseMoneyOnDeathKillerRewardMoney()) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(langManager.getFeedbackWinMoney().replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }

}
