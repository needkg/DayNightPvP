package me.needkg.daynightpvp.application;

import me.needkg.daynightpvp.feature.config.settings.MessagesSettings;
import me.needkg.daynightpvp.feature.config.settings.PluginSettings;

public class ApplicationState {

    private MessagesSettings messagesSettings;
    private PluginSettings globalSettings;

    public ApplicationState(MessagesSettings messagesSettings, PluginSettings globalSettings) {
        this.messagesSettings = messagesSettings;
        this.globalSettings = globalSettings;
    }

    public PluginSettings getSettings() {
        return globalSettings;
    }

    public void setSettings(PluginSettings globalSettings) {
        this.globalSettings = globalSettings;
    }

    public MessagesSettings getMessages() {
        return messagesSettings;
    }

    public void setMessages(MessagesSettings messagesSettings) {
        this.messagesSettings = messagesSettings;
    }


    //
}
