package me.banbeucmas.oregen3.utils;

import com.cryptomorin.xseries.XBlock;
import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Stream;

public class BlockUtils {
    public static final BlockFace[] FACES = {
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public static boolean isBlock(final Block b) {
        return Oregen3.getPlugin().getConfig().getStringList("blocks").contains(b.getType().toString());
    }

    public static boolean isItem(final ItemStack i) {
        return i != null && i.getType() != Material.AIR;
    }

    public static boolean isWater(final Material material) {
        return XBlock.isWater(material);
    }

    public static boolean isLava(final Material material) {
        return XBlock.isLava(material);
    }

    public static boolean isSurroundedByWater(final Location loc) {
        final World world = loc.getWorld();
        final int x = loc.getBlockX();
        final int y = loc.getBlockY();
        final int z = loc.getBlockZ();
        return Stream.of(world.getBlockAt(x + 1, y, z),
                         world.getBlockAt(x - 1, y, z),
                         world.getBlockAt(x, y, z + 1),
                         world.getBlockAt(x, y, z - 1))
                .anyMatch(b -> isWater(b.getType()));
    }
}
