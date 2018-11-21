package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class VanillaHook implements SkyblockHook{
    @Override
    public long getIslandLevel(UUID uuid, World world) {
        return -1;
    }

    @Override
    public UUID getIslandOwner(Location loc) {
        return null;
    }

    @Override
    public boolean isOnIsland(Location loc) {
        return false;
    }
}
