package org.callv2.daynightpvp.griefprevention;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;

public class GriefPreventionHandler {

    public boolean verify(Player damagedPlayer, Player damager) {
        DataStore griefPrevention = GriefPrevention.instance.dataStore;
        return griefPrevention.getClaimAt(damagedPlayer.getLocation(), true, null) != null ||  griefPrevention.getClaimAt(damager.getLocation(), true, null) != null;
    }

}
