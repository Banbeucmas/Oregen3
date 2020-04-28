package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.gui.InventoryClickHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        final ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        final InventoryHolder inventoryHolder = e.getInventory().getHolder();
        if (inventoryHolder instanceof InventoryClickHandler) {
            e.setCancelled(true);
            ((InventoryClickHandler) inventoryHolder).onClickHandle(e);
        }
    }
}
