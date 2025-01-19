package me.needkg.daynightpvp.configuration.emun;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;
import org.bukkit.Difficulty;
import org.bukkit.Sound;

public enum World {
    BOSSBAR("boss-bar") {
        @Override
        public boolean getBossbarEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".enabled", false);
        }
    },

    DAY_NIGHT_DURATION("day-night-duration") {
        @Override
        public boolean getDayNightDurationEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".enabled", false);
        }

        @Override
        public int getDayNightDurationDayDuration(String worldName, String path, ConfigurationAccess access) {
            return access.getInt(path + ".day-duration", 600, 1, 86400);
        }

        @Override
        public int getDayNightDurationNightDuration(String worldName, String path, ConfigurationAccess access) {
            return access.getInt(path + ".night-duration", 600, 1, 86400);
        }
    },

    INTEGRATION("integrations") {
        @Override
        public boolean getIntegrationsVaultLoseMoneyEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".vault.lose-money.enabled", false);
        }

        @Override
        public boolean getIntegrationsVaultLoseMoneyOnlyAtNight(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".vault.lose-money.only-at-night", true);
        }

        @Override
        public boolean getIntegrationsVaultLoseMoneyRewardKiller(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".vault.lose-money.reward-killer", true);
        }

        @Override
        public boolean getIntegrationsGriefPreventionPvpInClaims(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".grief-prevention.pvp-in-claims", false);
        }
    },

    PVP("pvp") {
        @Override
        public boolean getPvpAutomaticEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".automatic.enabled", true);
        }

        @Override
        public int getPvpAutomaticDayEnd(String worldName, String path, ConfigurationAccess access) {
            return access.getInt(path + ".automatic.day-end", 12000, 1, 24000);
        }

        @Override
        public boolean getPvpKeepInventoryEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".keep-inventory-on-pvp.enabled", false);
        }

        @Override
        public boolean getPvpKeepInventoryKeepExp(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".keep-inventory-on-pvp.keep-exp", true);
        }
    },

    DIFFICULTY("difficulty") {
        @Override
        public boolean getDifficultyEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".enabled", false);
        }

        @Override
        public Difficulty getDifficultyDay(String worldName, String path, ConfigurationAccess access) {
            return access.getDifficulty(path + ".day", Difficulty.NORMAL);
        }

        @Override
        public Difficulty getDifficultyNight(String worldName, String path, ConfigurationAccess access) {
            return access.getDifficulty(path + ".night", Difficulty.HARD);
        }
    },

    NOTIFICATION("notifications") {
        @Override
        public boolean getNotificationsChatDayNightChange(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".chat.day-night-change", true);
        }

        @Override
        public boolean getNotificationsChatNoPvpWarn(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".chat.no-pvp-warn", true);
        }

        @Override
        public boolean getNotificationsTitleEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".title.enabled", true);
        }

        @Override
        public int getNotificationsTitleFadeIn(String worldName, String path, ConfigurationAccess access) {
            return access.getInt(path + ".title.fade-in", 20, 0, 100);
        }

        @Override
        public int getNotificationsTitleStay(String worldName, String path, ConfigurationAccess access) {
            return access.getInt(path + ".title.stay", 20, 0, 100);
        }

        @Override
        public int getNotificationsTitleFadeOut(String worldName, String path, ConfigurationAccess access) {
            return access.getInt(path + ".title.fade-out", 20, 0, 100);
        }

        @Override
        public boolean getNotificationsSoundEnabled(String worldName, String path, ConfigurationAccess access) {
            return access.getBoolean(path + ".sound.enabled", true);
        }

        @Override
        public Sound getNotificationsSoundDayType(String worldName, String path, ConfigurationAccess access) {
            return access.getSound(path + ".sound.day.type", Sound.ENTITY_CHICKEN_AMBIENT);
        }

        @Override
        public float getNotificationsSoundDayVolume(String worldName, String path, ConfigurationAccess access) {
            return access.getFloat(path + ".sound.day.volume", 1.0f, 0.0f, 1.0f);
        }

        @Override
        public Sound getNotificationsSoundNightType(String worldName, String path, ConfigurationAccess access) {
            return access.getSound(path + ".sound.night.type", Sound.ENTITY_GHAST_AMBIENT);
        }

        @Override
        public float getNotificationsSoundNightVolume(String worldName, String path, ConfigurationAccess access) {
            return access.getFloat(path + ".sound.night.volume", 1.0f, 0.0f, 1.0f);
        }
    };

    private final String path;

    World(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getWorldPath(String worldName) {
        return String.format("worlds.%s.%s", worldName, path);
    }

    // Métodos default que podem ser sobrescritos
    public boolean getBossbarEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getDayNightDurationEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public int getDayNightDurationDayDuration(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public int getDayNightDurationNightDuration(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getIntegrationsVaultLoseMoneyEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getIntegrationsVaultLoseMoneyOnlyAtNight(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getIntegrationsVaultLoseMoneyRewardKiller(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getIntegrationsGriefPreventionPvpInClaims(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getPvpAutomaticEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public int getPvpAutomaticDayEnd(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getPvpKeepInventoryEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getPvpKeepInventoryKeepExp(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getDifficultyEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public Difficulty getDifficultyDay(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public Difficulty getDifficultyNight(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getNotificationsChatDayNightChange(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getNotificationsChatNoPvpWarn(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getNotificationsTitleEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public int getNotificationsTitleFadeIn(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public int getNotificationsTitleStay(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public int getNotificationsTitleFadeOut(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public boolean getNotificationsSoundEnabled(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public Sound getNotificationsSoundDayType(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public float getNotificationsSoundDayVolume(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public Sound getNotificationsSoundNightType(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }

    public float getNotificationsSoundNightVolume(String worldName, String path, ConfigurationAccess access) {
        throw new UnsupportedOperationException();
    }
} 