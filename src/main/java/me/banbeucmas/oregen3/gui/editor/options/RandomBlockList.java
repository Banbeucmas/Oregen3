package me.banbeucmas.oregen3.gui.editor.options;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import me.banbeucmas.oregen3.gui.editor.GeneratorMenu;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class RandomBlockList implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;
    private final Generator chooser;

    public RandomBlockList(final Generator chooser) {
        this.chooser = chooser;
        inventory    = Bukkit.createInventory(this, 9, "Edit random blocks (" + chooser.getId() + ')');
        chooser.getChances().forEach((mat, chance) -> {
            final ItemStack item = new ItemStack(mat);
            final ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(Collections.singletonList("§r" + StringUtils.DOUBLE_FORMAT.format(chance)));
            item.setItemMeta(itemMeta);

            inventory.addItem(item);
        });
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClick(final InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onClose(final InventoryCloseEvent event) {
        Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getPlayer().openInventory(new GeneratorMenu(chooser).getInventory()));
    }
}
