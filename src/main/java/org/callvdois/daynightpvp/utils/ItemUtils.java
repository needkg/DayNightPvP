package org.callvdois.daynightpvp.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemUtils {

    public static ItemStack createCustomHead(String name, String id, String description, String url) {
        ItemStack item = getHead(url);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        itemMeta.setLocalizedName(id);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createCustomHeadExtendedDescription(String name, String id, List<String> description, String url) {
        ItemStack item = getHead(url);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        for (String part : description) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        itemMeta.setLocalizedName(id);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItem(String name, String id, String description, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        itemMeta.setLocalizedName(id);
        item.setItemMeta(itemMeta);
        return item;
    }

    private static ItemStack getHead(String value) {
        try {
            if (value == null) return null;

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta hMeta = (SkullMeta) head.getItemMeta();

            GameProfile profile = new GameProfile(UUID.randomUUID(), "head");
            profile.getProperties().put("textures", new Property("textures", value));
            Method mtd = hMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(hMeta, profile);

            head.setItemMeta(hMeta);
            return head;
        } catch (Exception e) {
            // ignore
        }

        return null;
    }

}
