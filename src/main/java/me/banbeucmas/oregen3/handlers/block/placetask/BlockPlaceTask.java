package me.banbeucmas.oregen3.handlers.block.placetask;

import me.banbeucmas.oregen3.handlers.block.placer.BlockPlacer;
import org.bukkit.Material;
import org.bukkit.block.Block;

public interface BlockPlaceTask {
    void placeBlock(Block block, BlockPlacer blockPlacer);

    void stop();
}
