package me.banbeucmas.oregen3.utils.hooks;

import com.songoda.skyblock.SkyBlock;
import com.songoda.skyblock.island.Island;
import com.songoda.skyblock.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class FabledSkyBlockHook implements SkyblockHook {
    private final IslandManager manager;

    public FabledSkyBlockHook() {
        manager = SkyBlock.getInstance().getIslandManager();
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (manager.getIsland(player) != null) {
            final Island is = manager.getIsland(player);
            if (is.getLevel() != null)
                return is.getLevel().getLevel();
        }
        return 0;
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return manager.getIslandAtLocation(loc).getOwnerUUID();
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return manager.getIslandByUUID(uuid).getOwnerUUID();
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return manager.getIslandAtLocation(loc) != null;
    }
}
