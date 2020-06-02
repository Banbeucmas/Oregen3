package me.banbeucmas.oregen3.listeners;

import com.cryptomorin.xseries.XSound;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Map;

import static me.banbeucmas.oregen3.utils.BlockUtils.*;

public class BlockListener implements Listener {
    private final FileConfiguration config = Oregen3.getPlugin().getConfig();

    @EventHandler
    public void onOre(final BlockFromToEvent e) {
        final Block source = e.getBlock();
        final Block to = e.getToBlock();
        final Material sourceMaterial = source.getType();
        final Material toMaterial = to.getType();
        final World world = source.getWorld();

        if (world == null || sourceMaterial == Material.AIR)
            return;

        if (config.getBoolean("global.generators.world.enabled", false)
                && config.getBoolean("global.generators.world.blacklist", true)
                == config.getStringList("global.generators.world.list").contains(to.getWorld().getName())) {
            return;
        }

        if (isWater(sourceMaterial) || isLava(sourceMaterial)) {
            if ((toMaterial == Material.AIR || isWater(toMaterial))
                    && sourceMaterial != Material.STATIONARY_WATER
                    && canGenerate(sourceMaterial, to)
                    && e.getFace() != BlockFace.DOWN) {
                if (isLava(sourceMaterial) && !isSurroundedByWater(to.getLocation())) {
                    return;
                }
                generateBlock(world, source, to);
            }
            else if (canGenerateBlock(source, to)) {
                generateBlock(world, source, to);
            }
        }
    }

    private void generateBlock(final World world, final Block source, final Block to) {
        final MaterialChooser mc = PluginUtils.getChooser(source.getLocation());
        if (mc.isWorldEnabled() && mc.getWorldList().contains(to.getWorld().getName()) == mc.isWorldBlacklist())
            return;
        to.setType(randomChance(mc));
        if (mc.isSoundEnabled())
            world.playSound(to.getLocation(), mc.getSound(), mc.getSoundVolume(), mc.getSoundPitch());
        else if (config.getBoolean("global.generators.sound.enabled", false)) {
            world.playSound(to.getLocation(),
                    XSound.matchXSound(config.getString("global.generators.sound.name", "BLOCK_FIRE_EXTINGUISH")).map(XSound::parseSound).orElse(XSound.BLOCK_FIRE_EXTINGUISH.parseSound()),
                    (float) config.getDouble("global.generators.sound.volume", 1),
                    (float) config.getDouble("global.generators.sound.pitch", 1)
            );
        }
    }

    private boolean canGenerateBlock(final Block src, final Block to) {
        final Material material = src.getType();
        for (final BlockFace face : FACES) {
            final Block check = to.getRelative(face);
            if (isBlock(check)
                    && (isWater(material))
                    && config.getBoolean("mode.waterBlock")) {
                return true;
            }
            else if (isBlock(check)
                    && (isLava(material))
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
        final boolean check = isWater(material);
        for (final BlockFace face : FACES) {
            final Material type = b.getRelative(face, 1).getType();
            if (((check && isLava(type)) || (!check && isWater(type)))
                    && config.getBoolean("mode.waterLava")) {
                return true;
            }
        }
        return false;
    }

    private Material randomChance(final MaterialChooser mc) {
        final Map<Material, Double> chances = mc.getChances();
        double chance = 100 * PluginUtils.RANDOM.nextDouble();
        if (!config.getBoolean("randomFallback")) {
            for (final Map.Entry<Material, Double> entry : chances.entrySet()) {
                chance -= entry.getValue();
                if (chance <= 0) {
                    return entry.getKey();
                }
            }
        }
        else {
            final int id = PluginUtils.RANDOM.nextInt(chances.size());
            final Material mat = (Material) chances.keySet().toArray()[id];
            if (chance <= mc.getChances().get(mat)) {
                return mat;
            }
        }
        return mc.getFallback();
    }
}
