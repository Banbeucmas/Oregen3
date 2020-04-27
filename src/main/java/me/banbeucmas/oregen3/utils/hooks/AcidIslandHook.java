package me.banbeucmas.oregen3.utils.hooks;

import com.wasteofplastic.acidisland.ASkyBlockAPI;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class AcidIslandHook implements SkyblockHook {
    private final ASkyBlockAPI api;

    public AcidIslandHook() {
        api = ASkyBlockAPI.getInstance();
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return api.getIslandLevel(uuid);
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
