package me.needkg.daynightpvp.configuration.type;

import me.needkg.daynightpvp.configuration.access.ConfigurationAccess;

import java.util.Set;

public enum GlobalConfigurationType {
    DEBUG("debug") {
        @Override
        public boolean isEnabled(ConfigurationAccess access) {
            return access.getBoolean(getPath() + ".enabled", false);
        }

        @Override
        public boolean isVerbose(ConfigurationAccess access) {
            return access.getBoolean(getPath() + ".verbose", false);
        }
    },

    GENERAL("") {
        @Override
        public boolean isUpdateCheckerEnabled(ConfigurationAccess access) {
            return access.getBoolean("update-checker", true);
        }

        @Override
        public String getLanguage(ConfigurationAccess access) {
            return access.getString("language", "en-US");
        }

        @Override
        public Set<String> getWorldNames(ConfigurationAccess access) {
            return access.getConfigSection("worlds");
        }
    };

    private final String path;

    GlobalConfigurationType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    // Métodos default que podem ser sobrescritos
    public boolean isEnabled(ConfigurationAccess reader) {
        throw new UnsupportedOperationException();
    }

    public boolean isVerbose(ConfigurationAccess reader) {
        throw new UnsupportedOperationException();
    }

    public boolean isUpdateCheckerEnabled(ConfigurationAccess reader) {
        throw new UnsupportedOperationException();
    }

    public String getLanguage(ConfigurationAccess reader) {
        throw new UnsupportedOperationException();
    }

    public Set<String> getWorldNames(ConfigurationAccess reader) {
        throw new UnsupportedOperationException();
    }
} 