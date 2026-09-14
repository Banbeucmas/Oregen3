package me.banbeucmas.oregen3.gui.editor.generator;

import com.cryptomorin.xseries.XMaterial;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.GUICommons;
import me.banbeucmas.oregen3.gui.PagedInventory;
import me.banbeucmas.oregen3.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GeneratorAddWorld extends PagedInventory<World> {
    private Oregen3 plugin;
    private Generator generator;

    public GeneratorAddWorld(Oregen3 plugin, Generator generator) {
        this(plugin, generator, 1);
    }

    public GeneratorAddWorld(Oregen3 plugin, Generator generator, int page) {
        super(Bukkit.getWorlds(), "Edit world ("+ generator.getId() + ") [page %page%]", page);
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public ItemStack getContentIcon(World world) {
        return new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
                .setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM0NjdhNTMxOTc4ZDBiOGZkMjRmNTYyODVjNzI3MzRkODRmNWVjODhlMGI0N2M0OTMyMzM2Mjk3OWIzMjNhZiJ9fX0=")
                .setName("§2" + world.getName())
                .addLore("§eClick to add world")
                .build();
    }

    @Override
    public void getContentAction(World world, InventoryClickEvent event) {
        plugin.getConfigManager().setConfig(config -> {
            List<String> worlds = config.getStringList("generators." + generator.getId() + ".world.list");
            worlds.add(world.getName());
            config.set("generators." + generator.getId() + ".world.list", worlds);
        });
        generator.getWorldList().add(world.getName());
        event.getWhoClicked().openInventory(new GeneratorListWorld(plugin, generator).getInventory());
    }

    @Override
    public void addOtherIcons() {
        setItemWithAction(getSize() - 1, GUICommons.GO_BACK_ICON,
                (event) -> event.getWhoClicked().openInventory(new GeneratorListWorld(plugin, generator).getInventory()));
        setItemWithAction(getSize() - 9, new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
                .setSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM0NjdhNTMxOTc4ZDBiOGZkMjRmNTYyODVjNzI3MzRkODRmNWVjODhlMGI0N2M0OTMyMzM2Mjk3OWIzMjNhZiJ9fX0=")
                .setName("§7Type world name instead")
                .addLore("§eClick to type new world name")
                .build(), event -> {
            event.getWhoClicked().sendMessage("Type name of world to add in generator " + generator.getId() + ", or cancel to abort");
            plugin.getChatListener().addChatAction(event.getWhoClicked(), world -> {
                if (world.equals("cancel")) {
                    event.getWhoClicked().sendMessage("Cancelled");
                }
                else {
                    plugin.getConfigManager().setConfig(config -> {
                        List<String> worlds = config.getStringList("generators." + generator.getId() + ".world.list");
                        worlds.add(world);
                        config.set("generators." + generator.getId() + ".world.list", worlds);
                    });
                    generator.getWorldList().add(world);
                }
                event.getWhoClicked().openInventory(new GeneratorListWorld(plugin, generator).getInventory());
            });
        });
        setItemWithAction(getSize() - 8, new ItemBuilder(generator.isWorldBlacklist() ? XMaterial.LIME_STAINED_GLASS_PANE.parseItem() : XMaterial.RED_STAINED_GLASS_PANE.parseItem())
                .setName("§7World Blacklist: " + (generator.isWorldBlacklist() ? "§aEnabled" : "§cDisabled"))
                .addLore("§eClick to " + (generator.isWorldBlacklist() ? "enable" : "disable"))
                .build(), event -> {
            plugin.getConfigManager().setConfig(config -> config.set("generators." + generator.getId() + ".world.blacklist", !generator.isWorldBlacklist()));
            generator.setWorldBlacklist(!generator.isWorldBlacklist());
            event.getWhoClicked().openInventory(new GeneratorAddWorld(plugin, generator, getPage()).getInventory());
        });
    }
}
