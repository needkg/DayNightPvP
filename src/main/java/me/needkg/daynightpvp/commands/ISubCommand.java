package me.needkg.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface ISubCommand {

    void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);

    default List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        return new ArrayList<>();
    }

    default List<String> filterStartsWith(List<String> list, String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return list;
        }
        return list.stream()
                .filter(str -> str.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }

    default List<String> filterContains(List<String> list, String search) {
        if (search == null || search.isEmpty()) {
            return list;
        }
        return list.stream()
                .filter(str -> str.toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }

    default List<String> filterByParts(List<String> list, String search) {
        if (search == null || search.isEmpty()) {
            return list;
        }
        return list.stream()
                .filter(str -> {
                    String[] parts = str.toLowerCase().split("\\.");
                    String searchLower = search.toLowerCase();
                    // Verifica se alguma parte contém a busca
                    for (String part : parts) {
                        if (part.contains(searchLower)) {
                            return true;
                        }
                    }
                    // Também verifica se o caminho completo contém a busca
                    return str.toLowerCase().contains(searchLower);
                })
                .collect(Collectors.toList());
    }
}