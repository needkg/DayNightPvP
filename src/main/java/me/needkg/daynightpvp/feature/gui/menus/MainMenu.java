package me.needkg.daynightpvp.feature.gui.menus;

import java.util.List;

import org.bukkit.event.inventory.InventoryType;

import me.needkg.daynightpvp.feature.gui.components.Button;
import me.needkg.daynightpvp.feature.gui.components.ExitButton;
import net.kyori.adventure.text.Component;

public class MainMenu implements Menu {

    @Override
    public Component title() {
        return Component.text("Menu Principal");
    }

    @Override
    public InventoryType type() {
        return InventoryType.CHEST;
    }

    @Override
    public int size() {
        return MenuSize.THREE.getSize();
    }

    @Override
    public List<Button> buttons() {
        return List.of(
            new ExitButton(8)
        );
    }

}
