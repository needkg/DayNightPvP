package me.needkg.daynightpvp.shared.player;

import java.net.URI;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import com.destroystokyo.paper.profile.PlayerProfile;

import net.kyori.adventure.text.Component;

public class PlayerHead {

    public static ItemStack getByUrl(Component itemName, String base64) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());

        PlayerTextures textures = profile.getTextures();
        try {
            URI uri = new URI("http://textures.minecraft.net/texture/" + base64);
            textures.setSkin(uri.toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
        profile.setTextures(textures);

        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setPlayerProfile(profile);
        meta.customName(itemName);

        head.setItemMeta(meta);


        return head;
    }

}
