package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.PluginUtils;
import com.needkg.daynightpvp.utils.SearchUtils;
import com.needkg.daynightpvp.vault.Vault;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        String world = event.getEntity().getWorld().getName();
        List<String> worldList = ConfigManager.worldList;

        if (ConfigManager.keepInventoryWhenKilledByPlayer) {
            if (killer != null) {
                if (SearchUtils.stringInList(worldList, world)) {
                    event.setKeepInventory(true);
                    event.getDrops().clear();
                    event.setKeepLevel(true);
                }
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(DayNightPvP.plugin, () -> {
            if (ConfigManager.vaultLoseMoneyOnDeath && PluginUtils.isPluginInstalled("Vault")) {
                if (killer != null) {
                    for (PermissionAttachmentInfo permission : event.getEntity().getEffectivePermissions()) {
                        if (permission.getPermission().startsWith("dnp.losemoney")) {
                            String porcetage = killed.getEffectivePermissions().stream()
                                    .filter(perm -> perm.getPermission().startsWith("dnp.losemoney."))
                                    .findFirst()
                                    .map(perm -> perm.getPermission().replace("dnp.losemoney.", ""))
                                    .orElse("");
                            Vault.loseMoneyOnDeath(killed, killer, world, worldList, porcetage);
                        }
                    }
                }
            }
        });
    }

}
