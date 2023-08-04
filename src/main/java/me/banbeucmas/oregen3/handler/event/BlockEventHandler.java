package me.banbeucmas.oregen3.handler.event;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class BlockEventHandler {
    Oregen3 plugin;

    public BlockEventHandler(Oregen3 plugin) {
        this.plugin = plugin;
    }

    public abstract void generateBlock(final World world, final Block source, final Block to);

    void generate(final World world, final Block source, final Block to) {
        final Generator mc = plugin.getUtils().getChosenGenerator(source.getLocation());
        if (mc == null) return;
        plugin.getBlockPlaceTask().placeBlock(to, mc.randomChance());
        plugin.getUtils().sendBlockEffect(world, to, mc);
    }

    public abstract boolean isAsync();
}
