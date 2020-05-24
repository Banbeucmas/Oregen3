package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

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
}
