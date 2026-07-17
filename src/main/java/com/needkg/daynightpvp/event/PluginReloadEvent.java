package com.needkg.daynightpvp.event;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

public class PluginReloadEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final CommandSender sender;

    public PluginReloadEvent(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
