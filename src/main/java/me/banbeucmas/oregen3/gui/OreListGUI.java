package me.banbeucmas.oregen3.gui;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.utils.item.ItemInfo;
import me.banbeucmas.oregen3.utils.item.Items;
import me.banbeucmas.oregen3.utils.PluginUtils;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OreListGUI {
    private Location location;
    private Oregen3 plugin = Oregen3.getPlugin();
    private FileConfiguration config = plugin.getConfig();

    private Inventory inv;
    public OreListGUI(Location location) {
        this.location = location;

        int size = 9;
        MaterialChooser mc = PluginUtils.getChooser(location);
        Map<Material, Double> chances = mc.getChances();
        if(chances.size() > size){
            size *= (mc.getChances().size() / size) + 1;
        }

        this.inv = Bukkit.createInventory(null, size,
                StringUtils.getColoredString(config.getString("messages.gui.title")));

        int slot = 0;
        for(Material material : chances.keySet()){
            ItemStack display = new ItemStack(material);
            ItemMeta meta = display.getItemMeta();
            double chance = chances.get(material);

            ItemInfo itemInfo = Items.itemByType(material);
            String name = StringUtils.getColoredString(config.getString("messages.gui.block.displayName")
                    .replace("%name%", itemInfo.getName()));
            List<String> lore = new ArrayList<>();
            for(String s : config.getStringList("messages.gui.block.lore")){
                s = s.replace("%chance%", Double.toString(chance));
                s = StringUtils.getColoredString(s);
                lore.add(s);
            }

            meta.setDisplayName(name);
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
