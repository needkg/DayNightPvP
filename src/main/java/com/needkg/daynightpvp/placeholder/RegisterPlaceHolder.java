package com.needkg.daynightpvp.placeholder;

import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.utils.PluginUtils;

public class RegisterPlaceHolder {

    public void register() {
    if (PluginUtils.isPluginInstalled("PlaceholderAPI", ConfigManager.placeholderPlaceholders)) {
            if (ConfigManager.placeholderPlaceholders) {
                PvpStatus pvpStatus = new PvpStatus();
                pvpStatus.unregister();
                pvpStatus.register();
            }
        }
    }

}
