package me.needkg.daynightpvp.integration.vault;

import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.task.controller.world.WorldStateController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LoseMoney {

    private final WorldConfigurationManager worldConfigurationManager;
    private final MessageManager messageManager;

    public LoseMoney(WorldConfigurationManager worldConfigurationManager, MessageManager messageManager) {
        this.worldConfigurationManager = worldConfigurationManager;
        this.messageManager = messageManager;
    }

    public void handleDeathMoneyTransaction(Player killed, Player killer, World world, String percentage) {

        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            Bukkit.getLogger().severe("No Economy providers found! Disabling loseMoneyOnDeath functionality.");
            return;
        }

        Economy economy = economyProvider.getProvider();

        if (!percentage.isEmpty() && percentage.matches("[1-9][0-9]?|100")) {
            boolean shouldWithdraw = false;
            double currentBalance = economy.getBalance(killed);
            int parsedPercentage = Integer.parseInt(percentage);
            double amount = currentBalance * (parsedPercentage / 100.0);
            double roundedAmout = Math.round(amount * 100.0) / 100.0;

            if (worldConfigurationManager.isIntegrationsVaultLoseMoneyEnabled(world.getName())) {
                if (WorldStateController.nightWorlds.contains(world)) {
                    economy.withdrawPlayer(killed, roundedAmout);
                    shouldWithdraw = true;
                }
            } else {
                economy.withdrawPlayer(killed, roundedAmout);
                shouldWithdraw = true;
            }

            if (shouldWithdraw) {
                String money = Double.toString(roundedAmout);
                String killedName = killed.getName();
                String killerName = killer.getName(); // Corrigido killerName para pegar o nome do killer
                killed.sendMessage(messageManager.getMessage(Message.COMBAT_MONEY_LOST).replace("{0}", killerName).replace("{1}", money));
                if (worldConfigurationManager.isIntegrationsVaultLoseMoneyRewardKiller(world.getName())) {
                    economy.depositPlayer(killer, roundedAmout);
                    killer.sendMessage(messageManager.getMessage(Message.COMBAT_MONEY_WON).replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }
}