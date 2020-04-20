package me.banbeucmas.oregen3.data.permission;

import org.bukkit.OfflinePlayer;

public class DefaultPermission implements PermissionManager {
    @Override
    public boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        if (!player.isOnline()) return false;
        return player.getPlayer().hasPermission(permission);
    }
}
