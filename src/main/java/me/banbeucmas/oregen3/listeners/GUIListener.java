package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.gui.EditGUI;
import me.banbeucmas.oregen3.gui.OreListGUI;
import me.banbeucmas.oregen3.gui.editor.GeneratorList;
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
        if (inventoryHolder instanceof OreListGUI) {
            e.setCancelled(true);
        }
        else if (inventoryHolder instanceof EditGUI) {
            e.setCancelled(true);
            if (e.getSlot() == 0) {
                e.getWhoClicked().openInventory(new GeneratorList().getInventory());
            }
        }
        else if (inventoryHolder instanceof GeneratorList) {
            e.setCancelled(true);
        }
    }
}
