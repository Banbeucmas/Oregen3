package me.banbeucmas.oregen3.handlers.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

public interface BlockPlacer {
    void placeBlock(Block block, Material material);

    void stop();
}
