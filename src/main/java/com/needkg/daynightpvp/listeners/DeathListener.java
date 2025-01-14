package com.needkg.daynightpvp.listeners;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.settings.WorldSettings;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.vault.LoseMoneyOnDeath;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class DeathListener implements Listener {

    private final WorldSettings worldSettings;
    private final LoseMoneyOnDeath loseMoneyOnDeath;

    public DeathListener() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.worldSettings = container.getWorldSettings();
        this.loseMoneyOnDeath = container.getLoseMoneyOnDeath();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        World world = event.getEntity().getWorld();
        String worldName = world.getName();

        if (killer != null) {

            if (worldSettings.getPvpSettingsKeepInventoryWhenKilledByPlayersEnabled(worldName)) {
                event.setKeepInventory(true);
                event.getDrops().clear();
            }

            Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.getInstance(), () -> {
                if (worldSettings.getVaultLoseMoneyOnDeathEnabled(worldName) && DayNightPvP.vaultIsPresent) {
                    for (PermissionAttachmentInfo permission : event.getEntity().getEffectivePermissions()) {
                        if (permission.getPermission().startsWith("dnp.losemoney")) {
                            String percentage = killed.getEffectivePermissions().stream()
                                    .filter(perm -> perm.getPermission().startsWith("dnp.losemoney."))
                                    .findFirst()
                                    .map(perm -> perm.getPermission().replace("dnp.losemoney.", ""))
                                    .orElse("");

                            loseMoneyOnDeath.loseMoneyOnDeath(killed, killer, world, percentage);
                        }
                    }
                }
            });

        }

    }

}
