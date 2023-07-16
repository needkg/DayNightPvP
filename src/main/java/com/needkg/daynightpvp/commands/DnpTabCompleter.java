package com.needkg.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DnpTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission("dnp.admin")) {
            if (args.length == 1) {
                return filterOptions(args[0], Arrays.asList("pvpcontrol", "pvp", "reload", "perms", "day", "night"));
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("pvpcontrol")) {
                    return filterOptions(args[1], Arrays.asList("auto", "manual"));
                } else if (args[0].equalsIgnoreCase("pvp")) {
                    return filterOptions(args[1], Arrays.asList("on", "off"));
                }
            }
        } else {
            return new ArrayList<>();
        }
        return Collections.emptyList();
    }

    private List<String> filterOptions(String argumentoDigitado, List<String> options) {
        List<String> completions = new ArrayList<>();

        String argumentoDigitadoLower = argumentoDigitado.toLowerCase();

        for (String option : options) {
            if (option.toLowerCase().startsWith(argumentoDigitadoLower)) {
                completions.add(option);
            }
        }

        return completions;
    }

}
