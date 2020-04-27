package me.banbeucmas.oregen3.utils.hooks;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SuperiorSkyblockHook implements SkyblockHook {

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return SuperiorSkyblockAPI.getPlayer(uuid).getIsland().getIslandLevel().intValue();
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return SuperiorSkyblockAPI.getIslandAt(loc).getOwner().getUniqueId();
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return SuperiorSkyblockAPI.getPlayer(uuid).getIslandLeader().getUniqueId();
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return SuperiorSkyblockAPI.getIslandAt(loc) != null;
    }

    @Override
    public List<UUID> getMembers(final UUID uuid) {
        return SuperiorSkyblockAPI.getPlayer(uuid).getIsland().getIslandMembers(true).stream().map(SuperiorPlayer::getUniqueId).collect(Collectors.toList());
    }
}
