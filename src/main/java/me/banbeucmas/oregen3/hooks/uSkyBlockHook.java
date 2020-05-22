package me.banbeucmas.oregen3.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// "Stupid api only use for online players rip" - xHexed, 4/7/2020
public class uSkyBlockHook implements SkyblockHook {
    private final uSkyBlockAPI usb;

    public uSkyBlockHook() {
        usb = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (!player.isOnline()) return 0;
        return (long) usb.getIslandLevel(player.getPlayer());
    }

    @SuppressWarnings("deprecation")
    @Override
    public UUID getIslandOwner(final Location loc) {
        // We don't have other choice but to use the deprecated method because the api only return the leader's name :(
        return Bukkit.getOfflinePlayer(usb.getIslandInfo(loc).getLeader()).getUniqueId();
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        // Couldn't find any method related to this...
        return null;
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return usb.getIslandInfo(loc) != null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<UUID> getMembers(final UUID uuid) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (!player.isOnline()) return null;
        return usb.getIslandInfo(player.getPlayer()).getMembers().stream().map((s) -> Bukkit.getOfflinePlayer(s).getUniqueId()).collect(Collectors.toList());
    }
}
