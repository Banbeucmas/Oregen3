package me.banbeucmas.oregen3.gui;

import com.cryptomorin.xseries.XMaterial;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.util.ItemBuilder;
import me.banbeucmas.oregen3.util.StringParser;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorMaterialList extends PagedInventory<Map.Entry<String, Double>> {
    private static final Pattern CHANCE = Pattern.compile("%chance%", Pattern.LITERAL);

    private Oregen3 plugin;
    private OfflinePlayer player;

    public GeneratorMaterialList(Oregen3 plugin, OfflinePlayer player, Location location) {
        this(plugin, player, plugin.getUtils().getChosenGenerator(location));
    }

    public GeneratorMaterialList(Oregen3 plugin, OfflinePlayer player, World world) {
        this(plugin, player, plugin.getUtils().getChosenGenerator(player.getUniqueId(), world));
    }

    public GeneratorMaterialList(Oregen3 plugin, OfflinePlayer player, Generator generator) {
        super(new ArrayList<>(generator.getRandom().entrySet()),
                plugin.getStringParser().getColoredString(plugin.getConfig().getString("messages.gui.title"), player),
                true);
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public ItemStack getContentIcon(Map.Entry<String, Double> content) {
        ItemBuilder display = new ItemBuilder(XMaterial.matchXMaterial(content.getKey()).orElse(XMaterial.STONE).parseItem());
        double chance = content.getValue();
        for (final String s : plugin.getConfig().getStringList("messages.gui.block.lore")) {
            display.addLore(plugin.getStringParser().getColoredString(
                    CHANCE.matcher(s).replaceAll(Matcher.quoteReplacement(StringParser.DOUBLE_FORMAT.format(chance))), player));
        }
        return display.build();
    }

    @Override
    public void getContentAction(Map.Entry<String, Double> content, InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void addOtherIcons() { }
}
