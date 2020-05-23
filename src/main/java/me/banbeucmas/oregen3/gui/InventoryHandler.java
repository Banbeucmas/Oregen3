package me.banbeucmas.oregen3.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface InventoryHandler {
    default void onClick(final InventoryClickEvent event)                { }

    default void onPlayerInventoryClick(final InventoryClickEvent event) { }

    default void onClose(final InventoryCloseEvent event)                { }
}
