package me.banbeucmas.oregen3.handler.block.placetask;

import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import org.bukkit.block.Block;

public class NormalBlockPlaceTask implements BlockPlaceTask {
    @Override
    public void placeBlock(Block block, BlockPlacer placer) {
        placer.placeBlock(block);
    }

    @Override
    public void stop() { }
}
