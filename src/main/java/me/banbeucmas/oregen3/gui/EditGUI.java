package me.banbeucmas.oregen3.gui;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditGUI implements InventoryHolder {
    private static final Inventory inv = Bukkit.createInventory(new EditGUI(), InventoryType.HOPPER, "Oregen3 editor");

    public static void create() {
        if (Oregen3.DEBUG) {
            System.out.println(inv.getHolder().toString());
        }

        final ItemStack generator = new ItemStack(Material.COAL_ORE);
        final ItemMeta generatorMeta = generator.getItemMeta();
        generatorMeta.setDisplayName("§rEdit generators (comming soon...)");
        inv.addItem(generator);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}