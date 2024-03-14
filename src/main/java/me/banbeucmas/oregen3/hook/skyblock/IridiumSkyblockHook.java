package me.banbeucmas.oregen3.hook.skyblock;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import com.iridium.iridiumteams.Rank;
import com.iridium.iridiumteams.database.IridiumUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IridiumSkyblockHook implements SkyblockHook {
    @Override
    public double getIslandLevel(UUID uuid, Location loc) {
        return IridiumSkyblockAPI.getInstance().getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().map(Island::getValue).orElse(0.0);
    }

    @Override
    public UUID getIslandOwner(Location loc) {
        Island island = IridiumSkyblockAPI.getInstance().getIslandViaLocation(loc).orElse(null);
        if (island == null) return null;
        return IridiumSkyblock.getInstance().getTeamManager().getTeamMembers(island).stream()
                .filter(user -> user.getUserRank() == Rank.OWNER.getId())
                .findAny()
                .map(User::getUuid)
                .orElse(null);
    }

    @Override
    public UUID getIslandOwner(UUID uuid, World world) {
        Island island = IridiumSkyblockAPI.getInstance().getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().orElse(null);
        if (island == null) return null;
        return IridiumSkyblock.getInstance().getTeamManager().getTeamMembers(island).stream()
                .filter(user -> user.getUserRank() == Rank.OWNER.getId())
                .findAny()
                .map(User::getUuid)
                .orElse(null);
    }

    @Override
    public List<UUID> getMembers(UUID uuid, World world) {
        Island island = IridiumSkyblockAPI.getInstance().getUser(Bukkit.getOfflinePlayer(uuid)).getIsland().orElse(null);
        if (island == null) return Collections.emptyList();
        return IridiumSkyblock.getInstance().getTeamManager().getTeamMembers(island).stream().map(IridiumUser::getUuid).collect(Collectors.toList());
    }
}