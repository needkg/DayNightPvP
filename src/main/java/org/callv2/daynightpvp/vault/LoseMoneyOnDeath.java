package org.callv2.daynightpvp.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;
import org.callv2.daynightpvp.utils.SearchUtils;

import java.util.List;

public class LoseMoneyOnDeath {

    private final ConfigFile configFile;
    private final LangFile langFile;

    public LoseMoneyOnDeath(ConfigFile configFile, LangFile langFile) {
        this.configFile = configFile;
        this.langFile = langFile;
    }

    public void loseMoneyOnDeath(Player killed, Player killer, World world, List<World> worldList, String percentage) {
        boolean vaultLoseMoneyOnDeathOnlyAtNight = configFile.getVaultLoseMoneyOnDeathOnlyAtNight();
        boolean vaultLoseMoneyOnDeathOnlyInConfiguredWorlds = configFile.getVaultLoseMoneyOnDeathOnlyInConfiguredWorlds();

        Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        if (killed != null && !percentage.isEmpty() && percentage.matches("[1-9][0-9]?|100")) {
            boolean shouldWithdraw = false;
            double currentBalance = economy.getBalance(killed);
            int parsedPercentage = Integer.parseInt(percentage);
            double amount = currentBalance * (parsedPercentage / 100.0);
            double amountRounded = Math.round(amount * 100.0) / 100.0;

            if (vaultLoseMoneyOnDeathOnlyAtNight) {
                if (world.getPVP()) {
                    if (vaultLoseMoneyOnDeathOnlyInConfiguredWorlds) {
                        if (SearchUtils.worldExistsInWorldList(worldList, world.getName())) {
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
            } else if (vaultLoseMoneyOnDeathOnlyInConfiguredWorlds) {
                if (SearchUtils.worldExistsInWorldList(worldList, world.getName())) {
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
                killed.sendMessage(langFile.getFeedbackLoseMoney().replace("{0}", killerName).replace("{1}", money));
                if (configFile.getVaultLoseMoneyOnDeathKillerRewardMoney()) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(langFile.getFeedbackWinMoney().replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }

}
