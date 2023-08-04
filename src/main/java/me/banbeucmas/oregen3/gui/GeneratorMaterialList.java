package me.banbeucmas.oregen3.gui;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.util.PluginUtils;
import me.banbeucmas.oregen3.util.StringUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorMaterialList implements InventoryHolder, InventoryHandler {
    private static final Pattern CHANCE = Pattern.compile("%chance%", Pattern.LITERAL);
    private Oregen3 plugin;
    private final Inventory inv;

    public GeneratorMaterialList(Oregen3 plugin, final Location location, final OfflinePlayer player) {
        this.plugin = plugin;
        int size = 9;
        final Generator mc = plugin.getUtils().getChosenGenerator(location);
        final Map<String, Double> chances = mc.getRandom();
        if (chances.size() > size) {
            size *= chances.size() / size + 1;
        }

        final FileConfiguration config = plugin.getConfig();
        inv = Bukkit.createInventory(this, size,
                                     plugin.getStringUtils().getColoredString(config.getString("messages.gui.title"), player));

        chances.forEach((key, value) -> {
            final ItemStack display = new ItemStack(Material.valueOf(key));
            final ItemMeta meta = display.getItemMeta();
            final double chance = value;

            final List<String> lore = new ArrayList<>();
            for (final String s : config.getStringList("messages.gui.block.lore")) {
                lore.add(plugin.getStringUtils().getColoredString(
                        CHANCE.matcher(s).replaceAll(Matcher.quoteReplacement(Double.toString(chance))), player));
            }

            meta.setLore(lore);
            display.setItemMeta(meta);

            inv.addItem(display);
        });
    }

    public GeneratorMaterialList(final World world, final OfflinePlayer player) {
        int size = 9;
        final Generator mc = plugin.getUtils().getChosenGenerator(player.getUniqueId(), world);
        final Map<String, Double> chances = mc.getRandom();
        if (chances.size() > size) {
            size *= chances.size() / size + 1;
        }

        final FileConfiguration config = plugin.getConfig();
        inv = Bukkit.createInventory(this, size,
                plugin.getStringUtils().getColoredString(config.getString("messages.gui.title"), player));

        chances.forEach((key, value) -> {
            final ItemStack display = new ItemStack(Material.valueOf(key));
            final ItemMeta meta = display.getItemMeta();
            final double chance = value;

            final List<String> lore = new ArrayList<>();
            for (final String s : config.getStringList("messages.gui.block.lore")) {
                lore.add(plugin.getStringUtils().getColoredString(
                        CHANCE.matcher(s).replaceAll(Matcher.quoteReplacement(Double.toString(chance))), player));
            }

            meta.setLore(lore);
            display.setItemMeta(meta);
            inv.addItem(display);
        });
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    @Override
    public void onClick(final InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
