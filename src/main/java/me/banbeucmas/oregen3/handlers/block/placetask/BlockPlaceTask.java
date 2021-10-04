package me.banbeucmas.oregen3.handlers.block.placetask;

import org.bukkit.Material;
import org.bukkit.block.Block;

public interface BlockPlaceTask {
    void placeBlock(Block block, Material material);

    void stop();
}
