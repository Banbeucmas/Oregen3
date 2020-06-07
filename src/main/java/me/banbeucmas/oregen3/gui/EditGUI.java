package me.banbeucmas.oregen3.gui;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.gui.editor.GeneratorList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class EditGUI implements InventoryHolder, InventoryHandler {
    private static final Inventory inv = Bukkit.createInventory(new EditGUI(), InventoryType.HOPPER, "Oregen3 editor");

    static {
        final ItemStack generator = new ItemStack(Material.FURNACE);
        final ItemMeta generatorMeta = generator.getItemMeta();
        generatorMeta.setDisplayName("§rEdit generators");
        generatorMeta.setLore(Collections.singletonList("§rClick to edit generators"));
        generator.setItemMeta(generatorMeta);
        inv.addItem(generator);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public void onClick(final InventoryClickEvent event) {
        event.setCancelled(true);
        if (event.getSlot() == 0) {
            Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(new GeneratorList(1).getInventory()));
        }
    }
}