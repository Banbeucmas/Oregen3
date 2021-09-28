package me.banbeucmas.oregen3.handlers.event;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class AsyncBlockEventHandler implements BlockEventHandler {
    private final Plugin plugin;

    public AsyncBlockEventHandler(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void generateBlock(final World world, final Block source, final Block to, final FileConfiguration config) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> generate(world, source, to, config));
    }

}
