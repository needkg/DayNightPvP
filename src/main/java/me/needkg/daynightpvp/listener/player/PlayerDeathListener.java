package me.needkg.daynightpvp.listener.player;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.integration.vault.LoseMoney;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final WorldConfigurationManager worldConfigurationManager;
    private final LoseMoney loseMoney;

    public PlayerDeathListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldConfigurationManager = container.getWorldConfigurationManager();
        this.loseMoney = container.getLoseMoney();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = killed.getKiller();

        if (killer == null) {
            return;
        }

        World world = killed.getWorld();
        String worldName = world.getName();

        handleKeepInventory(event, worldName);
        handleMoneyLoss(killed, killer, world, worldName);
    }

    private void handleKeepInventory(PlayerDeathEvent event, String worldName) {
        if (worldConfigurationManager.isPvpKeepInventoryEnabled(worldName)) {
            event.setKeepInventory(true);
            event.getDrops().clear();
        }
        if (worldConfigurationManager.isPvpKeepInventoryKeepExp(worldName)) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }

    private void handleMoneyLoss(Player killed, Player killer, World world, String worldName) {
        if (!shouldHandleMoneyLoss(worldName)) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.getInstance(), () -> {
            String loseMoneyPercentage = extractLoseMoneyPercentage(killed);
            if (!loseMoneyPercentage.isEmpty()) {
                loseMoney.handleDeathMoneyTransaction(killed, killer, world, loseMoneyPercentage);
            }
        });
    }

    private boolean shouldHandleMoneyLoss(String worldName) {
        return worldConfigurationManager.isIntegrationsVaultLoseMoneyEnabled(worldName) && DayNightPvP.isVaultPresent;
    }

    private String extractLoseMoneyPercentage(Player player) {
        return player.getEffectivePermissions().stream()
                .filter(perm -> perm.getPermission().startsWith("dnp.losemoney" + "."))
                .findFirst()
                .map(perm -> perm.getPermission().replace("dnp.losemoney" + ".", ""))
                .orElse("");
    }
}
