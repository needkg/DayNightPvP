package me.needkg.daynightpvp.vault;

import me.needkg.daynightpvp.config.settings.MessageSettings;
import me.needkg.daynightpvp.config.settings.WorldSettings;
import me.needkg.daynightpvp.runnables.AutomaticPvp;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LoseMoneyOnDeath {

    private final WorldSettings worldSettings;
    private final MessageSettings messageSettings;

    public LoseMoneyOnDeath(WorldSettings worldSettings, MessageSettings messageSettings) {
        this.worldSettings = worldSettings;
        this.messageSettings = messageSettings;
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

            if (worldSettings.getVaultLoseMoneyOnDeathOnlyAtNight(world.getName())) {
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
                killed.sendMessage(messageSettings.getFeedbackLoseMoney().replace("{0}", killerName).replace("{1}", money));
                if (worldSettings.getVaultLoseMoneyOnDeathKillerRewardMoney(world.getName())) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(messageSettings.getFeedbackWinMoney().replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }
}