package me.banbeucmas.oregen3.handler.block.placetask;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.handler.block.placer.BlockPlacer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class NormalBlockPlaceTask extends BlockPlaceTask {
    private Oregen3 plugin;
    private BlockPlaceTask placeTask;

    public NormalBlockPlaceTask(Oregen3 plugin) {
        super(plugin);
        this.plugin = plugin;
        if (plugin.getBlockEventHandler().isAsync()) {
            placeTask = new SyncBlockPlaceTask();
        }
        else {
            placeTask = new DefaultBlockPlaceTask();
        }
    }

    @Override
    public void placeBlock(Block block, BlockPlacer placer) {
        placeTask.placeBlock(block, placer);
    }

    private class SyncBlockPlaceTask extends BlockPlaceTask {
        public SyncBlockPlaceTask() {
            super(plugin);
        }
        @Override
        public void placeBlock(Block block, BlockPlacer blockPlacer) {
            Bukkit.getScheduler().runTask(plugin, () -> place(block, blockPlacer));
        }
    }

    private class DefaultBlockPlaceTask extends BlockPlaceTask {
        public DefaultBlockPlaceTask() {
            super(plugin);
        }
        @Override
        public void placeBlock(Block block, BlockPlacer blockPlacer) {
            place(block, blockPlacer);
        }
    }
}
