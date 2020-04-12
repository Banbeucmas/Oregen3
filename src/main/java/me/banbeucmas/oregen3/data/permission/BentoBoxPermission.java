package me.banbeucmas.oregen3.data.permission;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.OfflinePlayer;
import world.bentobox.bentobox.api.user.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BentoBoxPermission implements PermissionManager {
    private static final Map<String, HashSet<String>> permlist = new HashMap<>();

    @Override
    public boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        checkContains(player.getName());

        final HashSet<String> list = permlist.get(player.getName());
        final User user = User.getInstance(player);
        if (user == null) return false;

        if (user.isOnline()) {
            if (!list.contains(permission) && Oregen3.getPerm().playerHas(player.getPlayer(), permission))
                list.add(permission);
            else if (list.contains(permission) && !Oregen3.getPerm().playerHas(player.getPlayer(), permission))
                list.remove(permission);
        }

        return list.contains(permission);
    }

    @Override
    public void checkPerms(final OfflinePlayer player) {
        checkContains(player.getName());
        final HashSet<String> list = permlist.get(player.getName());
        list.clear();
        for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
            if (checkPerm(null, player, chooser.getPermission())) {
                list.add(chooser.getPermission());
            }
        }
    }

    private void checkContains(final String player) {
        if (!permlist.containsKey(player)) {
            permlist.put(player, new HashSet<>());
        }
    }
}
