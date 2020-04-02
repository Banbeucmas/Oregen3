package me.banbeucmas.oregen3.utils.hooks;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;

import java.util.UUID;

public class ASkyblockHook implements SkyblockHook {
    private final ASkyBlockAPI api;

    public ASkyblockHook() {
        api = ASkyBlockAPI.getInstance();
    }

    @Override
    public long getIslandLevel(final UUID uuid) {
        return api.getLongIslandLevel(uuid);
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return api.getIslandAt(loc).getOwner();
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return api.getIslandAt(loc) != null;
    }
}
