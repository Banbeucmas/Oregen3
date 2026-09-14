package me.banbeucmas.oregen3.handler.block.placer;

import io.th0rgal.oraxen.api.OraxenBlocks;
import org.bukkit.block.Block;

public class OraxenBlockPlacer implements BlockPlacer {
    String itemID;

    public OraxenBlockPlacer(String block) {
        itemID = block;
    }

    @Override
    public void placeBlock(Block block) {
        OraxenBlocks.place(itemID, block.getLocation());
    }
}
