package me.banbeucmas.oregen3.gui.editor.options;

import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RandomBlockList implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;

    public RandomBlockList(final MaterialChooser chooser) {
        inventory = Bukkit.createInventory(this, 9, "Edit random blocks (" + chooser.getId() + ')');
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {

    }

    @Override
    public void onCloseHandle(final InventoryCloseEvent event) {

    }
}
