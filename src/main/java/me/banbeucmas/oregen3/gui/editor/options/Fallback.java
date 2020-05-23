package me.banbeucmas.oregen3.gui.editor.options;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Fallback implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;
    private final MaterialChooser chooser;
    private static ItemStack item;

    public Fallback(final MaterialChooser chooser) {
        this.chooser = chooser;
        inventory    = Bukkit.createInventory(this, InventoryType.HOPPER, "Edit fallback block (" + chooser.getId() + ')');
        inventory.setItem(1, item);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static void create() {
        item = new ItemStack(Material.PAPER);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§rSet fallback item");
        itemMeta.setLore(Arrays.asList("§rPut the item on the first slot (the left of this paper) to set the block,", "§ror click the required item in your inventory to set the item."));
        item.setItemMeta(itemMeta);
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {
        if (event.getClickedInventory().getHolder() instanceof Fallback && event.getSlot() == 1) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onCloseHandle(final InventoryCloseEvent event) {
        final ItemStack item = event.getInventory().getItem(0);
        if (item == null) return;
        Oregen3.getPlugin().getConfig().set("generators." + chooser.getId() + ".fallback", item.getType().name());
    }
}
