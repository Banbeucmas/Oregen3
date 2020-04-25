package me.banbeucmas.oregen3.utils.hooks;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.IslandManager;
import com.iridium.iridiumskyblock.User;
import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class IridiumSkyblockHook implements SkyblockHook {
    private IslandManager manager;

    public IridiumSkyblockHook() {
        Bukkit.getScheduler().runTask(Oregen3.getPlugin(), () -> manager = IridiumSkyblock.getIslandManager());
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
}
