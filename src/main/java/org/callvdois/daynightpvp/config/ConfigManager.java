package org.callvdois.daynightpvp.config;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.utils.ConsoleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    public static File file;
    public static FileConfiguration fileConfiguration;

    public void setValue(String path, Object value) {
        fileConfiguration.set(path, value);
        saveConfig();
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (Exception e) {
            ConsoleUtils.warning("Error saving configuration file, resetting...");
            resetFile("config.yml");
        }
    }

    public void resetFile(String fileName) {
        DayNightPvP.getInstance().saveResource(fileName, true);
    }

    public void addWorld(String worldName) {
        List<World> worldList = getDayNightPvpWorlds();
        worldList.add(Bukkit.getWorld(worldName));
        fileConfiguration.set("daynightpvp.worlds", worldList);
        saveConfig();
    }

    public void removeWorld(String worldName) {
        List<World> worldList = getDayNightPvpWorlds();
        worldList.remove(Bukkit.getWorld(worldName));
        fileConfiguration.set("daynightpvp.worlds", worldList);
        saveConfig();
    }

    public int getInt(String path, Integer defaultValue) {

        String configValue = fileConfiguration.getString(path);

        if (configValue == null) {
            resetFile("config.yml");
            return defaultValue;
        }

        try {
            return Integer.parseInt(configValue);
        } catch (Exception e) {
            setValue(path, defaultValue);
            return defaultValue;
        }
    }

    public Difficulty getDifficulty(String path, Difficulty defaultValue) {

        String configValue = fileConfiguration.getString(path);

        if (configValue == null) {
            resetFile("config.yml");
            return defaultValue;
        }

        try {
            return Difficulty.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            setValue(path, defaultValue.name());
            return defaultValue;
        }
    }

    public Sound getSound(String path, Sound defaultValue) {

        String configValue = fileConfiguration.getString(path);

        if (configValue == null) {
            resetFile("config.yml");
            return defaultValue;
        }

        try {
            return Sound.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            setValue(path, defaultValue.name());
            return defaultValue;
        }
    }

    public String getString(String path, String defaultValue) {
        String configValue = fileConfiguration.getString(path);

        if (configValue == null) {
            resetFile("config.yml");
            return defaultValue;
        }

        return configValue;
    }

    public boolean getBoolean(String path, Boolean defaultValue) {
        String configValue = fileConfiguration.getString(path);

        if (configValue == null) {
            resetFile("config.yml");
            return defaultValue;
        }

        if (configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(configValue);
        } else {
            setValue(path, defaultValue);
            return defaultValue;
        }
    }

    public List<World> getWorldList(String path, List<String> defaultValue) {

        List<String> configValue = fileConfiguration.getStringList(path);
        List<World> worldList = new ArrayList<>();

        // Se a lista de String estiver vazia, reseta a configuração e retorna o valor padrão.
        if (configValue.isEmpty()) {
            resetFile("config.yml");

            for (String worldName : defaultValue) {
                if (Bukkit.getWorld(worldName) != null) {
                    worldList.add(Bukkit.getWorld(worldName));
                }
            }
            setValue(path, defaultValue);
            return worldList;
        }

        // Verifica e transforma a lista de String em mundos validos.
        for (String worldName : configValue) {
            if (Bukkit.getWorld(worldName) != null) {
                worldList.add(Bukkit.getWorld(worldName));
            }
        }
        return worldList;
    }

    public int getVersion() {
        return fileConfiguration.getInt("version");
    }

    public boolean getUpdateChecker() {
        return getBoolean("update-checker", true);
    }

    public String getLanguage() {
        return getString("language", "en-US");
    }

    public Boolean getDayNightDurationEnabled() {
        return getBoolean("day-night-duration.enabled", false);
    }

    public int getDayNightDurationDayDuration() {
        return getInt("day-night-duration.day-duration", 600);
    }

    public int getDayNightDurationNightDuration() {
        return getInt("day-night-duration.night-duration", 600);
    }

    public List<World> getDayNightDurationWorlds() {
        List<String> defaultValue = new ArrayList<>();
        defaultValue.add("world");
        defaultValue.add("worldNameExample");
        defaultValue.add("MiningWorld");
        return getWorldList("day-night-duration.worlds", defaultValue);
    }

    public List<World> getDayNightPvpWorlds() {
        List<String> defaultValue = new ArrayList<>();
        defaultValue.add("world");
        defaultValue.add("worldNameExample");
        defaultValue.add("MiningWorld");
        return getWorldList("day-night-duration.worlds", defaultValue);
    }

    public int getDayNightPvpDayEnd() {
        return getInt("daynightpvp.day-end", 12000);
    }

    public boolean getDayNightPvpAutomaticDifficultyEnabled() {
        return getBoolean("daynightpvp.automatic-difficulty.enabled", false);
    }

    public Difficulty getDayNightPvpAutomaticDifficultyDay() {
        return getDifficulty("daynightpvp.automatic-difficulty.day", Difficulty.NORMAL);
    }

    public Difficulty getDayNightPvpAutomaticDifficultyNight() {
        return getDifficulty("daynightpvp.automatic-difficulty.night", Difficulty.HARD);
    }

    public boolean getNotifyPlayersChatDayNightStarts() {
        return getBoolean("notify-players.chat.day-night-starts", true);
    }

    public boolean getNotifyPlayersChatHitAnotherPlayerDuringTheDay() {
        return getBoolean("notify-players.chat.hit-another-player-during-the-day", true);
    }

    public boolean getNotifyPlayersTitleEnabled() {
        return getBoolean("notify-players.title.enabled", true);
    }

    public int getNotifyPlayersTitleFadeIn() {
        return getInt("notify-players.title.fade-in", 20);
    }

    public int getNotifyPlayersTitleStay() {
        return getInt("notify-players.title.stay", 20);
    }

    public int getNotifyPlayersTitleFadeOut() {
        return getInt("notify-players.title.fade-out", 20);
    }

    public boolean getNotifyPlayersSoundEnabled() {
        return getBoolean("notify-players.sound.enabled", true);
    }

    public Sound getNotifyPlayersSoundDay() {
        return getSound("notify-players.sound.day", Sound.ENTITY_CHICKEN_AMBIENT);
    }

    public Sound getNotifyPlayersSoundNight() {
        return getSound("notify-players.sound.night", Sound.ENTITY_GHAST_AMBIENT);
    }

    public boolean getPvpKeepInventoryWhenKilledByPlayer() {
        return getBoolean("pvp.keep-inventory-when-killed-by-player", false);
    }

    public boolean getVaultLoseMoneyOnDeathEnabled() {
        return getBoolean("vault.lose-money-on-death.enabled", false);
    }

    public boolean getVaultLoseMoneyOnDeathOnlyAtNight() {
        return getBoolean("vault.lose-money-on-death.only-at-night", true);
    }

    public boolean getVaultLoseMoneyOnDeathOnlyInConfiguredWorlds() {
        return getBoolean("vault.lose-money-on-death.only-in-configured-worlds", true);
    }

    public boolean getVaultLoseMoneyOnDeathKillerRewardMoney() {
        return getBoolean("vault.lose-money-on-death.killer-reward-money", true);
    }

    public boolean getGriefPreventionPvpInLandEnabled() {
        return getBoolean("griefprevention.pvp-in-land", false);
    }
}
