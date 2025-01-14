package com.needkg.daynightpvp.services;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.settings.MessageSettings;
import com.needkg.daynightpvp.di.DependencyContainer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateServices {

    private final MessageSettings messageSettings;

    public UpdateServices() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageSettings = container.getMessageSettings();
    }

    public void checkUpdate(PlayerJoinEvent event) {
        try {
            String currentVersion = DayNightPvP.getInstance().getDescription().getVersion();
            String latestVersion = verifyPluginVersion();

            if (!currentVersion.equals(latestVersion)) {
                TextComponent link = new TextComponent(messageSettings.getActionUpdateFoundClick());
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/daynightpvp-dynamic-pvp-for-day-night.102250/updates"));

                event.getPlayer().sendMessage(messageSettings.getFeedbackUpdateAvailable());
                event.getPlayer().sendMessage(messageSettings.getFeedbackUpdateCurrentVersion().replace("{0}", currentVersion));
                event.getPlayer().sendMessage(messageSettings.getFeedbackUpdateLatestVersion().replace("{0}", latestVersion));
                event.getPlayer().spigot().sendMessage(link);
            }
        } catch (IOException ex) {
            event.getPlayer().sendMessage(messageSettings.getFeedbackUpdateCheckFailed());
        }
    }

    private String verifyPluginVersion() throws IOException {
        HttpURLConnection connection = null;
        try {
            long random = (long) Math.floor(Math.random() * (Long.MAX_VALUE - 1 + 1) + 1);
            URL urlForGetRequest = new URL("https://api.spigotmc.org/legacy/update.php?resource=102250&t=" + random);
            connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String readLine;
                    while ((readLine = in.readLine()) != null) {
                        response.append(readLine);
                    }
                    return response.toString();
                }
            } else {
                throw new IOException("HTTP error code: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}