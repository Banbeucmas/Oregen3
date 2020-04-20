package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class GeneratorGui implements InventoryHolder {
    private final Inventory inv;

    public GeneratorGui() {
        inv = Bukkit.createInventory(this, 9 * ((DataManager.getChoosers().size() - 1) / 9 + 1), "Generators");
        DataManager.getChoosers().forEach((id, chooser) -> {
            final ItemStack display = new ItemStack(chooser.getFallback());
            final ItemMeta meta = display.getItemMeta();

            meta.setDisplayName("Generator: " + id);
            meta.setLore(Collections.singletonList("§rClick to edit this generator (comming soon...)"));
            display.setItemMeta(meta);

            inv.addItem(display);
        });
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
