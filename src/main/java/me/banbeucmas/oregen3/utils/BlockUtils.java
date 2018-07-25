package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class BlockUtils {
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
        FileConfiguration config = Oregen3.getPlugin().getConfig();
       return config.getStringList("blocks").contains(b.getType().toString());
    }
    /*
    public static boolean isFence(Material mat){
        return Arrays.asList(FENCE).contains(mat);
    }
    */
}
