package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GeneratorGui implements InventoryHolder {
    private final Inventory inv;

    public GeneratorGui() {
        inv = Bukkit.createInventory(this, 9 * ((DataManager.getChoosers().size() - 1) / 9 + 1), "Generators");
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
