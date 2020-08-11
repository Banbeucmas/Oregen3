package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public class SyncBlockEventHandler implements BlockEventHandler {
    @Override
    public void generateBlock(final World world, final Block source, final Block to, final FileConfiguration config) {
        final Generator mc = PluginUtils.getChooser(source.getLocation());
        if (mc.isWorldEnabled() && mc.getWorldList().contains(to.getWorld().getName()) == mc.isWorldBlacklist())
            return;
        to.setType(BlockListener.randomChance(mc, config));
        BlockListener.sendBlockEffect(world, to, config, mc);
    }
}
