package com.needkg.daynightpvp.services;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.player.PlayerJoinEvent;
import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.files.LangFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateServices {

    private final LangFile langFile;

    public UpdateServices() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.langFile = container.getLangFile();
    }

    public void checkUpdate(PlayerJoinEvent event) {
        try {
            String currentVersion = DayNightPvP.getInstance().getDescription().getVersion();
            String latestVersion = verifyPluginVersion();

            if (!currentVersion.equals(latestVersion)) {
                TextComponent link = new TextComponent(langFile.getActionUpdateFoundClick());
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/daynightpvp-dynamic-pvp-for-day-night.102250/updates"));

                event.getPlayer().sendMessage(langFile.getFeedbackUpdateAvailable());
                event.getPlayer().sendMessage(langFile.getFeedbackUpdateCurrentVersion().replace("{0}", currentVersion));
                event.getPlayer().sendMessage(langFile.getFeedbackUpdateLatestVersion().replace("{0}", latestVersion));
                event.getPlayer().spigot().sendMessage(link);
            }
        } catch (IOException ex) {
            event.getPlayer().sendMessage(langFile.getFeedbackUpdateCheckFailed());
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