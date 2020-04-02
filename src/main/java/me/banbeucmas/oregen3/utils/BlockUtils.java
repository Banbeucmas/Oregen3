package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

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
        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        return config.getStringList("blocks").contains(b.getType().toString());
    }

    /*
    public static boolean isFence(Material mat){
        return Arrays.asList(FENCE).contains(mat);
    }
    */
}
