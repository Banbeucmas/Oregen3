package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.gui.InventoryHandler;
import me.banbeucmas.oregen3.utils.BlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        if (!BlockUtils.isItem(e.getCurrentItem())) return;
        final InventoryHolder topInventory = e.getView().getTopInventory().getHolder();
        if (topInventory instanceof InventoryHandler) {
            if (e.getInventory().equals(topInventory))
                ((InventoryHandler) topInventory).onClick(e);
            else {
                ((InventoryHandler) topInventory).onPlayerInventoryClick(e);
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
