package org.callv2.daynightpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.utils.SearchUtils;
import org.callv2.daynightpvp.vault.LoseMoneyOnDeath;

import java.util.List;

public class DeathListener implements Listener {

    private final LoseMoneyOnDeath loseMoneyOnDeath;
    private final List<World> dayNightPvpWorlds;
    private final boolean keepInventoryWhenKilledByPlayer;
    private final boolean vaultLoseMoneyOnDeathEnabled;

    public DeathListener(ConfigFile configFile,  LoseMoneyOnDeath loseMoneyOnDeath) {
        this.loseMoneyOnDeath = loseMoneyOnDeath;

        dayNightPvpWorlds = configFile.getDayNightPvpWorlds();
        keepInventoryWhenKilledByPlayer = configFile.getPvpKeepInventoryWhenKilledByPlayer();
        vaultLoseMoneyOnDeathEnabled = configFile.getVaultLoseMoneyOnDeathEnabled();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        World world = event.getEntity().getWorld();

        if (keepInventoryWhenKilledByPlayer) {
            if (killer != null) {
                if (SearchUtils.worldExistsInWorldList(dayNightPvpWorlds, world.getName())) {
                    event.setKeepInventory(true);
                    event.getDrops().clear();
                    event.setKeepLevel(true);
                }
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.getInstance(), () -> {
            if (vaultLoseMoneyOnDeathEnabled && DayNightPvP.vaultIsPresent) {
                if (killer != null) {
                    for (PermissionAttachmentInfo permission : event.getEntity().getEffectivePermissions()) {
                        if (permission.getPermission().startsWith("dnp.losemoney")) {
                            String percentage = killed.getEffectivePermissions().stream()
                                    .filter(perm -> perm.getPermission().startsWith("dnp.losemoney."))
                                    .findFirst()
                                    .map(perm -> perm.getPermission().replace("dnp.losemoney.", ""))
                                    .orElse("");

                            loseMoneyOnDeath.loseMoneyOnDeath(killed, killer, world, dayNightPvpWorlds, percentage);
                        }
                    }
                }
            }
        });
    }

}
