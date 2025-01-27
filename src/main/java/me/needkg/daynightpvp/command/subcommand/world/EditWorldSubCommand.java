package me.needkg.daynightpvp.command.subcommand.world;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.command.base.SubCommand;
import me.needkg.daynightpvp.command.validator.permission.PermissionValidator;
import me.needkg.daynightpvp.command.validator.setting.SettingValidator;
import me.needkg.daynightpvp.command.validator.world.WorldConfiguredValidator;
import me.needkg.daynightpvp.command.validator.world.WorldExistsValidator;
import me.needkg.daynightpvp.configuration.enums.Message;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.service.plugin.PluginService;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.stream.Collectors;

public class EditWorldSubCommand implements SubCommand {

    private final ConfigurationFile configurationFile;
    private final MessageManager messageManager;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final PluginService pluginService;
    private final Map<String, SettingInfo> settingsMap;
    private final List<CommandValidator> validators;

    public EditWorldSubCommand(ConfigurationFile configurationFile, MessageManager messageManager, GlobalConfigurationManager globalConfigurationManager, PluginService pluginService) {
        this.configurationFile = configurationFile;
        this.messageManager = messageManager;
        this.globalConfigurationManager = globalConfigurationManager;
        this.pluginService = pluginService;
        this.settingsMap = Collections.unmodifiableMap(initializeSettingsMap());

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", messageManager));
        this.validators.add(new WorldExistsValidator(true, messageManager));
        this.validators.add(new WorldConfiguredValidator(configurationFile, messageManager, true));
        this.validators.add(new SettingValidator(settingsMap, messageManager));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 2) {
            showAvailableSettings(sender);
            return;
        }

        String worldName = args[1];
        String setting = args[2];
        SettingInfo settingInfo = settingsMap.get(setting);

        if (args.length == 3) {
            showSettingDetails(sender, worldName, setting, settingInfo);
            return;
        }

        updateSettingValue(sender, worldName, setting, settingInfo, args[3]);
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        if (!sender.hasPermission("dnp.admin")) {
            return new ArrayList<>();
        }

