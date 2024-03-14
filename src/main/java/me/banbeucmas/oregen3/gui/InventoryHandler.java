package me.banbeucmas.oregen3.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryHandler extends InventoryHolder {
    default void onClick(final InventoryClickEvent event)                { }

    default void onPlayerInventoryClick(final InventoryClickEvent event) {
        event.setCancelled(true);
    }

    default void onClose(final InventoryCloseEvent event)                { }
}
