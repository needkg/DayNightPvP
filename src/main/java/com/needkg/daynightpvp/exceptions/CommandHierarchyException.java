package com.needkg.daynightpvp.exceptions;

import java.nio.file.Path;

import com.needkg.daynightpvp.command.AbstractCommand;

public class CommandHierarchyException extends RuntimeException {

    public CommandHierarchyException(AbstractCommand commandName, AbstractCommand parentName) {
        super("Command '" + commandName.getName() + "' already has parent '" + parentName.getName() + "'.");
    }

}