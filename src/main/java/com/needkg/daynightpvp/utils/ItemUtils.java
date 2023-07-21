package com.needkg.daynightpvp.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
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

    public static ItemStack createCustomBanner(String name, String id, String description, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemLore = new ArrayList<>();

        String[] descriptionParts = description.split("\\|");
        for (String part : descriptionParts) {
            itemLore.add(part);
        }

        itemMeta.setLore(itemLore);
        itemMeta.setLocalizedName(id);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(itemMeta);
        return item;
    }

    private static ItemStack getHead(String value) {
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

    public static ItemStack getBanner(String banner)
    {
        List<Pattern> patterns = new ArrayList<>();

        if (banner.equals("brazilianBanner")) {
            ItemStack greenBanner = new ItemStack(Material.GREEN_BANNER, 1);
            BannerMeta greenBannerMeta = (BannerMeta)greenBanner.getItemMeta();


            patterns.add(new Pattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE));
            patterns.add(new Pattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE));
            patterns.add(new Pattern(DyeColor.YELLOW, PatternType.RHOMBUS_MIDDLE));
            patterns.add(new Pattern(DyeColor.BLUE, PatternType.CIRCLE_MIDDLE));
            patterns.add(new Pattern(DyeColor.BLUE, PatternType.CIRCLE_MIDDLE));
            greenBannerMeta.setPatterns(patterns);

            greenBanner.setItemMeta(greenBannerMeta);

            return greenBanner;
        }
        if (banner.equals("euaBanner")) {
            ItemStack redBanner = new ItemStack(Material.RED_BANNER, 1);
            BannerMeta redBannerMeta = (BannerMeta)redBanner.getItemMeta();

            patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_SMALL));
            patterns.add(new Pattern(DyeColor.BLUE, PatternType.SQUARE_TOP_LEFT));

            redBannerMeta.setPatterns(patterns);

            redBanner.setItemMeta(redBannerMeta);

            return redBanner;
        }
        if (banner.equals("spanishBanner")) {
            ItemStack yellowBanner = new ItemStack(Material.YELLOW_BANNER, 1);
            BannerMeta yellowBannerMeta = (BannerMeta)yellowBanner.getItemMeta();

            patterns.add(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_TOP));
            patterns.add(new Pattern(DyeColor.BROWN, PatternType.MOJANG));
            patterns.add(new Pattern(DyeColor.BROWN, PatternType.FLOWER));
            patterns.add(new Pattern(DyeColor.YELLOW, PatternType.HALF_HORIZONTAL));
            patterns.add(new Pattern(DyeColor.RED, PatternType.STRIPE_RIGHT));
            patterns.add(new Pattern(DyeColor.RED, PatternType.STRIPE_LEFT));
            yellowBannerMeta.setPatterns(patterns);

            yellowBanner.setItemMeta(yellowBannerMeta);

            return yellowBanner;
        }
        if (banner.equals("customLanguageBanner")) {
            ItemStack whiteBanner = new ItemStack(Material.WHITE_BANNER, 1);
            BannerMeta whiteBannerMeta = (BannerMeta)whiteBanner.getItemMeta();


            patterns.add(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
            patterns.add(new Pattern(DyeColor.WHITE, PatternType.RHOMBUS_MIDDLE));
            patterns.add(new Pattern(DyeColor.BLACK, PatternType.STRIPE_DOWNLEFT));
            patterns.add(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
            patterns.add(new Pattern(DyeColor.BLACK, PatternType.TRIANGLES_BOTTOM));
            patterns.add(new Pattern(DyeColor.WHITE, PatternType.CURLY_BORDER));
            patterns.add(new Pattern(DyeColor.WHITE, PatternType.CURLY_BORDER));
            whiteBannerMeta.setPatterns(patterns);

            whiteBanner.setItemMeta(whiteBannerMeta);

            return whiteBanner;
        }
        return null;
    }

}
