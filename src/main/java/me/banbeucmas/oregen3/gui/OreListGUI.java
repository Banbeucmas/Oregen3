package me.banbeucmas.oregen3.gui;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.utils.PluginUtils;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OreListGUI {
    private static final Pattern CHANCE = Pattern.compile("%chance%", Pattern.LITERAL);

    private final Inventory inv;

    public OreListGUI(final Location location, final Player player) {
        int size = 9;
        final MaterialChooser mc = PluginUtils.getChooser(location);
        final Map<Material, Double> chances = mc.getChances();
        if (chances.size() > size) {
            size *= (mc.getChances().size() / size) + 1;
        }

        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        inv = Bukkit.createInventory(player, size,
                                     StringUtils.getColoredString(config.getString("messages.gui.title"), player));

        int slot = 0;
        for (final Map.Entry<Material, Double> entry : chances.entrySet()) {
            final ItemStack display = new ItemStack(entry.getKey());
            final ItemMeta meta = display.getItemMeta();
            final double chance = entry.getValue();

            final List<String> lore = new ArrayList<>();
            for (String s : config.getStringList("messages.gui.block.lore")) {
                s = CHANCE.matcher(s).replaceAll(Matcher.quoteReplacement(Double.toString(chance)));
                s = StringUtils.getColoredString(s, player);
                lore.add(s);
            }

            meta.setLore(lore);
            display.setItemMeta(meta);

            inv.setItem(slot, display);
            slot++;
        }
    }

    public OreListGUI(final UUID uuid, final Player player) {
        int size = 9;
        final MaterialChooser mc = PluginUtils.getChooser(uuid);
        final Map<Material, Double> chances = mc.getChances();
        if (chances.size() > size) {
            size *= (mc.getChances().size() / size) + 1;
        }

        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        inv = Bukkit.createInventory(player, size,
                                     StringUtils.getColoredString(config.getString("messages.gui.title"), player));

        int slot = 0;
        for (final Map.Entry<Material, Double> entry : chances.entrySet()) {
            final ItemStack display = new ItemStack(entry.getKey());
            final ItemMeta meta = display.getItemMeta();
            final double chance = entry.getValue();

            final List<String> lore = new ArrayList<>();
            for (String s : config.getStringList("messages.gui.block.lore")) {
                s = CHANCE.matcher(s).replaceAll(Matcher.quoteReplacement(Double.toString(chance)));
                s = StringUtils.getColoredString(s, player);
                lore.add(s);
            }

            meta.setLore(lore);
            display.setItemMeta(meta);

            inv.setItem(slot, display);
            slot++;
        }
    }

    public Inventory getInventory() {
        return inv;
    }
}
