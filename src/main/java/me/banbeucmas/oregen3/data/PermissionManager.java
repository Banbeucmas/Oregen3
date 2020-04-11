package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PermissionManager {
    private static final Map<String, HashSet<String>> permlist = new HashMap<>();

    public static boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        if (!permlist.containsKey(player.getName())) {
            permlist.put(player.getName(), new HashSet<>());
        }

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
}
