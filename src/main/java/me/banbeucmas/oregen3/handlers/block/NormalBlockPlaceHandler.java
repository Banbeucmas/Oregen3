package me.banbeucmas.oregen3.handlers.block;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class NormalBlockPlaceHandler implements BlockPlaceHandler {
    @Override
    public void placeBlock(Block block, Material material) {
        block.setType(material);
    }

    @Override
    public void stop() { }
}
