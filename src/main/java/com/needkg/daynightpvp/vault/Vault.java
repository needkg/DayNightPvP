package com.needkg.daynightpvp.vault;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.SearchUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Vault {

    public static void loseMoneyOnDeath(Player killed, Player killer, String world, List<String> worldList, String percentage) {
        boolean onlyNight = ConfigManager.loseMoneyOnlyAtNight;
        boolean onlyConfiguredWorlds = ConfigManager.loseMoneyOnlyInConfiguredWorlds;

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
                        if (SearchUtils.stringInList(worldList, world)) {
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
                if (SearchUtils.stringInList(worldList, world)) {
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
                killed.sendMessage(LangManager.loseMoneyMessage.replace("{0}", killerName).replace("{1}", money));
                if (ConfigManager.loseMoneyKillerReward) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(LangManager.winMoneyMessage.replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }

}
