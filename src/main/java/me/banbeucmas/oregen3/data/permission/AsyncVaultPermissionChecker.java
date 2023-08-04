package me.banbeucmas.oregen3.data.permission;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.OfflinePlayer;

public class AsyncVaultPermissionChecker implements PermissionChecker {
    private Oregen3 plugin;

    public AsyncVaultPermissionChecker(Oregen3 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean checkPerm(String world, OfflinePlayer player, String permission) {
        return plugin.getPerm().playerHas(world, player, permission);
    }
}
