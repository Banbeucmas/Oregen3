package me.banbeucmas.oregen3.data.permission;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.OfflinePlayer;

public class SyncVaultPermissionChecker implements PermissionChecker {
    private Oregen3 plugin;

    public SyncVaultPermissionChecker(Oregen3 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        if (player.isOnline()) {
            return plugin.getPerm().playerHas(player.getPlayer(), permission);
        }
        else {
            return plugin.getPerm().playerHas(world, player, permission);
        }
    }
}
