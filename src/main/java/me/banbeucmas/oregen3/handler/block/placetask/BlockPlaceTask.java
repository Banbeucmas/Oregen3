package me.banbeucmas.oregen3.handler.block.placetask;

import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import org.bukkit.block.Block;

public interface BlockPlaceTask {
    void placeBlock(Block block, BlockPlacer blockPlacer);

    void stop();
}
