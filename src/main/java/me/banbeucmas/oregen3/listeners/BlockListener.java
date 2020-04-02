package me.banbeucmas.oregen3.listeners;

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
import java.util.Objects;

public class BlockListener implements Listener {
    private final Oregen3 plugin = Oregen3.getPlugin();
    private final FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onOre(final BlockFromToEvent e) {
        final World world = e.getBlock().getLocation().getWorld();
        assert world != null;
        if (e.getBlock().getType() == Material.AIR || config.getStringList("disabledWorlds").contains(world.getName())) {
            return;
        }

        final Block source = e.getBlock();
        final Block to = e.getToBlock();
        if ((source.getType() == Material.WATER
                || source.getType() == Material.STATIONARY_LAVA
                || source.getType() == Material.STATIONARY_WATER
                || source.getType() == Material.LAVA)) {

            if ((to.getType() == Material.AIR
                    || to.getType() == Material.WATER
                    || to.getType() == Material.STATIONARY_WATER)
                    && source.getType() != Material.STATIONARY_WATER
                    && generateCobble(source.getType(), to)
                    && e.getFace() != BlockFace.DOWN){
                if (source.getType() == Material.LAVA || source.getType() == Material.STATIONARY_LAVA) {
                    if (!isSurroundedByWater(to.getLocation())) {
                        return;
                    }
                }
                to.setType(randomChance(source.getLocation()));

                if (config.getBoolean("sound.enabled"))
                    world.playSound(to.getLocation(), PluginUtils.getCobbleSound(), config.getInt("sound.volume"), config.getInt("sound.pitch"));
            }
            else if (generateCobbleBlock(source, to)) {
                to.setType(randomChance(source.getLocation()));
                if (config.getBoolean("sound.enabled"))
                    world.playSound(to.getLocation(), PluginUtils.getCobbleSound(), config.getInt("sound.volume"), config.getInt("sound.pitch"));
            }
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

    private Material randomChance(final Location loc) {
        final MaterialChooser mc = PluginUtils.getChooser(loc);
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
        final Block[] blocks = {
                Objects.requireNonNull(fromLoc.getWorld()).getBlockAt(fromLoc.getBlockX() + 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() - 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() + 1),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() - 1)};

        for (final Block b : blocks) {
            if (b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER) {
                return true;
            }
        }
        return false;

    }

}
