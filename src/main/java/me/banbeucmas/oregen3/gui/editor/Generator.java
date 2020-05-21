package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import me.banbeucmas.oregen3.gui.editor.options.Fallback;
import me.banbeucmas.oregen3.gui.editor.options.RandomBlockList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class Generator implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;
    private final MaterialChooser chooser;

    public Generator(final MaterialChooser chooser) {
        this.chooser = chooser;
        final String id = chooser.getId();
        inventory = Bukkit.createInventory(this, 9, "Generator: " + id);

        final ItemStack randomItem = new ItemStack(Material.STONE);
        final ItemMeta randomItemMeta = randomItem.getItemMeta();
        randomItemMeta.setDisplayName("§rEdit random blocks");
        randomItemMeta.setLore(Collections.singletonList("§rClick to edit random blocks"));
        randomItem.setItemMeta(randomItemMeta);
        inventory.setItem(0, randomItem);

        final ItemStack backItem = new ItemStack(Material.ARROW);
        final ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName("§rBack");
        backItemMeta.setLore(Collections.singletonList("§rClick to go back to generator list"));
        backItem.setItemMeta(backItemMeta);
        inventory.setItem(8, backItem);


        //TODO: Add other generator options
        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        final String prefix = "generators." + id + '.';
        if (config.isSet(prefix + "fallback")) {
            final ItemStack fallbackItem = new ItemStack(Material.COBBLESTONE);
            final ItemMeta fallbackMeta = fallbackItem.getItemMeta();
            fallbackMeta.setDisplayName("§rEdit fallback block");
            fallbackMeta.setLore(Collections.singletonList("§rClick to edit fallback block"));
            fallbackItem.setItemMeta(fallbackMeta);

            inventory.setItem(1, fallbackItem);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClickHandle(final InventoryClickEvent event) {
        switch (event.getSlot()) {
            case 0:
                event.getWhoClicked().openInventory(new RandomBlockList(
                        ((Generator) event.getInventory().getHolder()).chooser).getInventory());
                break;
            case 1:
                event.getWhoClicked().openInventory(new Fallback(
                        ((Generator) event.getInventory().getHolder()).chooser).getInventory());
                break;
            case 8:
                event.getWhoClicked().openInventory(new GeneratorList(1).getInventory());
        }
    }
}
