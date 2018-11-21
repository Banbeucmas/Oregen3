package me.banbeucmas.oregen3.utils.hooks;

import com.wasteofplastic.acidisland.ASkyBlockAPI;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class AcidIslandHook implements SkyblockHook{
    private ASkyBlockAPI api;

    public AcidIslandHook(){
        api = ASkyBlockAPI.getInstance();
    }

    @Override
    public long getIslandLevel(UUID uuid, World world) {
        return api.getIslandLevel(uuid);
    }

    @Override
    public UUID getIslandOwner(Location loc) {
        return api.getIslandAt(loc).getOwner();
    }

    @Override
    public boolean isOnIsland(Location loc) {
        return api.getIslandAt(loc) != null;
    }
}
