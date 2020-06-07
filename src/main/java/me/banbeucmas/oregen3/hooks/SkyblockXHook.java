package me.banbeucmas.oregen3.hooks;

import net.savagelabs.skyblockx.core.IPlayer;
import net.savagelabs.skyblockx.core.IPlayerKt;
import net.savagelabs.skyblockx.core.Island;
import net.savagelabs.skyblockx.core.IslandKt;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SkyblockXHook implements SkyblockHook {
    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        final IPlayer player = IPlayerKt.getIPlayerByUUID(uuid.toString());
        if (player == null) return 0;
        final Island island = player.getIsland();
        return island == null ? 0 : island.getLevel();
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final Island island = IslandKt.getIslandFromLocation(loc);
        return island == null ? null : UUID.fromString(island.getOwnerUUID());
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
    public List<UUID> getMembers(final UUID uuid) {
        final IPlayer player = IPlayerKt.getIPlayerByUUID(uuid.toString());
        if (player == null) return null;
        final Island island = player.getIsland();
        if (island == null) return null;
        final List<UUID> list = new ArrayList<>();
        for (final String s : island.getAllMemberUUIDs()) {
            list.add(UUID.fromString(s));
        }
        return list;
    }
}
