package me.banbeucmas.oregen3.data.permission;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import me.banbeucmas.oregen3.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AsyncVaultPermission implements PermissionManager, Listener {
    private final Map<String, HashSet<String>> permlist = new HashMap<>();

    public AsyncVaultPermission() {
        Bukkit.getPluginManager().registerEvents(this, Oregen3.getPlugin());
    }

    @Override
    public boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        checkContains(player.getName());
        final HashSet<String> list = permlist.get(player.getName());

        if (player.isOnline()) {
            if (!list.contains(permission) && Oregen3.getPerm().playerHas(player.getPlayer(), permission))
                list.add(permission);
            else if (list.contains(permission) && !Oregen3.getPerm().playerHas(player.getPlayer(), permission))
                list.remove(permission);
        }
        else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!list.contains(permission) && Oregen3.getPerm().playerHas(world, player, permission))
                        list.add(permission);
                    else if (list.contains(permission) && !Oregen3.getPerm().playerHas(world, player, permission))
                        list.remove(permission);
                }
            }.runTaskAsynchronously(Oregen3.getPlugin());
        }

        return list.contains(permission);
    }

    private void checkPerms(final OfflinePlayer player) {
        checkContains(player.getName());
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
                    checkPerm(null, player, chooser.getPermission());
                }
            }
        }.runTaskAsynchronously(Oregen3.getPlugin());
    }

    private void checkContains(final String player) {
        if (!permlist.containsKey(player)) {
            permlist.put(player, new HashSet<>());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlock(final BlockDamageEvent e) {
        final OfflinePlayer player = PluginUtils.getOwner(e.getPlayer().getUniqueId());
        if (player == null) return;
        checkPerms(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent e) {
        final OfflinePlayer player = PluginUtils.getOwner(e.getPlayer().getUniqueId());
        if (player == null) return;
        checkPerms(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        final OfflinePlayer player = PluginUtils.getOwner(e.getPlayer().getUniqueId());
        if (player == null) return;
        checkPerms(player);
    }
}
