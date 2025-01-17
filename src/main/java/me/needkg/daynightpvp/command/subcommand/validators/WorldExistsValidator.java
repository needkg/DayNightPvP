package me.needkg.daynightpvp.command.subcommand.validators;

import me.needkg.daynightpvp.command.subcommand.base.CommandValidator;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.configuration.message.WorldEditorMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class WorldExistsValidator implements CommandValidator {
    private final WorldEditorMessages worldEditorMessages;
    private final boolean shouldExist;
    private final SystemMessages systemMessages;

    public WorldExistsValidator(WorldEditorMessages worldEditorMessages, boolean shouldExist, SystemMessages systemMessages) {
        this.worldEditorMessages = worldEditorMessages;
        this.shouldExist = shouldExist;
        this.systemMessages = systemMessages;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        boolean exists = Bukkit.getWorlds().contains(Bukkit.getWorld(args[1]));
        return shouldExist ? exists : !exists;
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return systemMessages.getIncorrectCommandMessage()
                    .replace("{0}", "/dnp editworld <world> <setting> <value>");
        } else {
            return worldEditorMessages.getWorldNotExistsMessage().replace("{0}", args[1]);
        }

    }
} 