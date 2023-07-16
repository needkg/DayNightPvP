package com.needkg.daynightpvp.commands;

import com.needkg.daynightpvp.DayNightPvP;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.config.FilesManager;
import com.needkg.daynightpvp.config.LangManager;
import com.needkg.daynightpvp.events.RegisterEvents;
import com.needkg.daynightpvp.service.ControlService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DnpCommand implements CommandExecutor {

    private final ControlService controlService = new ControlService();
    private final FilesManager filesManager = new FilesManager();
    private final RegisterEvents registerEvents = new RegisterEvents();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage("§8[§e☀§8] §3DayNightPvP §8- §7v" + DayNightPvP.plugin.getDescription().getVersion());
            return true;
        }
        if (args.length >= 1) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                filesManager.reloadAllFiles(DayNightPvP.plugin);
                sender.sendMessage(LangManager.reloadedConfig);
                registerEvents.register();
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("perms")) {
                sender.sendMessage("");
                sender.sendMessage(LangManager.dnpCommandPermissions);
                sender.sendMessage("");
                sender.sendMessage(LangManager.dnpCommandPermissionDNPADMIN);
                return true;
            }
            if (args[0].equalsIgnoreCase("pvpcontrol")) {
                if (args.length == 2 && args[1].equalsIgnoreCase("auto")) {
                    if (!ControlService.pvpControlStatus) {
                        sender.sendMessage(LangManager.setPvpControlAuto);
                        ControlService.pvpControlStatus = true;
                    } else {
                        sender.sendMessage(LangManager.dnpCommandPvpControlAlreadyEnabled);
                    }
                    return true;
                }
                if (args.length == 2 && args[1].equalsIgnoreCase("manual")) {
                    if (ControlService.pvpControlStatus) {
                        sender.sendMessage(LangManager.setPvpControlManual);
                        sender.sendMessage(LangManager.warnPvpControl2);
                        ControlService.pvpControlStatus = false;
                        controlService.startCheckPvpControl();
                    } else {
                        sender.sendMessage(LangManager.dnpCommandPvpControlAlreadyDisabled);
                    }
                    return true;
                }
                sender.sendMessage("");
                sender.sendMessage(LangManager.currentStatusPvpControl + controlService.checkPvpControlStatus());
                if (sender instanceof Player) {
                    if (((Player) sender).getWorld().getPVP()) {
                        sender.sendMessage(LangManager.currentStatusPvp + LangManager.onMessage);
                    } else {
                        sender.sendMessage(LangManager.currentStatusPvp + LangManager.offMessage);
                    }
                }
                sender.sendMessage(LangManager.dnpCommandPvp);
                return true;
            }
            if (args[0].equalsIgnoreCase("pvp")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 2 && args[1].equalsIgnoreCase("on")) {
                        ControlService.pvpControlStatus = false;
                        player.getWorld().setPVP(true);
                        sender.sendMessage(LangManager.pvpSetOn);
                        controlService.startCheckPvpControl();
                        controlService.sendWarnMessage();
                        return true;
                    }
                    if (args.length == 2 && args[1].equalsIgnoreCase("off")) {
                        ControlService.pvpControlStatus = false;
                        player.getWorld().setPVP(false);
                        sender.sendMessage(LangManager.pvpSetOff);
                        controlService.startCheckPvpControl();
                        controlService.sendWarnMessage();
                        return true;
                    }
                    sender.sendMessage("");
                    sender.sendMessage(LangManager.currentStatusPvpControl + controlService.checkPvpControlStatus());
                    if (((Player) sender).getWorld().getPVP()) {
                        sender.sendMessage(LangManager.currentStatusPvp + LangManager.onMessage);
                    } else {
                        sender.sendMessage(LangManager.currentStatusPvp + LangManager.offMessage);
                    }
                    sender.sendMessage(LangManager.dnpCommandPvpControl);
                    return true;
                }
                sender.sendMessage("This command can only be done by players");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("day")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.getWorld().setTime(0);
                    return true;
                }
                sender.sendMessage("This command can only be done by players");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("night")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.getWorld().setTime(ConfigManager.dayEnd);
                    return true;
                }
                sender.sendMessage("This command can only be done by players");
                return true;
            }
            sender.sendMessage("");
            sender.sendMessage(LangManager.dnpCommandMessage);
            sender.sendMessage("");
            sender.sendMessage(LangManager.dnpCommandDnp);
            sender.sendMessage(LangManager.dnpCommandReload);
            sender.sendMessage(LangManager.dnpCommandPerms);
            sender.sendMessage(LangManager.dnpCommandPvpControl);
            sender.sendMessage(LangManager.dnpCommandPvp);
            sender.sendMessage(LangManager.dnpCommandDayNight);
        } else {
            sender.sendMessage("");
            sender.sendMessage(LangManager.dnpCommandMessage);
            sender.sendMessage("");
            sender.sendMessage(LangManager.dnpCommandDnp);
            sender.sendMessage(LangManager.dnpCommandReload);
            sender.sendMessage(LangManager.dnpCommandPerms);
            sender.sendMessage(LangManager.dnpCommandPvpControl);
            sender.sendMessage(LangManager.dnpCommandPvp);
            sender.sendMessage(LangManager.dnpCommandDayNight);
        }
        return false;
    }
}
