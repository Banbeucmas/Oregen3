package me.banbeucmas.oregen3.gui.editor.options;

import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Fallback implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;

    public Fallback(final MaterialChooser chooser) {
        inventory = Bukkit.createInventory(this, InventoryType.HOPPER, "Edit fallback block (" + chooser.getId() + ')');

        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§rSet fallback item");
        itemMeta.setLore(Arrays.asList("§rPut the item on the first slot (the left of this paper) to set the block,", "or click the required item in your inventory to set the item."));
        item.setItemMeta(itemMeta);

        inventory.setItem(1, item);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {
    }
}
