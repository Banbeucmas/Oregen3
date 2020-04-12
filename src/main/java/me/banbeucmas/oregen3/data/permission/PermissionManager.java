package me.banbeucmas.oregen3.data.permission;

import org.bukkit.OfflinePlayer;

public interface PermissionManager {
    boolean checkPerm(final String world, final OfflinePlayer player, final String permission);

    void checkPerms(final OfflinePlayer player);
}
