package me.banbeucmas.oregen3.handlers.event;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

public interface BlockEventHandler {
    void generateBlock(final World world, final Block source, final Block to, final FileConfiguration config);

    default void generate(final World world, final Block source, final Block to, final FileConfiguration config) {
        final Generator mc = PluginUtils.getChosenGenerator(source.getLocation());
        if (mc.isWorldEnabled() && mc.getWorldList().contains(to.getWorld().getName()) == mc.isWorldBlacklist())
            return;
        Oregen3.getBlockPlaceHandler().placeBlock(to, PluginUtils.randomChance(mc));
        PluginUtils.sendBlockEffect(world, to, config, mc);
    }

    boolean isAsync();
}
