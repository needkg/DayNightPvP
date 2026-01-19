package org.callv2.daynightpvp.claims;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.callv2.daynightpvp.claims.griefprevention.GriefPreventionHandler;
import org.callv2.daynightpvp.claims.huskclaims.HuskClaimsHandler;

public interface ClaimHandler {
    boolean verify(Player damagedPlayer, Player damager);
}
