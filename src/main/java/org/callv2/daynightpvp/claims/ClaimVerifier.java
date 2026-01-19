package org.callv2.daynightpvp.claims;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.callv2.daynightpvp.claims.griefprevention.GriefPreventionHandler;
import org.callv2.daynightpvp.claims.huskclaims.HuskClaimsHandler;

public class ClaimVerifier {
    private final ClaimHandler handler;

    public ClaimVerifier() {
        if (isEnabled("HuskClaims")) {
            handler = new HuskClaimsHandler();
        } else if (isEnabled("GriefPrevention")) {
            handler = new GriefPreventionHandler();
        } else {
            handler = null;
        }
    }

    public boolean verify(Player damagedPlayer, Player damager) {
        if (handler == null) return false;

        try {
            return handler.verify(damagedPlayer, damager);
        } catch (Throwable t) {
            return false;
        }
    }

    private boolean isEnabled(String name) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
        return plugin != null && plugin.isEnabled();
    }
}
