package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.gui.InventoryClickHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class GeneratorList implements InventoryHolder, InventoryClickHandler {
    private final Inventory inv;

    public GeneratorList() {
        inv = Bukkit.createInventory(this, 9 * ((DataManager.getChoosers().size() - 1) / 9 + 1), "Generators");
        DataManager.getChoosers().forEach((id, chooser) -> {
            final ItemStack display = new ItemStack(chooser.getFallback());
            final ItemMeta meta = display.getItemMeta();

            meta.setDisplayName("§r" + id);
            meta.setLore(Collections.singletonList("§rClick to edit this generator."));
            display.setItemMeta(meta);

            inv.addItem(display);
        });
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {
        event.getWhoClicked().openInventory(new Generator(DataManager.getChoosers().get(
                        ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
                        .getInventory());
    }
}
