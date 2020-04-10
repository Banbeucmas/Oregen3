package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Location;

import java.util.UUID;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface SkyblockHook {

    /**
     * Gets the island level for this player's uuid. A player location is provided if the plugin requires a location
     *
     * @param uuid the user's uuid
     * @param loc  the user's location (can be different from the uuid's location) and can be null
     *
     * @return island level by player uuid, extra check with the location, or null (vaule of 0)
     */
    long getIslandLevel(UUID uuid, Location loc);

    /**
     * Gets the island owner's uuid on this location.
     *
     * @param loc the current location
     *
     * @return the owner's uuid on this island, or null if found none
     */
    UUID getIslandOwner(Location loc);

    /**
     * Gets the island owner's uuid with the given uuid.
     *
     * @param uuid the player's uuid
     *
     * @return the owner's uuid on this island, or null if found none
     */
    UUID getIslandOwner(UUID uuid);

    /**
     * Check whether the island is found on this location.
     *
     * @param loc the current location
     *
     * @return true if an island is found, false if none
     */
    boolean isOnIsland(Location loc);
}
