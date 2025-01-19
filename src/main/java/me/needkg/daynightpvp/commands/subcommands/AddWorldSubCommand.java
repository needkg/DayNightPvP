package me.needkg.daynightpvp.commands.subcommands;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.commands.subcommands.core.ISubCommand;
import me.needkg.daynightpvp.commands.subcommands.validators.ArgsLengthValidator;
import me.needkg.daynightpvp.commands.subcommands.validators.PermissionValidator;
import me.needkg.daynightpvp.commands.subcommands.validators.WorldConfiguredValidator;
import me.needkg.daynightpvp.commands.subcommands.validators.WorldExistsValidator;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.services.PluginService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class AddWorldSubCommand implements ISubCommand {

    private final ConfigurationFile configurationFile;
    private final MessageManager messageManager;
    private final PluginService pluginService;
    private final List<CommandValidator> validators;

    public AddWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configurationFile = container.getConfigurationFile();
        this.messageManager = container.getMessageManager();
        this.pluginService = container.getPluginService();

        this.validators = new ArrayList<>();
        this.validators.add(new PermissionValidator("dnp.admin", messageManager));
        this.validators.add(new ArgsLengthValidator(2, "/dnp addworld <worldName>", messageManager));
        this.validators.add(new WorldExistsValidator(true, messageManager));
        this.validators.add(new WorldConfiguredValidator(configurationFile, messageManager, false));
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        addWorldToConfig(args[1]);
        pluginService.reloadPlugin();
        sender.sendMessage(messageManager.getMessage(MessageType.WORLD_EDITOR_MANAGEMENT_ADDED).replace("{0}", args[1]));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase();
            for (World world : Bukkit.getWorlds()) {
                if (World.Environment.NORMAL == world.getEnvironment()) {
                    String worldName = world.getName().toLowerCase();
                    if (worldName.startsWith(prefix)) {
                        suggestions.add(world.getName());
                    }
                }
            }
        }
        return suggestions;
    }

    private void addWorldToConfig(String worldName) {
        configurationFile.setValue("worlds." + worldName + ".day-night-duration.enabled", false);
        configurationFile.setValue("worlds." + worldName + ".day-night-duration.day-duration", 600);
        configurationFile.setValue("worlds." + worldName + ".day-night-duration.night-duration", 600);

        configurationFile.setValue("worlds." + worldName + ".pvp.automatic.enabled", true);
        configurationFile.setValue("worlds." + worldName + ".pvp.automatic.day-end", 12000);
        configurationFile.setValue("worlds." + worldName + ".pvp.keep-inventory", false);

        configurationFile.setValue("worlds." + worldName + ".boss-bar.enabled", false);

        configurationFile.setValue("worlds." + worldName + ".difficulty.enabled", false);
        configurationFile.setValue("worlds." + worldName + ".difficulty.day", "NORMAL");
        configurationFile.setValue("worlds." + worldName + ".difficulty.night", "HARD");

        configurationFile.setValue("worlds." + worldName + ".notifications.chat.day-night-change", true);
        configurationFile.setValue("worlds." + worldName + ".notifications.chat.no-pvp-message", true);
        configurationFile.setValue("worlds." + worldName + ".notifications.title.enabled", true);
        configurationFile.setValue("worlds." + worldName + ".notifications.title.fade-in", 20);
        configurationFile.setValue("worlds." + worldName + ".notifications.title.stay", 20);
        configurationFile.setValue("worlds." + worldName + ".notifications.title.fade-out", 20);
        configurationFile.setValue("worlds." + worldName + ".notifications.sound.enabled", true);
        configurationFile.setValue("worlds." + worldName + ".notifications.sound.day.type", "ENTITY_CHICKEN_AMBIENT");
        configurationFile.setValue("worlds." + worldName + ".notifications.sound.day.volume", 1.0);
        configurationFile.setValue("worlds." + worldName + ".notifications.sound.night.type", "ENTITY_GHAST_AMBIENT");
        configurationFile.setValue("worlds." + worldName + ".notifications.sound.night.volume", 1.0);

        configurationFile.setValue("worlds." + worldName + ".integrations.vault.lose-money.enabled", false);
        configurationFile.setValue("worlds." + worldName + ".integrations.vault.lose-money.only-at-night", true);
        configurationFile.setValue("worlds." + worldName + ".integrations.vault.lose-money.reward-killer", true);
        configurationFile.setValue("worlds." + worldName + ".integrations.grief-prevention.pvp-in-claims", false);

        configurationFile.saveFile();
    }
}
