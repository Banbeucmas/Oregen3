package me.banbeucmas.oregen3.hook.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class uSkyBlockHook implements SkyblockHook {
    private final uSkyBlock usb;

    public uSkyBlockHook() {
        usb = (uSkyBlock) Bukkit.getPluginManager().getPlugin("uSkyBlock");
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        final PlayerInfo player = usb.getPlayerInfo(uuid);
        if (player == null) return 0;
        IslandInfo info = usb.getIslandInfo(player);
        if (info == null) return 0;
        return info.getLevel();
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final IslandInfo info = usb.getIslandInfo(loc);
        return info == null ? null : info.getLeaderUniqueId();
    }

    @Override
    public UUID getIslandOwner(final UUID uuid, World world) {
        final PlayerInfo player = usb.getPlayerInfo(uuid);
        if (player == null) return null;
        final IslandInfo info = usb.getIslandInfo(player);
        return info == null ? null : info.getLeaderUniqueId();
    }

    @Override
    public List<UUID> getMembers(final UUID uuid, World world) {
        final List<UUID> list = new ArrayList<>();
        final PlayerInfo player = usb.getPlayerInfo(uuid);
        if (player == null) return list;
        final IslandInfo info = usb.getIslandInfo(player);
        if (info == null) return list;
        list.addAll(info.getMemberUUIDs());
        return list;
    }
}
