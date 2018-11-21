package me.banbeucmas.oregen3.utils.hooks;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.UUID;

public class ASkyblockHook implements SkyblockHook{
    private ASkyBlockAPI api;

    public ASkyblockHook() {
        this.api = ASkyBlockAPI.getInstance();
    }

    @Override
    public long getIslandLevel(UUID uuid, World world) {
        return api.getLongIslandLevel(uuid);
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
