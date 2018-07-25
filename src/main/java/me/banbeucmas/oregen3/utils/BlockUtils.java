package me.banbeucmas.oregen3.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;

public class BlockUtils {
    private static Material[] FENCE = {
            Material.FENCE,
            Material.ACACIA_FENCE,
            Material.BIRCH_FENCE,
            Material.DARK_OAK_FENCE,
            Material.IRON_FENCE
    };

    public static BlockFace[] FACES = new BlockFace[] {
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };


    public static boolean isFence(Block b){
        return Arrays.asList(FENCE).contains(b.getType());
    }
    /*
    public static boolean isFence(Material mat){
        return Arrays.asList(FENCE).contains(mat);
    }
    */
}
