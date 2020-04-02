package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Location;

import java.util.UUID;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface SkyblockHook {

    long getIslandLevel(UUID uuid);

    UUID getIslandOwner(Location loc);

    boolean isOnIsland(Location loc);
}
