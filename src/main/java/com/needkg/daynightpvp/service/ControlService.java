package com.needkg.daynightpvp.service;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ControlService {

    public static Boolean pvpControlStatus = true;
    private int checkPvpControl;

    public String checkPvpControlStatus() {
        if (pvpControlStatus) {
            return LangManager.getString("statusAutomatic");
        } else {
            return LangManager.getString("statusManual");
        }
    }

    public void startCheckPvpControl() {
        if (ConfigManager.warnPvpControl) {
            checkPvpControl = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.plugin, () -> {
                if (!ControlService.pvpControlStatus) {
                    sendWarnMessage();
                    stopCheckPvpControl();
                }
            }, 12000L, 12000L);
        }
    }

    public void sendWarnMessage() {
        if (ConfigManager.warnPvpControl) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("dnp.admin")) {
                    String warnPvpControl = LangManager.getString("warnPvpControl");
                    player.sendMessage(warnPvpControl);
                }
            }
        }
    }

    private void stopCheckPvpControl() {
        if (pvpControlStatus) {
            Bukkit.getServer().getScheduler().cancelTask(checkPvpControl);
        }
    }

}