        if (args.size() == 1) {
            return filterStartsWith(globalConfigurationManager.getEnabledWorlds().stream()
                    .map(World::getName)
                    .collect(Collectors.toList()), args.get(0));
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

    private void showAvailableSettings(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_TITLE));
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
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_SETTING_DETAILS_TITLE).replace("{0}", setting));
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_SETTING_DETAILS_DESCRIPTION).replace("{0}", settingInfo.description));
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_SETTING_DETAILS_CURRENT).replace("{0}", String.valueOf(currentValue)));
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_SETTING_DETAILS_TYPE).replace("{0}", getTypeName(settingInfo.type)));

        if (settingInfo.type == Integer.class || settingInfo.type == Double.class) {
            sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_SETTING_DETAILS_RANGE)
                    .replace("{0}", String.valueOf(settingInfo.minValue))
                    .replace("{1}", String.valueOf(settingInfo.maxValue)));
        }

        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_SETTING_DETAILS_SUGGESTIONS)
                .replace("{0}", String.join(", ", settingInfo.suggestions)));
        sender.sendMessage("");
    }

    private void updateSettingValue(CommandSender sender, String worldName, String setting, SettingInfo settingInfo, String newValue) {
        try {
            Object parsedValue = parseValue(newValue, settingInfo.type);
            if (!isValidValue(parsedValue, settingInfo)) {
                sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_FEEDBACK_INVALID_VALUE)
                        .replace("{0}", newValue)
                        .replace("{1}", setting));
                return;
            }

            Object currentValue = getCurrentValue(worldName, setting);
            if (currentValue != null && currentValue.equals(parsedValue)) {
                sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_FEEDBACK_SAME_VALUE)
                        .replace("{0}", setting)
                        .replace("{1}", String.valueOf(parsedValue)));
                return;
            }

            saveAndReload(worldName, setting, parsedValue, sender);

        } catch (Exception e) {
            sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_FEEDBACK_INVALID_VALUE)
                    .replace("{0}", newValue)
                    .replace("{1}", setting));
        }
    }

    private void saveAndReload(String worldName, String setting, Object value, CommandSender sender) {
        String path = "worlds." + worldName + "." + setting;
        configurationFile.setValue(path, value);
        pluginService.reloadPlugin();

        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_FEEDBACK_SUCCESS)
                .replace("{0}", setting)
                .replace("{1}", String.valueOf(value)));
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
        return configurationFile.getFileContent().get("worlds." + worldName + "." + setting);
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

        // Day/Night Duration Settings
        map.put("day-night-duration.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable/disable custom day/night duration"));
        map.put("day-night-duration.day-duration", new SettingInfo(Integer.class, Arrays.asList("600", "1200", "1800", "3600"), 1, 86400, "Day duration in seconds"));
        map.put("day-night-duration.night-duration", new SettingInfo(Integer.class, Arrays.asList("600", "1200", "1800", "3600"), 1, 86400, "Night duration in seconds"));

        // PvP Settings
        map.put("pvp.automatic.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable/disable automatic PvP switching"));
        map.put("pvp.automatic.day-end", new SettingInfo(Integer.class, Arrays.asList("12000", "13000", "14000"), 1, 24000, "Time in ticks when day ends"));
        map.put("pvp.keep-inventory-on-pvp", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Keep inventory on PvP death"));

        // Boss Bar Settings
        map.put("boss-bar.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Show boss bar with time remaining"));

        // Difficulty Settings
        map.put("difficulty.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable/disable automatic difficulty changes"));
        map.put("difficulty.day", new SettingInfo(String.class,
                Arrays.stream(Difficulty.values()).map(Enum::name).collect(Collectors.toList()), "Server difficulty during daytime"));
        map.put("difficulty.night", new SettingInfo(String.class,
                Arrays.stream(Difficulty.values()).map(Enum::name).collect(Collectors.toList()), "Server difficulty during nighttime"));

        // Notification Settings
        map.put("notifications.chat.day-night-change", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Announce cycle changes in chat"));
        map.put("notifications.chat.no-pvp-warn", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Show PvP warning during day"));

        map.put("notifications.title.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable title notifications"));
        map.put("notifications.title.fade-in", new SettingInfo(Integer.class, Arrays.asList("10", "20", "30", "40"), 1, 100, "Title fade in duration in ticks"));
        map.put("notifications.title.stay", new SettingInfo(Integer.class, Arrays.asList("20", "40", "60", "80"), 1, 200, "Title stay duration in ticks"));
        map.put("notifications.title.fade-out", new SettingInfo(Integer.class, Arrays.asList("10", "20", "30", "40"), 1, 100, "Title fade out duration in ticks"));

        map.put("notifications.sound.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable sound notifications"));
        map.put("notifications.sound.day.type", new SettingInfo(String.class,
                Arrays.asList("ENTITY_CHICKEN_AMBIENT", "ENTITY_PLAYER_LEVELUP", "BLOCK_NOTE_BLOCK_PLING"), "Sound played at day start", Sound.class));
        map.put("notifications.sound.night.type", new SettingInfo(String.class,
                Arrays.asList("ENTITY_GHAST_AMBIENT", "ENTITY_WOLF_HOWL", "ENTITY_WITHER_SPAWN"), "Sound played at night start", Sound.class));
        map.put("notifications.sound.day.volume", new SettingInfo(Double.class, Arrays.asList("0.1", "0.5", "1.0"), 0.0, 1.0, "Day sound volume (0.0 to 1.0)"));
        map.put("notifications.sound.night.volume", new SettingInfo(Double.class, Arrays.asList("0.1", "0.5", "1.0"), 0.0, 1.0, "Night sound volume (0.0 to 1.0)"));

        // Integration Settings
        map.put("integrations.vault.lose-money.enabled", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Enable money loss on death"));
        map.put("integrations.vault.lose-money.only-at-night", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Restrict money loss to night time"));
        map.put("integrations.vault.lose-money.reward-killer", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Give lost money to killer"));

        map.put("integrations.grief-prevention.pvp-in-claims", new SettingInfo(Boolean.class, Arrays.asList("true", "false"), "Allow PvP in claimed land"));

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