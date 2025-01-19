package me.needkg.daynightpvp.features.griefprevention;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;

public class GriefPreventionManager {

    public boolean isPlayerInClaim(Player damagedPlayer, Player damager) {
        DataStore griefPrevention = GriefPrevention.instance.dataStore;
        return griefPrevention.getClaimAt(damagedPlayer.getLocation(), true, null) != null || griefPrevention.getClaimAt(damager.getLocation(), true, null) != null;
    }

}
