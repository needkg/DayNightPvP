package me.needkg.daynightpvp.command.impl.subcommand.world;

import me.needkg.daynightpvp.command.base.CommandValidator;
import me.needkg.daynightpvp.command.base.SubCommand;
import me.needkg.daynightpvp.command.validator.args.ArgsLengthValidator;
import me.needkg.daynightpvp.command.validator.permission.PermissionValidator;
import me.needkg.daynightpvp.command.validator.world.WorldConfiguredValidator;
import me.needkg.daynightpvp.command.validator.world.WorldExistsValidator;
import me.needkg.daynightpvp.configuration.emun.Message;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.core.DependencyContainer;
import me.needkg.daynightpvp.service.plugin.PluginService;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddWorldSubCommand implements SubCommand {

    private final ConfigurationFile configurationFile;
    private final MessageManager messageManager;
    private final PluginService pluginService;
    private final GlobalConfigurationManager globalConfigurationManager;
    private final List<CommandValidator> validators;

    public AddWorldSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.configurationFile = container.getConfigurationFile();
        this.messageManager = container.getMessageManager();
        this.pluginService = container.getPluginService();
        this.globalConfigurationManager = container.getGlobalConfigurationManager();

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
        sender.sendMessage(messageManager.getMessage(Message.WORLD_EDITOR_MANAGEMENT_ADDED).replace("{0}", args[1]));
    }

    @Override
    public List<CommandValidator> getValidators() {
        return validators;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {

        if (args.size() == 1) {
            return Bukkit.getWorlds().stream()
                    .filter(world -> !globalConfigurationManager.getEnabledWorlds().contains(world))
                    .filter(world -> world.getEnvironment() == World.Environment.NORMAL)
                    .map(World::getName)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
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