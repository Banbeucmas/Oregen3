package me.banbeucmas.oregen3.gui.editor.generator;

import com.cryptomorin.xseries.XMaterial;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.GUICommons;
import me.banbeucmas.oregen3.gui.PagedInventory;
import me.banbeucmas.oregen3.util.ItemBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratorEditSound extends PagedInventory<Sound> {
    private static final List<Sound> SOUNDS = new ArrayList<>(Arrays.asList(Sound.values()));

    private Oregen3 plugin;
    private Generator generator;

    public GeneratorEditSound(Oregen3 plugin, Generator generator) {
        super(SOUNDS, "Choose Music [page %page%]");
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public ItemStack getContentIcon(Sound sound) {
        return new ItemBuilder(XMaterial.MUSIC_DISC_FAR.parseItem())
                .setName("§r" + sound)
                .addLore(
                        "§8[§2Shift Left-Click§8]§e play at pitch 1",
                        "§8[§2Shift Right-Click§8]§e play at pitch 2",
                        "",
                        "§eClick to set this sound"
                )
                .build();
    }

    @Override
    public void getContentAction(Sound sound, InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.isShiftClick() && event.isLeftClick()) {
            player.playSound(player.getLocation(), sound, 1f, 1f);
            event.setCancelled(true);
            return;
        }
        if (event.isShiftClick() && event.isRightClick()) {
            player.playSound(player.getLocation(), sound, 1f, 2f);
            event.setCancelled(true);
            return;
        }
        plugin.getConfigManager().setConfig(config -> config.set("generators." + generator.getId() + ".sound.name", sound.toString()));
        generator.setSound(sound);
        event.getWhoClicked().openInventory(new GeneratorSound(plugin, generator).getInventory());
    }

    @Override
    public void addOtherIcons() {
        setItemWithAction(getSize() - 1, GUICommons.GO_BACK_ICON,
                (event) -> event.getWhoClicked().openInventory(new GeneratorSound(plugin, generator).getInventory()));
    }
}
