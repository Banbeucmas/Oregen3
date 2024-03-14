package me.banbeucmas.oregen3.listener;

import me.banbeucmas.oregen3.gui.InventoryHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        final Inventory inventory = e.getView().getTopInventory();
        final InventoryHolder topInventory = inventory.getHolder();
        if (topInventory instanceof InventoryHandler) {
            final InventoryHandler holder = ((InventoryHandler) topInventory);
            if (e.getClickedInventory().equals(inventory))
                holder.onClick(e);
            else {
                holder.onPlayerInventoryClick(e);
            }
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        final InventoryHolder inventoryHolder = e.getInventory().getHolder();
        if (inventoryHolder instanceof InventoryHandler) {
            ((InventoryHandler) inventoryHolder).onClose(e);
        }
    }
}
