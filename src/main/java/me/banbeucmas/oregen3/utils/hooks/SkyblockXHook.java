package me.banbeucmas.oregen3.utils.hooks;

import net.savagelabs.skyblockx.core.IPlayer;
import net.savagelabs.skyblockx.core.IPlayerKt;
import net.savagelabs.skyblockx.core.Island;
import net.savagelabs.skyblockx.core.IslandKt;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SkyblockXHook implements SkyblockHook {
    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        final IPlayer player = IPlayerKt.getIPlayerByUUID(uuid.toString());
        if (player == null) return 0;
        final Island island = player.getIsland();
        return (island == null) ? 0 : island.getLevel();
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final Island island = IslandKt.getIslandFromLocation(loc);
        if (island == null) return null;
        return UUID.fromString(island.getOwnerUUID());
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        final IPlayer player = IPlayerKt.getIPlayerByUUID(uuid.toString());
        if (player == null) return null;
        final Island island = player.getIsland();
        if (island == null) return null;
        return UUID.fromString(island.getOwnerUUID());
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return IslandKt.getIslandFromLocation(loc) != null;
    }

    @Override
    public List<UUID> getMembers(final UUID uuid) {
        final IPlayer player = IPlayerKt.getIPlayerByUUID(uuid.toString());
        if (player == null) return null;
        final Island island = player.getIsland();
        if (island == null) return null;
        return island.getAllMemberUUIDs().stream().map(UUID::fromString).collect(Collectors.toList());
    }
}
