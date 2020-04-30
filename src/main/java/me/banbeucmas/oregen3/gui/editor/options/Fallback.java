package me.banbeucmas.oregen3.gui.editor.options;

import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Fallback implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;

    public Fallback(final MaterialChooser chooser) {
        inventory = Bukkit.createInventory(this, InventoryType.HOPPER, "Edit fallback block (" + chooser.getId() + ')');

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {
    }
}
