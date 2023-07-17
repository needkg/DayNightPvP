package com.needkg.daynightpvp.events;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.needkg.daynightpvp.config.ConfigManager;
import com.needkg.daynightpvp.service.UpdateChecker;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.util.UUID;

public class JoinEvent implements Listener {
    private final UpdateChecker updateChecker;

    public JoinEvent() {
        this.updateChecker = new UpdateChecker();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("dnp.admin") && ConfigManager.updateChecker) {
            updateChecker.checkUpdate(event);
        }
    }

}
