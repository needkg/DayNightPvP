package me.needkg.daynightpvp.feature.vault;

import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.task.WorldStateController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LoseMoney {

    private final WorldConfiguration worldConfiguration;
    private final MessageConfiguration messageConfiguration;

    public LoseMoney(WorldConfiguration worldConfiguration, MessageConfiguration messageConfiguration) {
        this.worldConfiguration = worldConfiguration;
        this.messageConfiguration = messageConfiguration;
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

            if (worldConfiguration.getIntegrationsVaultLoseMoneyOnlyAtNight(world.getName())) {
                if (WorldStateController.nightWorlds.contains(world)) {
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
                killed.sendMessage(messageConfiguration.getFeedbackLoseMoney().replace("{0}", killerName).replace("{1}", money));
                if (worldConfiguration.getIntegrationsVaultLoseMoneyRewardKiller(world.getName())) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(messageConfiguration.getFeedbackWinMoney().replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }
}