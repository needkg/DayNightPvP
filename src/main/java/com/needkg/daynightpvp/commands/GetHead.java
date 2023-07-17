package com.needkg.daynightpvp.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;
import java.util.UUID;

public class GetHead {

    public static ItemStack getHead(String value) {
        try {
            if (value == null) return null;

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta hMeta = (SkullMeta) head.getItemMeta();

            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", value));
            Method mtd = hMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(hMeta, profile);

            head.setItemMeta(hMeta);
            return head;
        } catch (Exception e) {
            // handle error
        }

        return null;
    }
}
