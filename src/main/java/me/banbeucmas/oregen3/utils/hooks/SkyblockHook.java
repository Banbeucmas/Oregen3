package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import java.util.UUID;

public interface SkyblockHook {

    long getIslandLevel(UUID uuid, World world);

    UUID getIslandOwner(Location loc);

    boolean isOnIsland(Location loc);
}
