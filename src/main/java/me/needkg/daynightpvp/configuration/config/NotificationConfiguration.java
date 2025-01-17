package me.needkg.daynightpvp.configuration.config;

import me.needkg.daynightpvp.configuration.validator.ConfigurationValidator;
import org.bukkit.Sound;

public class NotificationConfiguration {
    private final ConfigurationValidator validator;

    public NotificationConfiguration(ConfigurationValidator validator) {
        this.validator = validator;
    }

    public boolean getNotificationsChatDayNightChangeEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.chat.day-night-change", true);
    }

    public boolean getNotificationsChatNoPvpWarn(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.chat.no-pvp-warn", true);
    }

    public boolean getNotificationsTitleEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.title.enabled", true);
    }

    public int getNotificationsTitleFadeIn(String worldName) {
        return validator.getInt("worlds." + worldName + ".notifications.title.fade-in", 20, 1, 86400);
    }

    public int getNotificationsTitleStay(String worldName) {
        return validator.getInt("worlds." + worldName + ".notifications.title.stay", 20, 1, 86400);
    }

    public int getNotificationsTitleFadeOut(String worldName) {
        return validator.getInt("worlds." + worldName + ".notifications.title.fade-out", 20, 1, 86400);
    }

    public boolean getNotificationsSoundEnabled(String worldName) {
        return validator.getBoolean("worlds." + worldName + ".notifications.sound.enabled", true);
    }

    public Sound getNotificationsSoundDayType(String worldName) {
        return validator.getSound("worlds." + worldName + ".notifications.sound.day.type", Sound.ENTITY_CHICKEN_AMBIENT);
    }

    public float getNotificationsSoundDayVolume(String worldName) {
        return validator.getFloat("worlds." + worldName + ".notifications.sound.day.volume", 1.0F, 0.0F, 1.0F);
    }

    public Sound getNotificationsSoundNightType(String worldName) {
        return validator.getSound("worlds." + worldName + ".notifications.sound.night.type", Sound.ENTITY_GHAST_AMBIENT);
    }

    public float getNotificationsSoundNightVolume(String worldName) {
        return validator.getFloat("worlds." + worldName + ".notifications.sound.night.volume", 1.0F, 0.0F, 1.0F);
    }

}
