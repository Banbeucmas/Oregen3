package me.banbeucmas.oregen3.handler.event;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class SyncBlockEventHandler extends BlockEventHandler {
    @Override
    public void generateBlock(final World world, final Block source, final Block to, final FileConfiguration config) {
        generate(world, source, to, config);
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
