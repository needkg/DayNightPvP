package org.callv2.daynightpvp.claims.griefprevention;

import me.ryanhamshire.GriefPrevention.DataStore;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.entity.Player;
import org.callv2.daynightpvp.claims.ClaimHandler;

public class GriefPreventionHandler implements ClaimHandler {

    @Override
    public boolean verify(Player damagedPlayer, Player damager) {
        DataStore griefPrevention = GriefPrevention.instance.dataStore;
        return griefPrevention.getClaimAt(damagedPlayer.getLocation(), true, null) != null || griefPrevention.getClaimAt(damager.getLocation(), true, null) != null;
    }

}
