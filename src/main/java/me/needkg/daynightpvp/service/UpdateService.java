package me.needkg.daynightpvp.service;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateService {

    private static final String SPIGOT_UPDATE_URL = "https://api.spigotmc.org/legacy/update.php";
    private static final String SPIGOT_RESOURCE_ID = "102250";
    private static final String PLUGIN_UPDATE_PAGE = "https://www.spigotmc.org/resources/daynightpvp-dynamic-pvp-for-day-night.102250/updates";

    private final MessageConfiguration messageConfiguration;
    private final String currentVersion;

    public UpdateService() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.messageConfiguration = container.getMessageSettings();
        this.currentVersion = DayNightPvP.getInstance().getDescription().getVersion();
    }

    public void checkUpdate(PlayerJoinEvent event) {
        try {
            String latestVersion = fetchLatestVersion();
            if (!currentVersion.equals(latestVersion)) {
                notifyUpdateAvailable(event.getPlayer(), latestVersion);
            }
        } catch (IOException ex) {
            event.getPlayer().sendMessage(messageConfiguration.getFeedbackUpdateCheckFailed());
        }
    }

    private void notifyUpdateAvailable(Player player, String latestVersion) {
        TextComponent updateLink = createUpdateLink();

        player.sendMessage(messageConfiguration.getFeedbackUpdateAvailable());
        player.sendMessage(messageConfiguration.getFeedbackUpdateCurrentVersion().replace("{0}", currentVersion));
        player.sendMessage(messageConfiguration.getFeedbackUpdateLatestVersion().replace("{0}", latestVersion));
        player.spigot().sendMessage(updateLink);
    }

    private TextComponent createUpdateLink() {
        TextComponent link = new TextComponent(messageConfiguration.getActionUpdateFoundClick());
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PLUGIN_UPDATE_PAGE));
        return link;
    }

    private String fetchLatestVersion() throws IOException {
        String urlString = String.format("%s?resource=%s&t=%d",
                SPIGOT_UPDATE_URL,
                SPIGOT_RESOURCE_ID,
                generateRandomToken());

        HttpURLConnection connection = createConnection(urlString);
        try {
            validateResponse(connection);
            return readResponse(connection);
        } finally {
            connection.disconnect();
        }
    }

    private long generateRandomToken() {
        return (long) Math.floor(Math.random() * (Long.MAX_VALUE - 1 + 1) + 1);
    }

    private HttpURLConnection createConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    private void validateResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}