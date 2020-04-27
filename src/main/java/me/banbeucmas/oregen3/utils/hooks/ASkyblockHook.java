package me.banbeucmas.oregen3.utils.hooks;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class ASkyblockHook implements SkyblockHook {
    private final ASkyBlockAPI api;

    public ASkyblockHook() {
        api = ASkyBlockAPI.getInstance();
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return api.getLongIslandLevel(uuid);
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return api.getIslandAt(loc).getOwner();
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return api.getTeamLeader(uuid);
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return api.getIslandAt(loc) != null;
    }

    @Override
    public List<UUID> getMembers(final UUID uuid) {
        return api.getIslandOwnedBy(api.getTeamLeader(uuid)).getMembers();
    }
}
