package me.banbeucmas.oregen3.data.permission;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.OfflinePlayer;

public class LuckPermsPermission implements PermissionManager {
    private final UserManager manager;

    public LuckPermsPermission() {
        manager = LuckPermsProvider.get().getUserManager();
    }

    @Override
    public boolean checkPerm(final String world, final OfflinePlayer player, final String permission) {
        final User user = manager.getUser(player.getUniqueId());
        if (user == null) {
            return false;
        }

        return user.getCachedData()
                .getPermissionData(QueryOptions.builder(QueryMode.NON_CONTEXTUAL).build())
                .checkPermission(permission).asBoolean();
    }

    @Override
    public void checkPerms(final OfflinePlayer player) {
        final User user = manager.getUser(player.getUniqueId());
        if (user == null) {
            return;
        }
        user.getCachedData().invalidatePermissionCalculators();
    }
}
