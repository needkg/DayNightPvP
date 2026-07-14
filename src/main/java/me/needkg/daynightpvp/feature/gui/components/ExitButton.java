package me.needkg.daynightpvp.feature.gui.components;

import org.bukkit.inventory.ItemStack;

import me.needkg.daynightpvp.shared.player.PlayerHead;
import net.kyori.adventure.text.Component;

public class ExitButton implements Button {

    private final int slot;

    public ExitButton(int slot) {
        this.slot = slot;
    }

    @Override
    public Component name() {
        return Component.text("Fechar");
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public ItemStack render() {
        return PlayerHead.getByUrl(name(), "5b30507783c37db3a3092cad043e57951aa8b4c6ea9acc47d604b7eb5aea028");
    }
    
}
