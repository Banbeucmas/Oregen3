package me.banbeucmas.oregen3.handlers.event;

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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            final Generator mc = PluginUtils.getChosenGenerator(source.getLocation());
            if (mc.isWorldEnabled() && mc.getWorldList().contains(to.getWorld().getName()) == mc.isWorldBlacklist())
                return;
            PluginUtils.sendBlockEffect(world, to, config, mc);
            final Material randomMaterial = PluginUtils.randomChance(mc);
            Bukkit.getScheduler().runTask(plugin, () -> to.setType(randomMaterial));
        });
    }

}
