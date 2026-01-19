package org.callv2.daynightpvp.claims.huskclaims;

import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.callv2.daynightpvp.claims.ClaimHandler;

public class HuskClaimsHandler implements ClaimHandler {

    @Override
    public boolean verify(Player damagedPlayer, Player damager) {
        BukkitHuskClaimsAPI api = BukkitHuskClaimsAPI.getInstance();
        return api.getClaimAt(api.getPosition(damagedPlayer.getLocation())).isPresent()
                || api.getClaimAt(api.getPosition(damager.getLocation())).isPresent();
    }

}
