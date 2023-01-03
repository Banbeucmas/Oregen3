package me.banbeucmas.oregen3.hook.blockplacer;

import dev.lone.itemsadder.api.CustomBlock;
import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import org.bukkit.block.Block;

public class ItemsAdderBlockPlacer implements BlockPlacer {
    String blockID;

    public ItemsAdderBlockPlacer(String blockID) {
        this.blockID = blockID;
    }
    @Override
    public void placeBlock(Block block) {
        CustomBlock.place(blockID, block.getLocation());
    }
}
