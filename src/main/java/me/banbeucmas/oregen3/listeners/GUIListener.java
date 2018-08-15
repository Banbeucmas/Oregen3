package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {
    private Oregen3 plugin = Oregen3.getPlugin();
    private FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onClick(InventoryClickEvent e){
        ItemStack item = e.getCurrentItem();
        Inventory inv = e.getView().getTopInventory();

        if(!inv.getTitle().equals(StringUtils.getColoredString(config.getString("messages.gui.title"))) ||
                item == null ||
                item.getType() == Material.AIR){
            return;
        }

        e.setCancelled(true);



    }
}
