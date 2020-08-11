package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.Generator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static me.banbeucmas.oregen3.Oregen3.getHook;
import static me.banbeucmas.oregen3.Oregen3.getPlugin;

public class PluginUtils {
    public static final Random RANDOM = ThreadLocalRandom.current();

    public static OfflinePlayer getOwner(final Location loc) {
        final UUID uuid = getHook().getIslandOwner(loc);
        if (uuid == null) {
            return null;
        }
        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    public static OfflinePlayer getOwner(final UUID uuid) {
        final UUID p = getHook().getIslandOwner(uuid);
        if (p == null) {
            return null;
        }
        return Bukkit.getServer().getOfflinePlayer(p);
    }

    public static Generator getChooser(final Location loc) {
        Generator mc = DataManager.getChoosers().get(getPlugin().getConfig().getString("defaultGenerator", ""));
        if (getPlugin().getConfig().getBoolean("hooks.skyblock.getLowestGenerator", false)) {
            Generator lowestChooser = null;
            for (final UUID uuid : getHook().getMembers(Objects.requireNonNull(getOwner(loc)).getUniqueId())) {
                final OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                final Generator chooser = getMaterialChooser(loc, mc, p);
                if (lowestChooser == null || lowestChooser.getPriority() > chooser.getPriority()) {
                    lowestChooser = chooser;
                }
            }
            return lowestChooser;
        }
        if (getPlugin().hasDependency()) {
            final OfflinePlayer p = getOwner(loc);
            if (p == null) {
                return mc;
            }
            mc = getMaterialChooser(loc, mc, p);
        }
        return mc;
    }

    public static Generator getChooser(final UUID uuid) {
        Generator mc = DataManager.getChoosers().get(getPlugin().getConfig().getString("defaultGenerator"));
        if (getPlugin().hasDependency()) {
            final UUID p = getHook().getIslandOwner(uuid);
            if (p == null) {
                return mc;
            }
            for (final Generator chooser : DataManager.getChoosers().values()) {
                //TODO: Support island-only world?
                if (Oregen3.getPermissionManager().checkPerm(null, Bukkit.getOfflinePlayer(p), chooser.getPermission())
                        && chooser.getPriority() >= mc.getPriority()
                        && getHook().getIslandLevel(p, null) >= chooser.getLevel()) {
                    mc = chooser;
                }
            }
        }
        return mc;
    }

    private static Generator getMaterialChooser(final Location loc, Generator mc, final OfflinePlayer p) {
        final double level = getHook().getIslandLevel(p.getUniqueId(), loc);
        for (final Generator chooser : DataManager.getChoosers().values()) {
            if (Oregen3.getPermissionManager().checkPerm(null, p, chooser.getPermission())
                    && chooser.getPriority() >= mc.getPriority()
                    && level >= chooser.getLevel()) {
                mc = chooser;
            }
        }
        return mc;
    }
}
