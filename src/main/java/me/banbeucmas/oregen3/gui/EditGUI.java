package me.banbeucmas.oregen3.gui;

import me.banbeucmas.oregen3.gui.editor.GeneratorList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditGUI implements InventoryHolder, InventoryClickHandler {
    private static final Inventory inv = Bukkit.createInventory(new EditGUI(), InventoryType.HOPPER, "Oregen3 editor");

    public static void create() {
        final ItemStack generator = new ItemStack(Material.COAL_ORE);
        final ItemMeta generatorMeta = generator.getItemMeta();
        generatorMeta.setDisplayName("§rEdit generators (comming soon...)");
        generator.setItemMeta(generatorMeta);
        inv.addItem(generator);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {
        if (event.getSlot() == 0) {
            event.getWhoClicked().openInventory(new GeneratorList().getInventory());
        }
    }
}