package me.needkg.daynightpvp.config.validator;

import me.needkg.daynightpvp.config.LangManager;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangValidator {
    
    private final LangManager langManager;

    public LangValidator(LangManager langManager) {
        this.langManager = langManager;
    }

    public String getMessage(String path) {
        String text = langManager.getFileContent().getString(path);
        if (text == null) {
            return "Invalid message syntax";
        }
        
        return processColorCodes(text);
    }

    private String processColorCodes(String text) {
        String hexProcessed = processHexColors(text);
        return ChatColor.translateAlternateColorCodes('&', hexProcessed);
    }

    private String processHexColors(String text) {
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