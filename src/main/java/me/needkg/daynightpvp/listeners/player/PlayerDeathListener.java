package me.needkg.daynightpvp.listeners.player;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.feature.vault.LoseMoneyOnDeath;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final WorldConfiguration worldConfiguration;
    private final LoseMoneyOnDeath loseMoneyOnDeath;

    public PlayerDeathListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldConfiguration = container.getWorldSettings();
        this.loseMoneyOnDeath = container.getLoseMoneyOnDeath();
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
        if (worldConfiguration.getPvpSettingsKeepInventoryWhenKilledByPlayersEnabled(worldName)) {
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
                loseMoneyOnDeath.loseMoneyOnDeath(killed, killer, world, loseMoneyPercentage);
            }
        });
    }

    private boolean shouldHandleMoneyLoss(String worldName) {
        return worldConfiguration.getVaultLoseMoneyOnDeathEnabled(worldName) && DayNightPvP.isVaultPresent;
    }

    private String extractLoseMoneyPercentage(Player player) {
        return player.getEffectivePermissions().stream()
                .filter(perm -> perm.getPermission().startsWith("dnp.losemoney" + "."))
                .findFirst()
                .map(perm -> perm.getPermission().replace("dnp.losemoney" + ".", ""))
                .orElse("");
    }
}
