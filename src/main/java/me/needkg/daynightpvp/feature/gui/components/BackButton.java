package me.needkg.daynightpvp.feature.gui.components;

import org.bukkit.inventory.ItemStack;

import me.needkg.daynightpvp.shared.player.PlayerHead;
import net.kyori.adventure.text.Component;

public class BackButton implements Button {

    private final int slot;

    public BackButton(int slot) {
        this.slot = slot;
    }

    @Override
    public Component name() {
        return Component.text("Voltar");
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public ItemStack render() {
        return PlayerHead.getByUrl(name(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGUxZGZjMTFhODM3MTExZDIyYjAwMWExNDQ2MWY5YTdmYzA5MzUyMmY4OGM1OGZhZWZkNmFkZWZmY2Q0ZTlhYiJ9fX0=");
    }

}
