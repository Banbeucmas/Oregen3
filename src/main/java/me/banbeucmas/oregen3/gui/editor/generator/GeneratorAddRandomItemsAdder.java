package me.banbeucmas.oregen3.gui.editor.generator;

import dev.lone.itemsadder.api.CustomBlock;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.GUICommons;
import me.banbeucmas.oregen3.gui.PagedInventory;
import me.banbeucmas.oregen3.util.ItemBuilder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GeneratorAddRandomItemsAdder extends PagedInventory<CustomBlock> {
    private static final List<CustomBlock> ITEMSADDER_BLOCKS;

    static {
        ITEMSADDER_BLOCKS = new ArrayList<>();
        for (String item : CustomBlock.getNamespacedIdsInRegistry()) {
            ITEMSADDER_BLOCKS.add(CustomBlock.getInstance(item));
        }
    }

    private Oregen3 plugin;
    private Generator generator;

    public GeneratorAddRandomItemsAdder(Oregen3 plugin, Generator generator) {
        this(plugin, generator, 1);
    }

    public GeneratorAddRandomItemsAdder(Oregen3 plugin, Generator generator, int page) {
        super(ITEMSADDER_BLOCKS, "ItemsAdder [page %page%]", page);
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public ItemStack getContentIcon(CustomBlock item) {
        return new ItemBuilder(item.getItemStack()).addLore("§eClick to add block").build();
    }

    @Override
    public void getContentAction(CustomBlock block, InventoryClickEvent event) {
        if (event.isLeftClick()) {
            addBlock(block, event, 1.0);
        } else if (event.isRightClick()) {
            HumanEntity player = event.getWhoClicked();
            player.sendMessage("Type chance of new block " + block + "in generator " + generator.getId());
            plugin.getChatListener().addChatAction(player, (chance) -> {
                if (chance.equals("cancel")) {
                    player.sendMessage("Cancelled");
                } else {
                    try {
                        addBlock(block, event, Double.parseDouble(chance));
                    } catch (NumberFormatException ex) {
                        player.sendMessage(chance + " is not a number...");
                    }
                }
                event.getWhoClicked().openInventory(new GeneratorAddRandomItemsAdder(plugin, generator, getPage()).getInventory());
            });
        }
    }

    private void addBlock(CustomBlock block, InventoryClickEvent event, double chance) {
        plugin.getConfigManager().setConfig(config -> config.set("generators." + generator.getId() + ".random.itemsadder-" + block.getNamespacedID(), chance));
        generator.addRandomBlock(block.getNamespacedID(), chance);
        event.getWhoClicked().openInventory(new GeneratorListRandomBlock(plugin, generator).getInventory());
    }

    @Override
    public void addOtherIcons() {
        setItemWithAction(getSize() - 1, GUICommons.GO_BACK_ICON,
                (event) -> event.getWhoClicked().openInventory(new GeneratorListRandomBlock(plugin, generator).getInventory()));
    }
}
