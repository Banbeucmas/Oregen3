package me.banbeucmas.oregen3.utils.hooks;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.IslandManager;
import com.iridium.iridiumskyblock.User;
import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IridiumSkyblockHook implements SkyblockHook {
    private IslandManager manager;

    public IridiumSkyblockHook() {
        Bukkit.getScheduler().runTaskLater(Oregen3.getPlugin(), () -> manager = IridiumSkyblock.getIslandManager(), 2);
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return User.getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().exp;
    }

    @SuppressWarnings("deprecation")
    @Override
    public UUID getIslandOwner(final Location loc) {
        return Bukkit.getOfflinePlayer(manager.getIslandViaLocation(loc).getOwner()).getUniqueId();
    }

    @SuppressWarnings("deprecation")
    @Override
    public UUID getIslandOwner(final UUID uuid) {
        return Bukkit.getOfflinePlayer(User.getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().getOwner()).getUniqueId();
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return manager.getIslandViaLocation(loc) != null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<UUID> getMembers(final UUID uuid) {
        final Island island = User.getUser(Bukkit.getOfflinePlayer(uuid)).getIsland();
        if (island == null) return null;
        return island.getMembers().stream().map(s -> Bukkit.getOfflinePlayer(s).getUniqueId()).collect(Collectors.toList());
    }
}
