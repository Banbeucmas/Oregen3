package me.banbeucmas.oregen3.gui.editor.options.generator;

import com.cryptomorin.xseries.XMaterial;
import dev.lone.itemsadder.api.CustomStack;
import io.github.rysefoxx.inventory.plugin.content.IntelligentItem;
import io.github.rysefoxx.inventory.plugin.content.InventoryContents;
import io.github.rysefoxx.inventory.plugin.content.InventoryProvider;
import io.github.rysefoxx.inventory.plugin.pagination.Pagination;
import io.github.rysefoxx.inventory.plugin.pagination.RyseInventory;
import io.github.rysefoxx.inventory.plugin.pagination.SlotIterator;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.editor.ListGenerator;
import me.banbeucmas.oregen3.manager.items.ItemBuilder;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateRandomItemsAdder {

    protected static final ItemStack BORDER = new ItemBuilder(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem()).setName("§0").build();

    private static final List<CustomStack> ITEMSADDER_BLOCKS;

    static {
        ITEMSADDER_BLOCKS = new ArrayList<>();
        for (String item : CustomStack.getNamespacedIdsInRegistry()) {
            CustomStack stack = CustomStack.getInstance(item);
            if (stack.isBlock()) ITEMSADDER_BLOCKS.add(stack);
        }
    }

    public static void open(Player player, Generator generator, Oregen3 plugin) {
        RyseInventory oraxenUI = RyseInventory.builder()
                .title("ItemsAdder [p.1]")
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        Pagination pagination = contents.pagination();
                        pagination.setItemsPerPage(36);
                        pagination.iterator(SlotIterator.builder().startPosition(1, 0).type(SlotIterator.SlotIteratorType.HORIZONTAL).build());

                        contents.fillRow(0, BORDER);
                        contents.set(0, IntelligentItem.of(new ItemBuilder(XMaterial.ARROW.parseMaterial())
                                .setName("§e <- Go Back ")
                                .build(), event -> CreateRandomBlock.open(player, generator, plugin)));
                        contents.fillRow(45, BORDER);

                        Configuration config = plugin.getConfig();

                        ListGenerator.movePage(player, contents, pagination);

                        for (CustomStack item : ITEMSADDER_BLOCKS) {
                            ItemStack stack = item.getItemStack();
                            ItemMeta meta = stack.getItemMeta();
                            if (meta != null) {
                                meta.setLore(Arrays.asList("", "§eClick to add oraxen block", ""));
                            }
                            stack.setItemMeta(meta);
                            pagination.addItem(IntelligentItem.of(stack, event -> {
                                // TODO: Save config with comments
                                config.set("generators." + generator.getId() + ".random.itemsadder-" + item.getId(), 1.0);
                                plugin.saveConfig();
                                plugin.reload();
                                ListRandomBlock.open(player, generator, plugin);
                            }));
                        }
                    }
                })
                .build(plugin);
        oraxenUI.open(player);
    }
}
