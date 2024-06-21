package org.callvdois.daynightpvp.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {

    public static File file;
    public static FileConfiguration fileConfiguration;

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (Exception ignore) {

        }
    }

    public void addWorld(String world) {
        List<String> worldList = getDayNightPvpWorlds();
        worldList.add(world);
        fileConfiguration.set("daynightpvp.worlds", worldList);
        saveConfig();
    }

    public void removeWorld(String world) {
        List<String> worldList = getDayNightPvpWorlds();
        worldList.remove(world);
        fileConfiguration.set("daynightpvp.worlds", worldList);
        saveConfig();
    }

    public void setValue(String path, String value) {
        fileConfiguration.set(path, value);
    }

    public int getVersion() {
        return fileConfiguration.getInt("version");
    }

    public boolean getUpdateChecker() {
        return fileConfiguration.getBoolean("update-checker");
    }

    public String getLanguage() {
        return fileConfiguration.getString("language");
    }

    public List<String> getDayNightPvpWorlds() {
        return fileConfiguration.getStringList("daynightpvp.worlds");
    }

    public int getDayNightPvpDayEnd() {
        return fileConfiguration.getInt("daynightpvp.day-end");
    }

    public boolean getDayNightPvpAutomaticDifficultyEnabled() {
        return fileConfiguration.getBoolean("daynightpvp.automatic-difficulty.enabled");
    }

    public String getDayNightPvpAutomaticDifficultyDay() {
        return fileConfiguration.getString("daynightpvp.automatic-difficulty.day");
    }

    public String getDayNightPvpAutomaticDifficultyNight() {
        return fileConfiguration.getString("daynightpvp.automatic-difficulty.night");
    }

    public boolean getNotifyPlayersChatDayNightStarts() {
        return fileConfiguration.getBoolean("notify-players.chat.day-night-starts");
    }

    public boolean getNotifyPlayersChatHitAnotherPlayerDuringTheDay() {
        return fileConfiguration.getBoolean("notify-players.chat.hit-another-player-during-the-day");
    }

    public boolean getNotifyPlayersTitleEnabled() {
        return fileConfiguration.getBoolean("notify-players.title.enabled");
    }

    public int getNotifyPlayersTitleFadeIn() {
        return fileConfiguration.getInt("notify-players.title.fade-in");
    }

    public int getNotifyPlayersTitleStay() {
        return fileConfiguration.getInt("notify-players.title.stay");
    }

    public int getNotifyPlayersTitleFadeOut() {
        return fileConfiguration.getInt("notify-players.title.fade-out");
    }

    public boolean getNotifyPlayersSoundEnabled() {
        return fileConfiguration.getBoolean("notify-players.sound.enabled");
    }

    public String getNotifyPlayersSoundDay() {
        return fileConfiguration.getString("notify-players.sound.day");
    }

    public String getNotifyPlayersSoundNight() {
        return fileConfiguration.getString("notify-players.sound.night");
    }

    public boolean getPvpKeepInventoryWhenKilledByPlayer() {
        return fileConfiguration.getBoolean("pvp.keep-inventory-when-killed-by-player");
    }

    public boolean getVaultLoseMoneyOnDeathEnabled() {
        return fileConfiguration.getBoolean("vault.lose-money-on-death.enabled");
    }

    public boolean getVaultLoseMoneyOnDeathOnlyAtNight() {
        return fileConfiguration.getBoolean("vault.lose-money-on-death.only-at-night");
    }

    public boolean getVaultLoseMoneyOnDeathOnlyInConfiguredWorlds() {
        return fileConfiguration.getBoolean("vault.lose-money-on-death.only-in-configured-worlds");
    }

    public boolean getVaultLoseMoneyOnDeathKillerRewardMoney() {
        return fileConfiguration.getBoolean("vault.lose-money-on-death.killer-reward-money");
    }

    public boolean getGriefPreventionPvpInLandEnabled() {
        return fileConfiguration.getBoolean("griefprevention.pvp-in-land");
    }
}
