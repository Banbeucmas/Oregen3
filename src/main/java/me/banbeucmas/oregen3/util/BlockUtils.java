package me.banbeucmas.oregen3.util;

import com.cryptomorin.xseries.XBlock;
import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

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

    private Oregen3 plugin;

    public BlockUtils(Oregen3 plugin) {
        this.plugin = plugin;
    }

    public boolean isBlock(final Block b) {
        return XBlock.isOneOf(b, plugin.getConfig().getStringList("blocks"));
    }

    public static boolean isItem(final ItemStack i) {
        return i != null && i.getType() != Material.AIR;
    }

    public static boolean isSurroundedByWater(final Location loc) {
        final World world = loc.getWorld();
        final int x = loc.getBlockX();
        final int y = loc.getBlockY();
        final int z = loc.getBlockZ();
        for (Block b : Arrays.asList(world.getBlockAt(x + 1, y, z),
                world.getBlockAt(x - 1, y, z),
                world.getBlockAt(x, y, z + 1),
                world.getBlockAt(x, y, z - 1))) {
            if (XBlock.isWater(b.getType())) {
                return true;
            }
        }
        return false;
    }
}
