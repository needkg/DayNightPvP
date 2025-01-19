package me.needkg.daynightpvp.configuration.access;

import me.needkg.daynightpvp.configuration.file.LanguageFile;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageAccess {

    private final LanguageFile languageFile;

    public LanguageAccess(LanguageFile languageFile) {
        this.languageFile = languageFile;
    }

    public String getMessage(String path) {
        String text = languageFile.getFileContent().getString(path);
        if (text == null) {
            return "[DayNightPvP] Invalid message syntax";
        }

        return translateColorCodes(text);
    }

    private String translateColorCodes(String text) {
        String hexProcessed = translateHexColors(text);
        return ChatColor.translateAlternateColorCodes('&', hexProcessed);
    }

    private String translateHexColors(String text) {
        Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]{6})>");
        Matcher matcher = hexPattern.matcher(text);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            String formattedHexColor = formatHexColor(hexCode);
            matcher.appendReplacement(buffer, formattedHexColor);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    private String formatHexColor(String hexCode) {
        StringBuilder hexColor = new StringBuilder("§" + "x");
        for (char c : hexCode.toCharArray()) {
            hexColor.append("§").append(c);
        }
        return hexColor.toString();
    }
} 