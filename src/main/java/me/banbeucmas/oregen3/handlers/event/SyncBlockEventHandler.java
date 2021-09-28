package me.banbeucmas.oregen3.handlers.event;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class SyncBlockEventHandler implements BlockEventHandler {
    @Override
    public void generateBlock(final World world, final Block source, final Block to, final FileConfiguration config) {
        generate(world, source, to, config);
    }
}
