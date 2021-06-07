package me.banbeucmas.oregen3.handlers.block;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LimitedBlockPlaceHandler implements BlockPlaceHandler {
    private Queue<BlockPlaceTask> tasks = new ConcurrentLinkedQueue<>();
    private BukkitTask task;
    private long maxBlockPlacePerTick;

    public LimitedBlockPlaceHandler(Oregen3 plugin) {
        maxBlockPlacePerTick = plugin.getConfig().getLong("global.generators.maxBlockPlacePerTick", -1);
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            //Bukkit.getLogger().info("Total size: " + tasks.size());
            long blockPlaced = 0;
            while (!tasks.isEmpty() && blockPlaced < maxBlockPlacePerTick) {
                BlockPlaceTask blockPlace = tasks.poll();
                blockPlace.block.setType(blockPlace.material);
                blockPlaced++;
            }
        }, 0, 1);
    }

    public void stop() {
        task.cancel();
        while (!tasks.isEmpty()) {
            BlockPlaceTask blockPlace = tasks.poll();
            blockPlace.block.setType(blockPlace.material);
        }
    }

    @Override
    public void placeBlock(Block block, Material material) {
        tasks.add(new BlockPlaceTask(block, material));
    }

    public static class BlockPlaceTask {
        public Block block;
        public Material material;

        public BlockPlaceTask(Block block, Material material) {
            this.block = block;
            this.material = material;
        }
    }
}
