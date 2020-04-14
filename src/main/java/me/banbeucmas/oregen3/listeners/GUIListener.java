package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.gui.OreListGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        final ItemStack item = e.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;
        if (e.getInventory().getHolder() instanceof OreListGUI) {
            e.setCancelled(true);
        }
    }
}
