package me.banbeucmas.oregen3.gui.editor.options;

import com.cryptomorin.xseries.XMaterial;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import me.banbeucmas.oregen3.gui.editor.Generator;
import me.banbeucmas.oregen3.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class Fallback implements InventoryHolder, InventoryHandler {
    private final Inventory inventory;
    private final MaterialChooser chooser;
    private static final ItemStack confirmItem = XMaterial.GREEN_STAINED_GLASS_PANE.parseItem();

    static {
        final ItemMeta confirmItemMeta = Objects.requireNonNull(confirmItem).getItemMeta();
        confirmItemMeta.setDisplayName("§aConfirm selection");
        confirmItemMeta.setLore(Arrays.asList("§rClick here to confirm your block", "§rand update the config"));
        confirmItem.setItemMeta(confirmItemMeta);
    }

    public Fallback(final MaterialChooser chooser) {
        this.chooser = chooser;
        inventory    = Bukkit.createInventory(this, InventoryType.HOPPER, "Edit fallback block (" + chooser.getId() + ')');
        inventory.setItem(4, confirmItem);

        final ItemStack fallbackItem = new ItemStack(chooser.getFallback());
        final ItemMeta fallbackItemMeta = fallbackItem.getItemMeta();
        fallbackItemMeta.setDisplayName("§rCurrent fallback block");
        fallbackItemMeta.setLore(Arrays.asList("§rClick the required item in your inventory to set the fallback block.", "§rOr click here to remove the the fallback block"));
        fallbackItem.setItemMeta(fallbackItemMeta);
        inventory.setItem(0, fallbackItem);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void onClick(final InventoryClickEvent event) {
        event.setCancelled(true);
        final ItemStack item = event.getInventory().getItem(0);
        if (event.getSlot() == 4 && BlockUtils.isItem(item)) {
            final Oregen3 plugin = Oregen3.getPlugin();
            plugin.getConfig().set("generators." + chooser.getId() + ".fallback", item.getType().name());
            plugin.updateConfig();
            Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(new Generator(chooser).getInventory()));
        }
    }

    @Override
    public void onPlayerInventoryClick(final InventoryClickEvent event) {
        event.setCancelled(true);
        inventory.setItem(0, event.getCurrentItem());
        Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> event.getWhoClicked().openInventory(inventory));
    }

    @Override
    public void onClose(final InventoryCloseEvent event) {
        final ItemStack item = event.getInventory().getItem(0);
        if (item == null) return;
        final Oregen3 plugin = Oregen3.getPlugin();
        plugin.getConfig().set("generators." + chooser.getId() + ".fallback", item.getType().name());
        plugin.updateConfig();
    }
}
