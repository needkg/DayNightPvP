package me.needkg.daynightpvp.integration.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.needkg.daynightpvp.integration.worldguard.flags.DaytimePvpFlag;

public class FlagManager {

    public static void register(String flagName, boolean defaultValue) {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag(flagName, defaultValue);
            registry.register(flag);
            DaytimePvpFlag.setDaytimePvpFlag(flag);
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get(flagName);
            if (existing instanceof StateFlag) {
                DaytimePvpFlag.setDaytimePvpFlag((StateFlag) existing);
            }
        }
    }
}
