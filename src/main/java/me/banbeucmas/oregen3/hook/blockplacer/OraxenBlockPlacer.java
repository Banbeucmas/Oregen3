package me.banbeucmas.oregen3.hook.blockplacer;

import io.th0rgal.oraxen.mechanics.provided.gameplay.noteblock.NoteBlockMechanicFactory;
import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class OraxenBlockPlacer implements BlockPlacer {
    String itemID;

    public OraxenBlockPlacer(String block) {
        itemID = block;
    }

    @Override
    public void placeBlock(Block block) {
        block.setType(Material.NOTE_BLOCK);
        NoteBlockMechanicFactory.setBlockModel(block, itemID);
    }
}
