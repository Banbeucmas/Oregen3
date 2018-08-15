package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PluginUtils {
    public static OfflinePlayer getOwner(Location loc) {
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
        OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(uuid);

        return p;
    }

    public static MaterialChooser getChooser(Location loc){
        Oregen3 plugin = Oregen3.getPlugin();
        MaterialChooser mc = DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator"));
        if(plugin.getConfig().getBoolean("enableDependency")){
            Player p = (Player) PluginUtils.getOwner(loc);
            for(MaterialChooser chooser : DataManager.getChoosers().values()){
                if(p == null){
                    break;
                }
                if(p.hasPermission(chooser.getPermission())){
                    mc = chooser;
                }
            }
        }
        return mc;
    }
}
