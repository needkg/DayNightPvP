package me.needkg.daynightpvp.feature.vault;

import me.needkg.daynightpvp.core.di.ConfigurationContainer;
import me.needkg.daynightpvp.configuration.config.IntegrationConfiguration;
import me.needkg.daynightpvp.configuration.message.CombatMessages;
import me.needkg.daynightpvp.core.di.MessageContainer;
import me.needkg.daynightpvp.task.WorldStateController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LoseMoney {

    private final IntegrationConfiguration integrationConfiguration;
    private final CombatMessages combatMessages;

    public LoseMoney(ConfigurationContainer configurationContainer, MessageContainer messageContainer) {
        this.integrationConfiguration = configurationContainer.getIntegrationConfiguration();
        this.combatMessages = messageContainer.getCombat();
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

            if (integrationConfiguration.getIntegrationsVaultLoseMoneyOnlyAtNight(world.getName())) {
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
                killed.sendMessage(combatMessages.getMoneyLostMessage().replace("{0}", killerName).replace("{1}", money));
                if (integrationConfiguration.getIntegrationsVaultLoseMoneyRewardKiller(world.getName())) {
                    economy.depositPlayer(killer, amountRounded);
                    killer.sendMessage(combatMessages.getMoneyWonMessage().replace("{0}", killedName).replace("{1}", money));
                }
            }
        }
    }
}