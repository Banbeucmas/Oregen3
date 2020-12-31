package me.banbeucmas.oregen3.hooks.skyblock;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.managers.IslandManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IridiumSkyblockHook implements SkyblockHook {

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        final Island island = User.getUser(uuid).getIsland();
        return island == null ? 0 : island.value / IridiumSkyblock.getConfiguration().valuePerLevel;
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final Island island = IslandManager.getIslandViaLocation(loc);
        return island == null ? null : UUID.fromString(island.owner);
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        final Island island = User.getUser(uuid).getIsland();
        return island == null ? null : UUID.fromString(island.owner);
    }

    @Override
    public List<UUID> getMembers(final UUID uuid) {
        final Island island = User.getUser(uuid).getIsland();
        if (island == null) return null;
        final List<UUID> list = new ArrayList<>();
        for (final String member : island.members) {
            list.add(UUID.fromString(member));
        }
        return list;
    }
}
