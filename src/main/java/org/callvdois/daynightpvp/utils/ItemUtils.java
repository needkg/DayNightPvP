package org.callvdois.daynightpvp.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.callvdois.daynightpvp.DayNightPvP;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemUtils {

    private static final NamespacedKey customTagKey = new NamespacedKey(DayNightPvP.getInstance(), "id");

    public static ItemStack createCustomHead(String name, String id, String description, String url) {
        ItemStack item = getHead(url);
        ItemStack itemWithId = setID(item, id);

        ItemMeta itemMeta = itemWithId.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createCustomHeadExtendedDescription(String name, String id, List<String> description, String url) {
        ItemStack item = getHead(url);
        ItemStack itemWithId = setID(item, id);

        ItemMeta itemMeta = itemWithId.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        for (String part : description) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createItem(String name, String id, String description, Material material) {
        ItemStack item = new ItemStack(material);
        ItemStack itemWithId = setID(item, id);
        ItemMeta itemMeta = itemWithId.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    private static ItemStack setID(ItemStack item, String value) {
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(customTagKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
        return item;
    }

    public static String getID(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(customTagKey, PersistentDataType.STRING);
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
