package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Generator implements InventoryHolder {
    private final Inventory inventory;

    public Generator(final MaterialChooser chooser) {
        inventory = Bukkit.createInventory(this, InventoryType.HOPPER, "Generator " + chooser.getId());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
