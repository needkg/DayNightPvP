package org.callvdois.daynightpvp.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.files.ConfigFile;
import org.callvdois.daynightpvp.utils.SearchUtils;
import org.callvdois.daynightpvp.vault.LoseMoneyOnDeath;

import java.util.List;

public class DeathListener implements Listener {

    private final ConfigFile configFile;
    private final SearchUtils searchUtils;

    public DeathListener() {
        configFile = new ConfigFile();
        searchUtils = new SearchUtils();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        World world = event.getEntity().getWorld();
        List<World> worldList = configFile.getDayNightPvpWorlds();

        if (configFile.getPvpKeepInventoryWhenKilledByPlayer()) {
            if (killer != null) {
                if (searchUtils.worldExistsInWorldList(worldList, world.getName())) {
                    event.setKeepInventory(true);
                    event.getDrops().clear();
                    event.setKeepLevel(true);
                }
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.getInstance(), () -> {
            if (configFile.getVaultLoseMoneyOnDeathEnabled() && DayNightPvP.vaultIsPresent) {
                if (killer != null) {
                    for (PermissionAttachmentInfo permission : event.getEntity().getEffectivePermissions()) {
                        if (permission.getPermission().startsWith("dnp.losemoney")) {
                            String porcetage = killed.getEffectivePermissions().stream()
                                    .filter(perm -> perm.getPermission().startsWith("dnp.losemoney."))
                                    .findFirst()
                                    .map(perm -> perm.getPermission().replace("dnp.losemoney.", ""))
                                    .orElse("");
                            LoseMoneyOnDeath.loseMoneyOnDeath(killed, killer, world, worldList, porcetage);
                        }
                    }
                }
            }
        });
    }

}
