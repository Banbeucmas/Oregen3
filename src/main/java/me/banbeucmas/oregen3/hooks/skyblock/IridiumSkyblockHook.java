package me.banbeucmas.oregen3.hooks.skyblock;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IridiumSkyblockHook implements SkyblockHook {

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return IridiumSkyblockAPI.getInstance().getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().map(Island::getValue).orElse(0.0);
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        return IridiumSkyblockAPI.getInstance().getIslandViaLocation(loc).map(value -> value.getOwner().getUuid()).orElse(null);
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return IridiumSkyblockAPI.getInstance().getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().map(value -> value.getOwner().getUuid()).orElse(null);
    }

    @Override
    public List<UUID> getMembers(final UUID uuid) {
        final Optional<Island> island = IridiumSkyblockAPI.getInstance().getUser(Bukkit.getOfflinePlayer(uuid)).getIsland();
        if (!island.isPresent()) return null;
        final List<UUID> list = new ArrayList<>();
        for (final User member : island.get().getMembers()) {
            list.add(member.getUuid());
        }
        return list;
    }
}
