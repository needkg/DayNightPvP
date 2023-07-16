package com.needkg.daynightpvp.config;

import org.bukkit.plugin.java.JavaPlugin;

public class LangManager {

    private final StartupFiles startupFiles;
    public static String fileVersion;
    public static String onMessage;
    public static String offMessage;
    public static String dayMessage;
    public static String nightMessage;
    public static String reloadedConfig;
    public static String pvpSetOn;
    public static String pvpSetOff;
    public static String warnPvpControl;
    public static String warnPvpControl2;
    public static String currentStatusPvpControl;
    public static String currentStatusPvp;
    public static String setPvpControlAuto;
    public static String setPvpControlManual;
    public static String statusAutomatic;
    public static String statusManual;
    public static String updateFoundMessage;
    public static String updateFoundClick;
    public static String dnpCommandPermissions;
    public static String dnpCommandPermissionDNPADMIN;
    public static String dnpCommandMessage;
    public static String dnpCommandDnp;
    public static String dnpCommandReload;
    public static String dnpCommandPerms;
    public static String dnpCommandPvpControl;
    public static String dnpCommandPvp;
    public static String dnpCommandDayNight;
    public static String dnpCommandPvpControlAlreadyDisabled;
    public static String dnpCommandPvpControlAlreadyEnabled;

    public LangManager() {
        startupFiles = new StartupFiles();
    }

    public void updateLangs(JavaPlugin plugin) {

        startupFiles.selectLangFile(plugin);

        fileVersion = getString("fileVersion");
        onMessage = getString("onMessage");
        offMessage = getString("offMessage");
        dayMessage = getString("dayMessage");
        nightMessage = getString("nightMessage");
        reloadedConfig = getString("reloadedConfig");
        pvpSetOn = getString("pvpSetOn");
        pvpSetOff = getString("pvpSetOff");
        warnPvpControl = getString("warnPvpControl");
        warnPvpControl2 = getString("warnPvpControl2");
        currentStatusPvpControl = getString("currentStatusPvpControl");
        currentStatusPvp = getString("currentStatusPvp");
        setPvpControlAuto = getString("setPvpControlAuto");
        setPvpControlManual = getString("setPvpControlManual");
        statusAutomatic = getString("statusAutomatic");
        statusManual = getString("statusManual");
        updateFoundMessage = getString("updateFoundMessage");
        updateFoundClick = getString("updateFoundClick");

        dnpCommandPermissions = getString("dnpCommandPermissions");
        dnpCommandPermissionDNPADMIN = getString("dnpCommandPermissionDNPADMIN");
        dnpCommandMessage = getString("dnpCommandMessage");
        dnpCommandDnp = getString("dnpCommandDnp");
        dnpCommandReload = getString("dnpCommandReload");
        dnpCommandPerms = getString("dnpCommandPerms");
        dnpCommandPvpControl = getString("dnpCommandPvpControl");
        dnpCommandPvp = getString("dnpCommandPvp");
        dnpCommandDayNight = getString("dnpCommandDayNight");
        dnpCommandPvpControlAlreadyDisabled = getString("dnpCommandPvpControlAlreadyDisabled");
        dnpCommandPvpControlAlreadyEnabled = getString("dnpCommandPvpControlAlreadyEnabled");
    }

    public static String getString(String path) {
        String resultado = StartupFiles.currentLangFile.getString(path);
        if (resultado != null) {
            resultado = resultado.replaceAll("&", "§");
        }
        return resultado;
    }

}
