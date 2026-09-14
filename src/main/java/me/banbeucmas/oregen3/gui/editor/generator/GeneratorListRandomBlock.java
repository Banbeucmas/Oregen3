package me.banbeucmas.oregen3.gui.editor.generator;

import com.cryptomorin.xseries.XMaterial;
import dev.lone.itemsadder.api.CustomBlock;
import io.th0rgal.oraxen.api.OraxenItems;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.GUICommons;
import me.banbeucmas.oregen3.gui.PagedInventory;
import me.banbeucmas.oregen3.gui.editor.GeneratorMenu;
import me.banbeucmas.oregen3.util.ItemBuilder;
import me.banbeucmas.oregen3.util.StringParser;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class GeneratorListRandomBlock extends PagedInventory<Map.Entry<String, Double>> {
    private Oregen3 plugin;
    private Generator generator;

    public GeneratorListRandomBlock(Oregen3 plugin, Generator generator) {
        this(plugin, generator, 1);
    }

    public GeneratorListRandomBlock(Oregen3 plugin, Generator generator, int page) {
        super(new ArrayList<>(generator.getRandom().entrySet()), "Edit random blocks (" + generator.getId() + ") [page %page%]", page);
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public ItemStack getContentIcon(Map.Entry<String, Double> content) {
        String material = content.getKey();
        ItemBuilder item;
        if (material.startsWith("oraxen-")) {
            if (Bukkit.getPluginManager().isPluginEnabled("Oraxen")) {
                item = new ItemBuilder(OraxenItems.getItemById(material.substring(7)).build());
            }
            else {
                item = new ItemBuilder(XMaterial.PAPER.parseItem()).setName(material.substring(7));
            }
        }
        else if (material.startsWith("itemsadder-")) {
            if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
                item = new ItemBuilder(CustomBlock.getInstance(material.substring(11)).getItemStack());
            }
            else {
                item = new ItemBuilder(XMaterial.PAPER.parseItem()).setName(material.substring(11));
            }
        }
        else {
            item = new ItemBuilder(XMaterial.matchXMaterial(material).orElse(XMaterial.COBBLESTONE).parseItem());
        }

        item.addLore("§7Chances: §6" + StringParser.DOUBLE_FORMAT.format(content.getValue()) + "%",
                "",
                "§8[§2Left-Click§8]§e to edit chances",
                "§8[§2Right-Click§8]§e to delete");
        return item.build();
    }

    @Override
    public void getContentAction(Map.Entry<String, Double> content, InventoryClickEvent event) {
        if (event.isLeftClick()) {
            event.getWhoClicked().sendMessage("§7Type in chat to set chance percentage, type §ccancel §7to cancel");
            plugin.getChatListener().addChatAction(event.getWhoClicked(), (msg) -> {
                if (msg.equals("cancel")) {
                    event.getWhoClicked().sendMessage("Cancelled");
                }
                else {
                    try {
                        double chance = Double.parseDouble(msg);
                        plugin.getConfigManager().setConfig(config -> config.set("generators." + generator.getId() + ".random." + content.getKey(), chance));
                        generator.setRandomBlock(content.getKey(), chance);
                    } catch (NumberFormatException exception) {
                        event.getWhoClicked().sendMessage(msg + " is not a number...");
                    }
                }
                event.getWhoClicked().openInventory(new GeneratorListRandomBlock(plugin, generator, getPage()).getInventory());
            });
        }
        else if (event.isRightClick()) {
            plugin.getConfigManager().setConfig(config -> config.set("generators." + generator.getId() + ".random." + content.getKey(), null));
            generator.removeRandomBlock(content.getKey());
            event.getWhoClicked().openInventory(new GeneratorListRandomBlock(plugin, generator, getPage()).getInventory());
        }
    }

    @Override
    public void addOtherIcons() {
        setItemWithAction(getSize() - 9, new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
                .setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19")
                .setName("§2Total chances: " + generator.getTotalChance())
                .addLore("§7Click to add new random block")
                .build(), event -> event.getWhoClicked().openInventory(new GeneratorAddRandomBlock(plugin, generator).getInventory()));
        int slot = getSize() - 9;
        if (Bukkit.getPluginManager().isPluginEnabled("Oraxen")) {
            setItemWithAction(slot, new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
                            .setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhiZGM3YTFkNmNmZjc2YTkyNTU2NTJkMzE2NTUzMjI4NWFjYzNhOWQxYzBmMTJmMzljYTAwNzc2OWE3ZWExNCJ9fX0=")
                            .setName("§2Total chances: " + generator.getTotalChance())
                            .addLore("§7Click to add random Oraxen block")
                            .build(),
                    event -> event.getWhoClicked().openInventory(new GeneratorAddRandomOraxen(plugin, generator).getInventory()));
            slot++;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
            setItemWithAction(slot, new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
                            .setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhiZGM3YTFkNmNmZjc2YTkyNTU2NTJkMzE2NTUzMjI4NWFjYzNhOWQxYzBmMTJmMzljYTAwNzc2OWE3ZWExNCJ9fX0=")
                            .setName("§2Total chances: " + generator.getTotalChance())
                            .addLore("§7Click to add random ItemsAdder block")
                            .build(),
                    event -> event.getWhoClicked().openInventory(new GeneratorAddRandomItemsAdder(plugin, generator).getInventory()));
        }
        setItemWithAction(getSize() - 1, GUICommons.GO_BACK_ICON,
                (event) -> event.getWhoClicked().openInventory(new GeneratorMenu(plugin, generator).getInventory()));
    }
}
