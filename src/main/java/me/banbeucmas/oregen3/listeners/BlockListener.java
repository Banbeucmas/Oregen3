package me.banbeucmas.oregen3.listeners;

import com.cryptomorin.xseries.XSound;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.utils.BlockUtils;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.security.SecureRandom;
import java.util.Map;

public class BlockListener implements Listener {
    private final FileConfiguration config = Oregen3.getPlugin().getConfig();

    @EventHandler
    public void onOre(final BlockFromToEvent e) {
        final World world = e.getBlock().getLocation().getWorld();

        if (world == null || e.getBlock().getType() == Material.AIR)
            return;

        final Block source = e.getBlock();
        final Block to = e.getToBlock();

        if (config.getBoolean("global.generators.world.enabled", false)
                && config.getBoolean("global.generators.world.blacklist", true)
                == config.getStringList("global.generators.world.list").contains(to.getWorld().getName())) {
            return;
        }

        final Material sourceMaterial = source.getType();
        final Material toMaterial = to.getType();

        if ((sourceMaterial == Material.WATER
                || sourceMaterial == Material.STATIONARY_LAVA
                || sourceMaterial == Material.STATIONARY_WATER
                || sourceMaterial == Material.LAVA)) {

            if ((toMaterial == Material.AIR
                    || toMaterial == Material.WATER
                    || toMaterial == Material.STATIONARY_WATER)
                    && sourceMaterial != Material.STATIONARY_WATER
                    && generateCobble(sourceMaterial, to)
                    && e.getFace() != BlockFace.DOWN) {
                if (sourceMaterial == Material.LAVA || sourceMaterial == Material.STATIONARY_LAVA) {
                    if (!isSurroundedByWater(to.getLocation())) {
                        return;
                    }
                }
                run(world, source, to);
            }

            else if (generateCobbleBlock(source, to)) {
                run(world, source, to);
            }
        }
    }

    private void run(final World world, final Block source, final Block to) {
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

    private boolean generateCobbleBlock(final Block src, final Block to) {
        final Material material = src.getType();
        for (final BlockFace face : BlockUtils.FACES) {
            final Block check = to.getRelative(face);
            if (BlockUtils.isBlock(check)
                    && (material == Material.WATER
                    || material == Material.STATIONARY_WATER)
                    && config.getBoolean("mode.waterBlock")) {
                return true;
            }
            else if (BlockUtils.isBlock(check)
                    && (material == Material.LAVA
                    || material == Material.STATIONARY_LAVA)
                    && config.getBoolean("mode.lavaBlock")) {
                    return true;
            }
        }
        return false;
    }

    /*
    Checks for Water + Lava, block will use another method to prevent confusion
     */
    private boolean generateCobble(final Material material, final Block b) {
        final Material mirMat1 = material == Material.WATER || material == Material.STATIONARY_WATER
                ? Material.LAVA : Material.WATER;
        final Material mirMat2 = material == Material.WATER || material == Material.STATIONARY_WATER
                ? Material.STATIONARY_LAVA : Material.STATIONARY_WATER;

        for (final BlockFace face : BlockUtils.FACES) {
            final Block check = b.getRelative(face, 1);
            if ((check.getType() == mirMat1 || check.getType() == mirMat2)
                    && config.getBoolean("mode.waterLava")) {
                return true;
            }
        }
        return false;
    }

    private Material randomChance(final MaterialChooser mc) {
        final Map<Material, Double> chances = mc.getChances();

        //We like unique chances ;)
        final SecureRandom r = new SecureRandom();

        double chance = 100 * r.nextDouble();

        if (!config.getBoolean("randomFallback")) {
            for (final Map.Entry<Material, Double> entry : chances.entrySet()) {
                chance -= entry.getValue();
                if (chance <= 0) {
                    return entry.getKey();
                }
            }
        }
        else {
            final int id = r.nextInt(chances.size());
            final Material mat = (Material) chances.keySet().toArray()[id];
            if (chance <= mc.getChances().get(mat)) {
                return mat;
            }
        }
        return mc.getFallback();
    }

    private boolean isSurroundedByWater(final Location fromLoc) {
        final World world = fromLoc.getWorld();
        final Block[] blocks = {
                world.getBlockAt(fromLoc.getBlockX() + 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                world.getBlockAt(fromLoc.getBlockX() - 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                world.getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() + 1),
                world.getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() - 1)};

        for (final Block b : blocks) {
            if (b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER) {
                return true;
            }
        }
        return false;

    }

}
