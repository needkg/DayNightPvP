package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.settings.MessageConfiguration;
import me.needkg.daynightpvp.configuration.settings.WorldConfiguration;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import me.needkg.daynightpvp.util.WorldUtil;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.stream.Collectors;

public class EditWorldSubCommand implements ISubCommand {

    private final ConfigurationManager configurationManager;
    private final WorldConfiguration worldConfiguration;
    private final MessageConfiguration messageConfiguration;
    private final PluginService pluginService;
    private final Map<String, SettingInfo> settingsMap;

    public EditWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configurationManager = container.getConfigManager();
        this.worldConfiguration = container.getWorldSettings();
        this.messageConfiguration = container.getMessageSettings();
        this.pluginService = container.getPluginServices();
        this.settingsMap = initializeSettingsMap();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage(messageConfiguration.getFeedbackErrorNoPermission());
            return;
        }

        if (args.length < 2) {
            showAvailableSettings(sender);
            return;
        }

        String worldName = args[1];
        if (!WorldUtil.isWorldValid(worldName)) {
            sender.sendMessage(messageConfiguration.getFeedbackWorldDoesNotExist().replace("{0}", worldName));
            return;
        }

        if (args.length == 2) {
            showAvailableSettings(sender);
            return;
        }

        handleSettingEdit(sender, args, worldName);
    }

    private void handleSettingEdit(CommandSender sender, String[] args, String worldName) {
        String setting = args[2];
        SettingInfo settingInfo = settingsMap.get(setting);

        if (settingInfo == null) {
            sender.sendMessage(messageConfiguration.getFeedbackEditWorldInvalidSetting().replace("{0}", setting));
            return;
        }

        if (args.length == 3) {
            showSettingDetails(sender, worldName, setting, settingInfo);
            return;
        }

        updateSettingValue(sender, worldName, setting, settingInfo, args[3]);
    }

    private void showAvailableSettings(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(messageConfiguration.getEditWorldTitle());
        sender.sendMessage("");

        Map<String, List<Map.Entry<String, SettingInfo>>> categorizedSettings = categorizeSetting();
        displayCategorizedSettings(sender, categorizedSettings);
    }

    private Map<String, List<Map.Entry<String, SettingInfo>>> categorizeSetting() {
        Map<String, List<Map.Entry<String, SettingInfo>>> categorizedSettings = new HashMap<>();
        for (Map.Entry<String, SettingInfo> entry : settingsMap.entrySet()) {
            String category = entry.getKey().split("\\.")[0];
            categorizedSettings.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
        }
        return categorizedSettings;
    }

    private void displayCategorizedSettings(CommandSender sender, Map<String, List<Map.Entry<String, SettingInfo>>> categorizedSettings) {
        categorizedSettings.forEach((category, settings) -> {
            sender.sendMessage("§8§l* §b" + formatCategoryName(category) + "§7:");
            settings.forEach(entry -> {
                String setting = entry.getKey();
                SettingInfo info = entry.getValue();
                sender.sendMessage("  §8- §7" + setting + " §e(" + getTypeName(info.type) + ")");
            });
            sender.sendMessage("");
        });
    }

    private void showSettingDetails(CommandSender sender, String worldName, String setting, SettingInfo settingInfo) {
        Object currentValue = getCurrentValue(worldName, setting);
        sender.sendMessage("");
        sender.sendMessage(messageConfiguration.getEditWorldSettingDetailsTitle().replace("{0}", setting));
        sender.sendMessage(messageConfiguration.getEditWorldSettingDescription().replace("{0}", settingInfo.description));
        sender.sendMessage(messageConfiguration.getEditWorldSettingCurrentValue().replace("{0}", String.valueOf(currentValue)));
        sender.sendMessage(messageConfiguration.getEditWorldSettingType().replace("{0}", getTypeName(settingInfo.type)));

        if (settingInfo.type == Integer.class || settingInfo.type == Double.class) {
            sender.sendMessage(messageConfiguration.getEditWorldSettingRange()
                    .replace("{0}", String.valueOf(settingInfo.minValue))
                    .replace("{1}", String.valueOf(settingInfo.maxValue)));
        }

        sender.sendMessage(messageConfiguration.getEditWorldSettingSuggestions()
                .replace("{0}", String.join(", ", settingInfo.suggestions)));
        sender.sendMessage("");
    }

    private void updateSettingValue(CommandSender sender, String worldName, String setting, SettingInfo settingInfo, String newValue) {
        try {
            Object parsedValue = parseValue(newValue, settingInfo.type);
            if (!isValidValue(parsedValue, settingInfo)) {
                sender.sendMessage(messageConfiguration.getFeedbackEditWorldInvalidValue()
                        .replace("{0}", newValue)
                        .replace("{1}", setting));
                return;
            }

            Object currentValue = getCurrentValue(worldName, setting);
            if (currentValue != null && currentValue.equals(parsedValue)) {
                sender.sendMessage(messageConfiguration.getFeedbackEditWorldSameValue()
                        .replace("{0}", setting)
                        .replace("{1}", String.valueOf(parsedValue)));
                return;
            }

            saveAndReload(worldName, setting, parsedValue, sender);

        } catch (Exception e) {
            sender.sendMessage(messageConfiguration.getFeedbackEditWorldInvalidValue()
                    .replace("{0}", newValue)
                    .replace("{1}", setting));
        }
    }

    private void saveAndReload(String worldName, String setting, Object value, CommandSender sender) {
        String path = "worlds." + worldName + "." + setting;
        configurationManager.setValue(path, value);
        pluginService.reloadPlugin();

        sender.sendMessage(messageConfiguration.getFeedbackEditWorldSuccess()
                .replace("{0}", setting)
                .replace("{1}", String.valueOf(value)));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        if (!sender.hasPermission("dnp.admin")) {
            return new ArrayList<>();
        }

        if (args.size() == 1) {
            return filterStartsWith(worldConfiguration.getValidWorldNames(), args.get(0));
        }

        if (args.size() == 2) {
            List<String> settings = new ArrayList<>(settingsMap.keySet());
            return filterByParts(settings, args.get(1));
        }

        if (args.size() == 3) {
            SettingInfo settingInfo = settingsMap.get(args.get(1));
            if (settingInfo != null) {
                return filterContains(settingInfo.getTabCompleteSuggestions(), args.get(2));
            }
        }

        return new ArrayList<>();
    }

    private String formatCategoryName(String category) {
        return Arrays.stream(category.split("-"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    private String getTypeName(Class<?> type) {
        if (type == Boolean.class) return "true/false";
        if (type == Integer.class) return "number";
        if (type == Double.class) return "decimal";
        if (type == Sound.class) return "sound";
        return type.getSimpleName();
    }

    private Object getCurrentValue(String worldName, String setting) {
        return configurationManager.getFileContent().get("worlds." + worldName + "." + setting);
    }

    private Object parseValue(String value, Class<?> type) {
        if (type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == Integer.class) return Integer.parseInt(value);
        if (type == Double.class) return Double.parseDouble(value);
        if (type == Sound.class) return Sound.valueOf(value.toUpperCase());
        return value;
    }

    private boolean isValidValue(Object value, SettingInfo settingInfo) {
        if (settingInfo.type == Integer.class) {
            int intValue = (Integer) value;
            return intValue >= settingInfo.minValue && intValue <= settingInfo.maxValue;
        }
        if (settingInfo.type == Double.class) {
            double doubleValue = (Double) value;
            return doubleValue >= settingInfo.minValue && doubleValue <= settingInfo.maxValue;
        }
        return true;
    }

    private Map<String, SettingInfo> initializeSettingsMap() {
        Map<String, SettingInfo> map = new HashMap<>();

        map.put("day-night-duration.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable/disable custom day/night duration"));
        map.put("day-night-duration.day-duration", new SettingInfo(Integer.class, Arrays.asList("600", "1200", "1800", "3600"), 1, 86400, "Day duration in seconds"));
        map.put("day-night-duration.night-duration", new SettingInfo(Integer.class, Arrays.asList("600", "1200", "1800", "3600"), 1, 86400, "Night duration in seconds"));

        map.put("automatic-pvp.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable/disable automatic PvP switching"));
        map.put("automatic-pvp.day-end", new SettingInfo(Integer.class, Arrays.asList("12000", "13000", "14000"), 1, 24000, "Time in ticks when day ends (12000 = 6:00 PM)"));
        map.put("pvp-settings.keep-inventory-when-killed-by-player", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Keep inventory on PvP death"));

        map.put("boss-bar.time-remaining", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Show countdown until next cycle"));

        map.put("automatic-difficulty.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable/disable automatic difficulty changes"));
        map.put("automatic-difficulty.day", new SettingInfo(String.class,
                Arrays.stream(Difficulty.values()).map(Enum::name).collect(Collectors.toList()), "Server difficulty during daytime"));
        map.put("automatic-difficulty.night", new SettingInfo(String.class,
                Arrays.stream(Difficulty.values()).map(Enum::name).collect(Collectors.toList()), "Server difficulty during nighttime"));

        map.put("notify-players.chat.day-night-starts", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Announce cycle changes in chat"));
        map.put("notify-players.chat.hit-another-player-during-day", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Show PvP warning during day"));

        map.put("notify-players.title.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable title notifications"));
        map.put("notify-players.title.fade-in", new SettingInfo(Integer.class, Arrays.asList("10", "20", "30", "40"), 1, 100, "Title fade in duration in ticks"));
        map.put("notify-players.title.stay", new SettingInfo(Integer.class, Arrays.asList("20", "40", "60", "80"), 1, 200, "Title stay duration in ticks"));
        map.put("notify-players.title.fade-out", new SettingInfo(Integer.class, Arrays.asList("10", "20", "30", "40"), 1, 100, "Title fade out duration in ticks"));

        map.put("notify-players.sound.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable sound notifications"));
        map.put("notify-players.sound.day.sound", new SettingInfo(String.class,
                Arrays.asList("ENTITY_CHICKEN_AMBIENT", "ENTITY_PLAYER_LEVELUP", "BLOCK_NOTE_BLOCK_PLING"), "Sound played at day start", Sound.class));
        map.put("notify-players.sound.night.sound", new SettingInfo(String.class,
                Arrays.asList("ENTITY_GHAST_AMBIENT", "ENTITY_WOLF_HOWL", "ENTITY_WITHER_SPAWN"), "Sound played at night start", Sound.class));
        map.put("notify-players.sound.day.volume", new SettingInfo(Double.class, Arrays.asList("0.1", "0.5", "1.0"), 0.0, 1.0, "Day sound volume (0.0 to 1.0)"));
        map.put("notify-players.sound.night.volume", new SettingInfo(Double.class, Arrays.asList("0.1", "0.5", "1.0"), 0.0, 1.0, "Night sound volume (0.0 to 1.0)"));

        map.put("vault.lose-money-on-death.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable money loss on death"));
        map.put("vault.lose-money-on-death.only-at-night", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Restrict money loss to night time"));
        map.put("vault.lose-money-on-death.killer-reward-money", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Give lost money to killer"));

        map.put("grief-prevention.pvp-in-land", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Allow PvP in claimed land"));

        return map;
    }

    private static class SettingInfo {
        final Class<?> type;
        final List<String> suggestions;
        final double minValue;
        final double maxValue;
        final String description;
        final Class<? extends Enum<?>> enumType;

        SettingInfo(Class<?> type, List<String> suggestions, String description) {
            this(type, suggestions, Double.MIN_VALUE, Double.MAX_VALUE, description);
        }

        SettingInfo(Class<?> type, List<String> suggestions, String description, Class<? extends Enum<?>> enumType) {
            this(type, suggestions, Double.MIN_VALUE, Double.MAX_VALUE, description, enumType);
        }

        SettingInfo(Class<?> type, List<String> suggestions, double minValue, double maxValue, String description) {
            this(type, suggestions, minValue, maxValue, description, null);
        }

        SettingInfo(Class<?> type, List<String> suggestions, double minValue, double maxValue, String description, Class<? extends Enum<?>> enumType) {
            this.type = type;
            this.suggestions = suggestions;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.description = description;
            this.enumType = enumType;
        }

        List<String> getSuggestions() {
            List<String> result = new ArrayList<>(suggestions);
            if (type == Integer.class) {
                result.add(0, String.format("<%d-%d>", (int) minValue, (int) maxValue));
            } else if (type == Double.class) {
                result.add(0, String.format("<%.1f-%.1f>", minValue, maxValue));
            }
            return result;
        }

        List<String> getTabCompleteSuggestions() {
            if (enumType != null) {
                return Arrays.stream(enumType.getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.toList());
            }
            return getSuggestions();
        }
    }
} 