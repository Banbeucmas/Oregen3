package me.banbeucmas.oregen3.gui.editor.options;

import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import me.banbeucmas.oregen3.gui.editor.Generator;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Collections;

public class RandomBlockList implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;
    private final MaterialChooser chooser;

    public RandomBlockList(final MaterialChooser chooser) {
        this.chooser = chooser;
        inventory = Bukkit.createInventory(this, 9, "Edit random blocks (" + chooser.getId() + ')');
        final DecimalFormat format = new DecimalFormat("#.##");
        chooser.getChances().forEach((mat, chance) -> {
            final ItemStack item = new ItemStack(mat);
            final ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(Collections.singletonList("§r" + format.format(chance)));
            item.setItemMeta(itemMeta);

            inventory.addItem(item);
        });
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {

    }

    @Override
    public void onCloseHandle(final InventoryCloseEvent event) {
        event.getPlayer().openInventory(new Generator(
                ((RandomBlockList) event.getInventory().getHolder()).chooser).getInventory());
    }
}
