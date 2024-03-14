package me.banbeucmas.oregen3.handler.block.placetask;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import org.bukkit.block.Block;

public abstract class BlockPlaceTask {
    public boolean preventOverrideBlocks;

    public BlockPlaceTask(Oregen3 plugin) {
        preventOverrideBlocks = plugin.getConfig().getBoolean("preventOverrideBlocks", false);
    }

    /* The actual placing */
    boolean place(Block block, BlockPlacer placer) {
        if (preventOverrideBlocks && !block.isEmpty()) return false;
        placer.placeBlock(block);
        return true;
    }

    public abstract void placeBlock(Block block, BlockPlacer blockPlacer);

    public void stop() {}
}
