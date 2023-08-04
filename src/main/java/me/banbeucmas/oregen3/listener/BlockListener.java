package me.banbeucmas.oregen3.listener;

import com.cryptomorin.xseries.XBlock;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.handler.event.BlockEventHandler;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import static me.banbeucmas.oregen3.util.BlockUtils.*;

public class BlockListener implements Listener {
    private Oregen3 plugin;
    private final FileConfiguration config;
    private final BlockEventHandler eventHandler;

    public BlockListener(final Oregen3 plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.eventHandler = plugin.getBlockEventHandler();
    }

    private boolean canGenerateBlock(final Block src, final Block to) {
        final Material material = src.getType();
        for (final BlockFace face : FACES) {
            final Block check = to.getRelative(face);
            if (plugin.getBlockUtils().isBlock(check)
                    && (XBlock.isWater(material))
                    && config.getBoolean("mode.waterBlock")) {
                return true;
            }
            else if (plugin.getBlockUtils().isBlock(check)
                    && (XBlock.isLava(material))
                    && config.getBoolean("mode.lavaBlock")) {
                return true;
            }
        }
        return false;
    }

    /*
    Checks for Water + Lava, block will use another method to prevent confusion
     */
    private boolean canGenerate(final Material material, final Block b) {
        final boolean check = XBlock.isWater(material);
        for (final BlockFace face : FACES) {
            final Material type = b.getRelative(face).getType();
            if (((check && XBlock.isLava(type)) || (!check && XBlock.isWater(type)))
                    && config.getBoolean("mode.waterLava")) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onOre(final BlockFromToEvent event) {
        final Block source = event.getBlock();
        final Block to = event.getToBlock();
        final Material sourceMaterial = source.getType();
        final Material toMaterial = to.getType();
        final World world = source.getWorld();

        if (XBlock.isAir(sourceMaterial))
            return;

        if (config.getBoolean("global.generators.world.enabled", false)
                && config.getBoolean("global.generators.world.blacklist", true)
                == config.getStringList("global.generators.world.list").contains(to.getWorld().getName())) {
            return;
        }

        if (XBlock.isWater(sourceMaterial) || XBlock.isLava(sourceMaterial)) {
            if ((XBlock.isAir(toMaterial) || XBlock.isWater(toMaterial))
                    && XBlock.isWaterStationary(source)
                    && canGenerate(sourceMaterial, to)
                    && event.getFace() != BlockFace.DOWN) {
                if (XBlock.isLava(sourceMaterial) && !isSurroundedByWater(to.getLocation())) {
                    return;
                }
                event.setCancelled(true);
                eventHandler.generateBlock(world, source, to);
            }
            else if (canGenerateBlock(source, to)) {
                event.setCancelled(true);
                eventHandler.generateBlock(world, source, to);
            }
        }
    }
}

