package me.banbeucmas.oregen3.gui.editor.generator;

import com.cryptomorin.xseries.XMaterial;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.gui.GUICommons;
import me.banbeucmas.oregen3.gui.InventoryHandler;
import me.banbeucmas.oregen3.gui.editor.GeneratorMenu;
import me.banbeucmas.oregen3.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GeneratorSound implements InventoryHandler {
    private Oregen3 plugin;
    private Inventory inv;
    private Generator generator;

    public GeneratorSound(Oregen3 plugin, Generator generator) {
        this.plugin = plugin;
        this.generator = generator;
        inv = Bukkit.createInventory(this, 9, "Music editor (" + generator.getId() + ")");
        inv.setItem(8, GUICommons.GO_BACK_ICON);
        inv.setItem(0, GUICommons.createGeneratorIcon(generator).build());
        inv.setItem(1, GUICommons.BORDER_ICON);
        inv.setItem(2, new ItemBuilder(XMaterial.JUKEBOX.parseItem())
                .setName("§rEdit Sound")
                .addLore("", "§7Current sound:§e " + generator.getSound(), "", "§eClick to edit sound", "")
                .build());
        inv.setItem(3, new ItemBuilder(XMaterial.NOTE_BLOCK.parseItem())
                .setName("§rEdit Volume")
                .addLore("", "§7Current volume:§e " + generator.getSoundVolume(), "", "§eClick to set volume", "")
                .build());
        inv.setItem(4, new ItemBuilder(XMaterial.PAPER.parseItem())
                .setName("§rEdit Pitch")
                .addLore("", "§7Current pitch:§e " + generator.getSoundPitch(), "", "§eClick to set pitch", "")
                .build());
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        HumanEntity player = event.getWhoClicked();
        switch (event.getSlot()) {
            case 2:
                player.openInventory(new GeneratorEditSound(plugin, generator).getInventory());
                break;
            case 3:
                player.closeInventory();
                player.sendMessage("§7Type in chat volume for generator sound, §7type §ccancel §7to cancel");
                plugin.getChatListener().addChatAction(player, (msg) -> {
                    if (msg.equals("cancel")) {
                        player.sendMessage("Cancelled");
                    }
                    else {
                        try {
                            float volume = Float.parseFloat(msg);
                            plugin.getConfigManager().setConfig(config -> plugin.getConfig().set("generators." + generator + ".sound.volume", volume));
                            generator.setSoundVolume(volume);
                        } catch (NumberFormatException ex) {
                            player.sendMessage(msg + " is not a number...");
                        }
                    }
                    player.openInventory(new GeneratorSound(plugin, generator).getInventory());
                });
                break;
            case 4:
                player.closeInventory();
                player.sendMessage("§7Type in chat pitch for generator sound, §7type §ccancel §7to cancel");
                plugin.getChatListener().addChatAction(player, (msg) -> {
                    if (msg.equals("cancel")) {
                        player.sendMessage("Cancelled");
                    }
                    else {
                        try {
                            float pitch = Float.parseFloat(msg);
                            plugin.getConfigManager().setConfig(config -> plugin.getConfig().set("generators." + generator + ".sound.pitch", pitch));
                            generator.setSoundPitch(pitch);
                        } catch (NumberFormatException ex) {
                            player.sendMessage(msg + " is not a number...");
                        }
                    }
                    player.openInventory(new GeneratorSound(plugin, generator).getInventory());
                });
                break;
            case 8:
                player.openInventory(new GeneratorMenu(plugin, generator).getInventory());
                break;
            default:
                event.setCancelled(true);
        }
    }
}
