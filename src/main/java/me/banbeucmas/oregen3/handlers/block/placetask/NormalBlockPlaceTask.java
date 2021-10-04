package me.banbeucmas.oregen3.handlers.block.placetask;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class NormalBlockPlaceTask implements BlockPlaceTask {
    @Override
    public void placeBlock(Block block, Material material) {
        block.setType(material);
    }

    @Override
    public void stop() { }
}
