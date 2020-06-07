package me.banbeucmas.oregen3.hooks;

import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.iridium.iridiumskyblock.IridiumSkyblock.getIslandManager;

public class IridiumSkyblockHook implements SkyblockHook {

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return User.getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().exp;
    }

    @SuppressWarnings("deprecation")
    @Override
    public UUID getIslandOwner(final Location loc) {
        final Island island = getIslandManager().getIslandViaLocation(loc);
        return island == null ? null : Bukkit.getOfflinePlayer(getIslandManager().getIslandViaLocation(loc).getOwner()).getUniqueId();
    }

    @SuppressWarnings("deprecation")
    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return Bukkit.getOfflinePlayer(User.getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().getOwner()).getUniqueId();
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<UUID> getMembers(final UUID uuid) {
        final Island island = User.getUser(Bukkit.getOfflinePlayer(uuid)).getIsland();
        if (island == null) return null;
        final List<UUID> list = new ArrayList<>();
        for (final String s : island.getMembers()) {
            list.add(Bukkit.getOfflinePlayer(s).getUniqueId());
        }
        return list;
    }
}
