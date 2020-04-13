package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import java.util.UUID;

// "Stupid api only use for online players rip" - xHexed, 4/7/2020
public class uSkyBlockHook implements SkyblockHook {
    private final uSkyBlockAPI usb;

    public uSkyBlockHook() {
        usb = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        return (long) usb.getIslandLevel(Bukkit.getPlayer(uuid));
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
}
