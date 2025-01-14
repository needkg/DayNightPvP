package com.needkg.daynightpvp.config.validator;

import com.needkg.daynightpvp.config.LangManager;
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
        if (text != null) {
            // Regex para encontrar códigos hexadecimais no formato <#RRGGBB>
            Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]{6})>");
            Matcher matcher = hexPattern.matcher(text);
            StringBuffer buffer = new StringBuffer();

            // Substitui cada código hexadecimal encontrado pelo formato §x§R§R§G§G§B§B
            while (matcher.find()) {
                String hexCode = matcher.group(1);
                StringBuilder hexColor = new StringBuilder("§x");
                for (char c : hexCode.toCharArray()) {
                    hexColor.append('§').append(c);
                }
                matcher.appendReplacement(buffer, hexColor.toString());
            }
            matcher.appendTail(buffer);

            // Substitui os códigos '&' por '§'
            text = ChatColor.translateAlternateColorCodes('&', buffer.toString());
            return text;
        }
        return "Invalid message syntax";
    }
} 