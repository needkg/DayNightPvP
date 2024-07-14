package org.callv2.daynightpvp.griefprevention;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;

public class GriefPreventionHandler {

    public boolean verify(Player damagedPlayer, Player damager) {
        if (GriefPrevention.instance.dataStore.getClaimAt(damagedPlayer.getLocation(), true, null) != null ||
                GriefPrevention.instance.dataStore.getClaimAt(damager.getLocation(), true, null) != null) {
            return true;
        }
        return false;
    }

}
