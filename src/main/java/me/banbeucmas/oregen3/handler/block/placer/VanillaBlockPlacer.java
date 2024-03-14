package me.banbeucmas.oregen3.handler.block.placer;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class VanillaBlockPlacer implements BlockPlacer {
    Material material;

    public VanillaBlockPlacer(String mat) {
        XMaterial.matchXMaterial(mat).ifPresent((m) -> material = m.parseMaterial());
    }

    @Override
    public void placeBlock(Block block) {
        block.setType(material);
    }
}
