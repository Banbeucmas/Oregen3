package me.banbeucmas.oregen3.utils.hooks;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Location;

import java.util.UUID;

public class SuperiorSkyblockHook implements SkyblockHook {

    @Override
    public long getIslandLevel(final UUID uuid) {
        final SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(uuid);
        if (player == null || player.getIsland() == null)
            return 0;
        return player.getIsland().getIslandLevel().intValue();
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return SuperiorSkyblockAPI.getIslandAt(loc).getOwner().getUniqueId();
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return SuperiorSkyblockAPI.getIslandAt(loc) != null;
    }
}
