package me.banbeucmas.oregen3.gui.editor;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.EditGUI;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Map;

public class GeneratorList implements InventoryHolder, InventoryHandler {
    private static ItemStack exitItem;
    private final Inventory inv;
    private final long page;

    public GeneratorList(final long page) {
        this.page = page;
        final Map<String, MaterialChooser> choosers = DataManager.getChoosers();
        final int size = 9 * (choosers.size() / 9 + 1);

        inv = Bukkit.createInventory(this, size, "Generators");

        inv.setItem(size - 1, exitItem);

        if (page != 1) {
            final ItemStack lastPage = new ItemStack(Material.ARROW);
            final ItemMeta lastPageMeta = lastPage.getItemMeta();
            lastPageMeta.setDisplayName("§rPage " + (page + 1));
            lastPage.setItemMeta(lastPageMeta);
            inv.setItem(size - 3, lastPage);
        }

        if (choosers.size() / 52 > 0) {
            final ItemStack nextPage = new ItemStack(Material.ARROW);
            final ItemMeta nextPageMeta = nextPage.getItemMeta();
            nextPageMeta.setDisplayName("§rPage " + (page + 1));
            nextPage.setItemMeta(nextPageMeta);
            inv.setItem(size - 2, nextPage);
        }

        choosers.forEach((id, chooser) -> {
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
        final int slot = event.getSlot();
        final int size = event.getInventory().getSize();
        if (slot == size - 3) {
            Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(new GeneratorList(page - 1).inv));
        }
        else if (slot == size - 2) {
            Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(new GeneratorList(page + 1).inv));
        }
        else if (slot == size - 1) {
            Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(new EditGUI().getInventory()));
        }
        else {
            Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(new Generator(DataManager.getChoosers().get(
                    ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
                                                                                                                 .getInventory()));
        }
    }

    public static void create() {
        exitItem = new ItemStack(Material.BARRIER);
        final ItemMeta exitItemMeta = exitItem.getItemMeta();
        exitItemMeta.setDisplayName("§rBack");
        exitItem.setItemMeta(exitItemMeta);
    }
}
