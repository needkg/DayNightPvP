package org.callvdois.daynightpvp.worldguard;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;

public class AllowPvpOnDayFlag {

    public static StateFlag allowPvpOnDay;

    public static boolean checkState(Player player) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        Location loc = localPlayer.getLocation();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        return query.testState(loc, localPlayer, allowPvpOnDay);
    }

    public static void register() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("allow-pvp-on-day", false);
            registry.register(flag);
            allowPvpOnDay = flag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("allow-pvp-on-day");
            if (existing instanceof StateFlag) {
                allowPvpOnDay = (StateFlag) existing;
            }
        }
    }
}
