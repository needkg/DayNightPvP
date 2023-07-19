package com.needkg.daynightpvp.events;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.vault.Vault;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import java.util.List;

public class DeathEvent implements Listener {

    private final Vault vault;

    public DeathEvent() {
        vault = new Vault();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        String world = event.getEntity().getWorld().getName();
        List<String> worldList = ConfigManager.worldList;

        if (killer != null) {
            if (killed.hasPermission("dnp.losemoney.")) {
                String porcetage = killed.getEffectivePermissions().stream()
                        .filter(perm -> perm.getPermission().startsWith("dnp.losemoney."))
                        .findFirst()
                        .map(perm -> perm.getPermission().replace("dnp.losemoney.", ""))
                        .orElse("");
                vault.loseMoneyOnDeath(killed, killer, world, worldList, porcetage);
            }
        }
    }

}
