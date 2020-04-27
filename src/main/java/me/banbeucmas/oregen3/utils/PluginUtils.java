package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.Objects;
import java.util.UUID;

public class PluginUtils {
    public static OfflinePlayer getOwner(final Location loc) {
        if (!Oregen3.getHook().isOnIsland(loc)) {
            return null;
        }

        final UUID uuid = Oregen3.getHook().getIslandOwner(loc);
        if (uuid == null) {
            return null;
        }
        debug("UUID: " + uuid);

        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    public static OfflinePlayer getOwner(final UUID uuid) {
        final UUID p = Oregen3.getHook().getIslandOwner(uuid);
        if (p == null) {
            return null;
        }
        debug("UUID: " + p);
        return Bukkit.getServer().getOfflinePlayer(p);
    }

    public static MaterialChooser getChooser(final Location loc) {
        final Oregen3 plugin = Oregen3.getPlugin();
        MaterialChooser mc = DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator", ""));
        if (plugin.getConfig().getBoolean("hooks.skyblock.getLowestGenerator", false)) {
            MaterialChooser lowestChooser = null;
            for (final UUID uuid : Oregen3.getHook().getMembers(Objects.requireNonNull(getOwner(loc)).getUniqueId())) {
                final OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                final MaterialChooser chooser = getMaterialChooser(loc, mc, p);
                if (lowestChooser == null || lowestChooser.getPriority() > chooser.getPriority()) {
                    lowestChooser = chooser;
                }
            }
            return lowestChooser;
        }
        if (plugin.hasDependency()) {
            final OfflinePlayer p = getOwner(loc);
            if (p == null) {
                debug("Null UUID...");
                return mc;
            }
            mc = getMaterialChooser(loc, mc, p);
        }
        debug("Gennerator id: " + mc.getId());
        return mc;
    }

    public static MaterialChooser getChooser(final UUID uuid) {
        final Oregen3 plugin = Oregen3.getPlugin();
        MaterialChooser mc = DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator"));
        if (plugin.hasDependency()) {
            final UUID p = Oregen3.getHook().getIslandOwner(uuid);
            if (p == null) {
                debug("Null UUID...");
                return mc;
            }
            for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
                debug("Checking generator type " + chooser.getId() + " for " + Bukkit.getOfflinePlayer(uuid).getName() + ":");
                debug(" - Has perm: " + Oregen3.getPermissionManager().checkPerm(null, Bukkit.getOfflinePlayer(uuid), chooser.getPermission()));
                debug(" - Priority check: " + chooser.getPriority() + " " + mc.getPriority());
                debug(" - Level check: " + getLevel(p, null) + " " + chooser.getLevel());
                //TODO: Support island-only world?
                if (Oregen3.getPermissionManager().checkPerm(null, Bukkit.getOfflinePlayer(p), chooser.getPermission())
                        && chooser.getPriority() >= mc.getPriority()
                        && getLevel(p, null) >= chooser.getLevel()) {
                    debug("Changed gennerator id to " + chooser.getId());
                    mc = chooser;
                }
            }
        }
        debug("Gennerator id: " + mc.getId());
        return mc;
    }

    private static double getLevel(final UUID id, final Location loc) {
        return Oregen3.getHook().getIslandLevel(id, loc);
    }

    public static void debug(final String message) {
        if (Oregen3.DEBUG) {
            System.out.println(message);
        }
    }

    private static MaterialChooser getMaterialChooser(final Location loc, MaterialChooser mc, final OfflinePlayer p) {
        for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
            debug("Checking generator type " + chooser.getId() + " for " + p.getName() + ":");
            debug(" - Has perms: " + Oregen3.getPermissionManager().checkPerm(null, p, chooser.getPermission()));
            debug(" - Priority check: " + chooser.getPriority() + " " + mc.getPriority());
            debug(" - Level check: " + getLevel(p.getUniqueId(), loc) + " " + chooser.getLevel());
            if (Oregen3.getPermissionManager().checkPerm(null, p, chooser.getPermission())
                    && chooser.getPriority() >= mc.getPriority()
                    && getLevel(p.getUniqueId(), loc) >= chooser.getLevel()) {
                debug("Changed gennerator id to " + chooser.getId());
                mc = chooser;
            }
        }
        return mc;
    }
}
