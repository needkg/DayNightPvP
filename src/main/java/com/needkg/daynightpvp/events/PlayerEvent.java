package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.utils.SearchUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import java.util.List;

public class PlayerEvent implements Listener {

    private final SearchUtils searchUtils;

    public PlayerEvent() {
        searchUtils = new SearchUtils();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        String world = event.getEntity().getWorld().getName();
        List<String> worldList = ConfigManager.configFileConfig.getStringList("worlds");

        if (killed.hasPermission("dnp.losemoney.*")) {
            String porcetage = killed.getEffectivePermissions().stream()
                    .filter(perm -> perm.getPermission().startsWith("dnp.losemoney."))
                    .findFirst()
                    .map(perm -> perm.getPermission().replace("dnp.losemoney.", ""))
                    .orElse("");
            loseMoney(killed, killer, world, worldList, porcetage);
        }

    }

    public void loseMoney(Player killed, Player killer, String world, List<String> worldList, String percentage) {
        boolean onlyNight = ConfigManager.loseMoneyOnlyNight;
        boolean onlyConfiguredWorlds = ConfigManager.loseMoneyOnlyConfiguredWorlds;

        Economy economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
        if (economy != null && killed != null && !percentage.isEmpty() && percentage.matches("[1-9][0-9]?|100")) {
            double currentBalance = economy.getBalance(killed);
            int parsedPercentage = Integer.parseInt(percentage);
            double amount = currentBalance * (parsedPercentage / 100.0);
            double amountRounded = Math.round(amount * 100.0) / 100.0;

            if (onlyNight) {
                if (Bukkit.getWorld(world).getPVP()) {
                    if (onlyConfiguredWorlds) {
                        if (searchUtils.stringInList(worldList, world)) {
                            economy.withdrawPlayer(killed, amountRounded);
                            // Perde dinheiro se estiver de noite e for no mundo configurado
                        }
                    } else {
                        economy.withdrawPlayer(killed, amountRounded);
                        // Perde dinheiro se estiver de noite e em qualquer mundo
                    }
                }
            } else if (onlyConfiguredWorlds) {
                if (searchUtils.stringInList(worldList, world)) {
                    economy.withdrawPlayer(killed, amountRounded);
                    // Perde dinheiro se estiver de dia/noite e for no mundo configurado
                }
            } else {
                economy.withdrawPlayer(killed, amountRounded);
                // Perde dinheiro se estiver de dia/noite e em qualquer mundo
            }
            String money = Double.toString(amountRounded);
            String killedName = killed.getName();
            String killerName = killed.getName();
            killed.sendMessage(LangManager.loseMoneyMessage.replace("{0}", money));
            if (ConfigManager.loseMoneyKillerWinMoney) {
                economy.depositPlayer(killer, amountRounded);
                killer.sendMessage(LangManager.winMoneyMessage.replace("{0}", killedName).replace("{1}", money));
            }
        }
    }

}
