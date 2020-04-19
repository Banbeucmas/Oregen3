package me.banbeucmas.oregen3.data.permission;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AsyncVaultPermission implements PermissionManager {
    private static final Map<String, HashSet<String>> permlist = new HashMap<>();

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

    @Override
    public void checkPerms(final OfflinePlayer player) {
        checkContains(player.getName());
        new BukkitRunnable() {
            @Override
            public void run() {
                final HashSet<String> list = permlist.get(player.getName());
                list.clear();
                for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
                    if (checkPerm(null, player, chooser.getPermission())) {
                        list.add(chooser.getPermission());
                    }
                }
            }
        }.runTaskAsynchronously(Oregen3.getPlugin());
    }

    private void checkContains(final String player) {
        if (!permlist.containsKey(player)) {
            permlist.put(player, new HashSet<>());
        }
    }
}
