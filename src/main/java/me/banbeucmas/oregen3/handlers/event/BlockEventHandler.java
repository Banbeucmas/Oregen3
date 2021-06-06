package me.banbeucmas.oregen3.handlers.event;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public interface BlockEventHandler {
    void generateBlock(final World world, final Block source, final Block to, final FileConfiguration config);
}
