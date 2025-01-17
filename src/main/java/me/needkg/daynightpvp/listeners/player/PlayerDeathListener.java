package me.needkg.daynightpvp.listeners.player;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.config.IntegrationConfiguration;
import me.needkg.daynightpvp.configuration.config.PvpConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.features.vault.LoseMoney;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final PvpConfiguration pvpConfiguration;
    private final IntegrationConfiguration integrationConfiguration;
    private final LoseMoney loseMoney;

    public PlayerDeathListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.pvpConfiguration = container.getConfigurationContainer().getPvpConfiguration();
        this.integrationConfiguration = container.getConfigurationContainer().getIntegrationConfiguration();
        this.loseMoney = container.getLoseMoneyOnDeath();
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
        if (pvpConfiguration.getPvpKeepInventoryOnPvp(worldName)) {
            event.setKeepInventory(true);
            event.getDrops().clear();
        }
    }

    private void handleMoneyLoss(Player killed, Player killer, World world, String worldName) {
        if (!shouldHandleMoneyLoss(worldName)) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.getInstance(), () -> {
            String loseMoneyPercentage = extractLoseMoneyPercentage(killed);
            if (!loseMoneyPercentage.isEmpty()) {
                loseMoney.loseMoneyOnDeath(killed, killer, world, loseMoneyPercentage);
            }
        });
    }

    private boolean shouldHandleMoneyLoss(String worldName) {
        return integrationConfiguration.getIntegrationsVaultLoseMoneyEnabled(worldName) && DayNightPvP.isVaultPresent;
    }

    private String extractLoseMoneyPercentage(Player player) {
        return player.getEffectivePermissions().stream()
                .filter(perm -> perm.getPermission().startsWith("dnp.losemoney" + "."))
                .findFirst()
                .map(perm -> perm.getPermission().replace("dnp.losemoney" + ".", ""))
                .orElse("");
    }
}
