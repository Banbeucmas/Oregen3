package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.*;

public class BlockListener implements Listener {
    private Oregen3 plugin = Oregen3.getPlugin();
    private FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onOre(BlockFromToEvent e) {
        World world = e.getBlock().getLocation().getWorld();
        if (e.getBlock() == null
                || e.getBlock().getType() == Material.AIR
                || config.getStringList("disabledWorlds").contains(world.getName())) {
            return;
        }

        Block source = e.getBlock();
        Block to = e.getToBlock();
        if((source.getType() == Material.WATER
                || source.getType() == Material.STATIONARY_LAVA
                || source.getType() == Material.STATIONARY_WATER
                || source.getType() == Material.LAVA)
                && e.getFace() != BlockFace.DOWN) {

            if((to.getType() == Material.AIR
                    || to.getType() == Material.WATER
                    || to.getType() == Material.STATIONARY_WATER)
                    && source.getType() != Material.STATIONARY_WATER
                    && generateCobble(source.getType(), to)){
                if(source.getType() == Material.LAVA || source.getType() == Material.STATIONARY_LAVA){
                    if(!isSurroundedByWater(to.getLocation())){
                        return;
                    }
                }
                to.setType(randomChance(source.getLocation()));
                world.playSound(to.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 3, 2);
            }
            else if(generateCobbleFence(source, to)){
                to.setType(randomChance(source.getLocation()));
                world.playSound(to.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 3, 2);
            }
        }

    }

    private boolean generateCobbleFence(Block src, Block to){
        Material material = src.getType();
        for(BlockFace face : BlockUtils.FACES){
            Block check = to.getRelative(face);
            if(BlockUtils.isFence(check)
                    && (material == Material.WATER
                    || material == Material.STATIONARY_WATER)
                    && config.getBoolean("mode.waterFence")){
                    return true;
            }
            else if(BlockUtils.isFence(check)
                    && (material == Material.LAVA
                    || material == Material.STATIONARY_LAVA)
                    && config.getBoolean("mode.lavaFence")){
                    return true;
            }
        }
        return false;
    }

    /*
    Checks for Water + Lava, fence will use another method to prevent confusion
     */
    private boolean generateCobble(Material material, Block b){
        Material mirMat1 = material == Material.WATER || material == Material.STATIONARY_WATER
                ? Material.LAVA : Material.WATER;
        Material mirMat2 = material == Material.WATER || material == Material.STATIONARY_WATER
                ? Material.STATIONARY_LAVA : Material.STATIONARY_WATER;

        for(BlockFace face : BlockUtils.FACES){
            Block check = b.getRelative(face, 1);
            if((check.getType() == mirMat1 || check.getType() == mirMat2)
                    && config.getBoolean("mode.waterLava")){
                return true;
            }
        }

        return false;

    }

    private Material randomChance(Location loc){
        MaterialChooser mc = DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator"));
        Player p = (Player) getOwner(loc);
        for(MaterialChooser chooser : DataManager.getChoosers().values()){
            if(p == null){
                break;
            }
            if(p.hasPermission(chooser.getPermission())){
                mc = chooser;
            }
        }

        Random r = new Random();
        int id = r.nextInt(mc.getChances().size());
        double chance = 100 * r.nextDouble();

        Material mat = (Material) mc.getChances().keySet().toArray()[id];
        if(chance <= mc.getChances().get(mat)){
            return mat;
        }
        return mc.getFallback();
    }

    private boolean isSurroundedByWater(Location fromLoc) {
        Block[] blocks = {
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() + 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() - 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() + 1),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() - 1) };

        for (Block b : blocks) {
            if (b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER) {
                return true;
            }
        }
        return false;

    }

    private OfflinePlayer getOwner(Location loc) {
        Set<Location> set = new HashSet<>();
        set.add(loc);

        UUID uuid = null;
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock")) {
            uuid = com.wasteofplastic.askyblock.ASkyBlockAPI.getInstance()
                    .getOwner(com.wasteofplastic.askyblock.ASkyBlockAPI.getInstance().locationIsOnIsland(set, loc));
        }else if(Bukkit.getServer().getPluginManager().isPluginEnabled("AcidIsland")) {
            uuid = com.wasteofplastic.acidisland.ASkyBlockAPI.getInstance()
                    .getOwner(com.wasteofplastic.acidisland.ASkyBlockAPI.getInstance().locationIsOnIsland(set, loc));
        }
        if(uuid == null){
            return null;
        }
        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);

        return p;
    }

}
