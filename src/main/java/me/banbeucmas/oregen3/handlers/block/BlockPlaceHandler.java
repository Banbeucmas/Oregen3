package me.banbeucmas.oregen3.handlers.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

public interface BlockPlaceHandler {
    void placeBlock(Block block, Material material);

    void stop();
}
