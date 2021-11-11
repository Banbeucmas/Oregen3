package me.banbeucmas.oregen3.handlers.block.placetask;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.handlers.block.placer.BlockPlacer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LimitedBlockPlaceTask implements BlockPlaceTask {
    private Queue<BlockPlaceTask> tasks;
    private BukkitTask task;
    private long maxBlockPlacePerTick;

    public LimitedBlockPlaceTask(Oregen3 plugin) {
        if (plugin.getEventHandler().isAsync()) {
            tasks = new ConcurrentLinkedQueue<>();
        } else {
            tasks = new ArrayDeque<>();
        }
        maxBlockPlacePerTick = plugin.getConfig().getLong("global.generators.maxBlockPlacePerTick", Integer.MAX_VALUE);
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            //Bukkit.getLogger().info("Total size: " + tasks.size());
            long blockPlaced = 0;
            while (!tasks.isEmpty() && blockPlaced < maxBlockPlacePerTick) {
                BlockPlaceTask blockPlace = tasks.poll();
                blockPlace.placer.placeBlock(blockPlace.block);
                blockPlaced++;
            }
        }, 0, 1);
    }

    public void stop() {
        task.cancel();
        while (!tasks.isEmpty()) {
            BlockPlaceTask blockPlace = tasks.poll();
            blockPlace.placer.placeBlock(blockPlace.block);
        }
    }

    @Override
    public void placeBlock(Block block, BlockPlacer placer) {
        tasks.add(new BlockPlaceTask(block, placer));
    }

    public static class BlockPlaceTask {
        public Block block;
        public BlockPlacer placer;

        public BlockPlaceTask(Block block, BlockPlacer placer) {
            this.block = block;
            this.placer = placer;
        }
    }
}
