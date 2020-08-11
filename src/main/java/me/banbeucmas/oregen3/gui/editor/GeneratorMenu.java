package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
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

public class GeneratorMenu implements InventoryHolder, InventoryHandler {
    private static final ItemStack randomItem = new ItemStack(Material.STONE);
    private static final ItemStack backItem = new ItemStack(Material.ARROW);
    private static final ItemStack fallbackItem = new ItemStack(Material.COBBLESTONE);
    private final Inventory inventory;
    private final Generator chooser;

    static {
        final ItemMeta randomItemMeta = randomItem.getItemMeta();
        randomItemMeta.setDisplayName("§rEdit random blocks");
        randomItemMeta.setLore(Collections.singletonList("§rClick to edit random blocks"));
        randomItem.setItemMeta(randomItemMeta);

        final ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName("§rBack");
        backItemMeta.setLore(Collections.singletonList("§rClick to go back to generator list"));
        backItem.setItemMeta(backItemMeta);

        final ItemMeta fallbackMeta = fallbackItem.getItemMeta();
        fallbackMeta.setDisplayName("§rEdit fallback block");
        fallbackMeta.setLore(Collections.singletonList("§rClick to edit fallback block"));
        fallbackItem.setItemMeta(fallbackMeta);
    }

    public GeneratorMenu(final Generator chooser) {
        this.chooser = chooser;
        final String id = chooser.getId();

        inventory = Bukkit.createInventory(this, 9, "Generator: " + id);
        inventory.setItem(0, randomItem);
        inventory.setItem(8, backItem);

        //TODO: Add other generator options
        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        final String prefix = "generators." + id + '.';
        if (config.isSet(prefix + "fallback")) {
            inventory.setItem(1, fallbackItem);
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClick(final InventoryClickEvent event) {
        event.setCancelled(true);
        switch (event.getSlot()) {
            case 0:
                event.getWhoClicked().openInventory(new RandomBlockList(
                        ((GeneratorMenu) event.getInventory().getHolder()).chooser).getInventory());
                break;
            case 1:
                event.getWhoClicked().openInventory(new Fallback(
                        ((GeneratorMenu) event.getInventory().getHolder()).chooser).getInventory());
                break;
            case 8:
                event.getWhoClicked().openInventory(new GeneratorList(1).getInventory());
        }
    }
}
