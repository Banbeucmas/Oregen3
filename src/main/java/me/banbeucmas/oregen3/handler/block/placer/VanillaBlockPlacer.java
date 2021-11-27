package me.banbeucmas.oregen3.handler.block.placer;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class VanillaBlockPlacer implements BlockPlacer {
    Material material;

    public VanillaBlockPlacer(String mat) {
        material = Material.matchMaterial(mat);
    }

    @Override
    public void placeBlock(Block block) {
        block.setType(material);
    }
}
