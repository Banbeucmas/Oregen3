package me.banbeucmas.oregen3.data.permission;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AsyncOnSyncVaultPermissionChecker implements PermissionChecker, Listener {
    private final Oregen3 plugin;
    private final Map<String, HashSet<String>> permissionList = new HashMap<>();

    /**
     * This class sucks, not thread-safe, might not update properly during first few block regenerations
     * and causes extra lag while breaking blocks but hey it works :")
     */
    public AsyncOnSyncVaultPermissionChecker(final Oregen3 plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        checkContains(player.getName());
        final HashSet<String> list = permissionList.get(player.getName());

        if (player.isOnline()) {
            if (!list.contains(permission) && plugin.getPerm().playerHas(player.getPlayer(), permission))
                list.add(permission);
            else if (list.contains(permission) && !plugin.getPerm().playerHas(player.getPlayer(), permission))
                list.remove(permission);
        }
        else {
            plugin.getUtils().runAsyncTask(() -> {
                if (!list.contains(permission) && plugin.getPerm().playerHas(world, player, permission))
                    list.add(permission);
                else if (list.contains(permission) && !plugin.getPerm().playerHas(world, player, permission))
                    list.remove(permission);
            });
        }

        return list.contains(permission);
    }

    private void checkPerms(final OfflinePlayer player) {
        checkContains(player.getName());
        plugin.getUtils().runAsyncTask(() -> {
            for (final Generator chooser : plugin.getDataManager().getGenerators().values()) {
                checkPerm(null, player, chooser.getPermission());
            }
        });
    }

    private void checkContains(final String player) {
        if (!permissionList.containsKey(player)) {
            permissionList.put(player, new HashSet<>());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlock(final BlockDamageEvent e) {
        final OfflinePlayer player = plugin.getUtils().getOwner(e.getPlayer().getUniqueId(), e.getPlayer().getWorld());
        if (player == null) return;
        checkPerms(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent e) {
        final OfflinePlayer player = plugin.getUtils().getOwner(e.getPlayer().getUniqueId(), e.getPlayer().getWorld());
        if (player == null) return;
        checkPerms(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final OfflinePlayer player = plugin.getUtils().getOwner(e.getPlayer().getUniqueId(), e.getPlayer().getWorld());
        if (player == null) return;
        checkPerms(player);
    }
}
