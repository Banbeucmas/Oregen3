package me.banbeucmas.oregen3.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface InventoryHandler {
    default void onClickHandle(final InventoryClickEvent event) { }

    default void onCloseHandle(final InventoryCloseEvent event) { }
}
