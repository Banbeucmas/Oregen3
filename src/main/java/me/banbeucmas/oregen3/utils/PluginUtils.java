package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.MaterialChooser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class PluginUtils {
    public static OfflinePlayer getOwner(final Location loc) {
        if (!Oregen3.getHook().isOnIsland(loc)) {
            return null;
        }

        final UUID uuid = Oregen3.getHook().getIslandOwner(loc);
        if (uuid == null) {
            return null;
        }

        if (Oregen3.DEBUG) {
            System.out.println("UUID: " + uuid);
        }

        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    public static OfflinePlayer getOwner(final UUID uuid) {
        final UUID p = Oregen3.getHook().getIslandOwner(uuid);
        if (p == null) {
            return null;
        }

        if (Oregen3.DEBUG) {
            System.out.println("UUID: " + p);
        }

        return Bukkit.getServer().getOfflinePlayer(p);
    }

    public static MaterialChooser getChooser(final Location loc) {
        final Oregen3 plugin = Oregen3.getPlugin();
        final AtomicReference<MaterialChooser> mc = new AtomicReference<>(DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator")));
        if (plugin.hasDependency()) {
            final OfflinePlayer p = getOwner(loc);
            if (p == null) {
                return mc.get();
            }
            for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
                Oregen3.getPlugin().getServer().getScheduler().runTaskAsynchronously(Oregen3.getPlugin(), () -> {
                    //TODO: Support island-only world?
                    if (plugin.getPerm().playerHas(null, p, chooser.getPermission())
                            && chooser.getPriority() >= mc.get().getPriority()
                            && getLevel(p.getUniqueId(), loc) >= chooser.getLevel()) {
                        if (Oregen3.DEBUG) {
                            System.out.println("Changed gennerator id to " + chooser.getId());
                        }
                        mc.set(chooser);
                    }
                });
            }
        }
        if (Oregen3.DEBUG) {
            System.out.println("Gennerator id: " + mc.get().getId());
        }
        return mc.get();
    }

    public static MaterialChooser getChooser(final UUID uuid) {
        final Oregen3 plugin = Oregen3.getPlugin();
        final AtomicReference<MaterialChooser> mc = new AtomicReference<>(DataManager.getChoosers().get(plugin.getConfig().getString("defaultGenerator")));
        if (plugin.hasDependency()) {
            final UUID p = Oregen3.getHook().getIslandOwner(uuid);
            if (p == null) {
                if (Oregen3.DEBUG) {
                    System.out.println("Null UUID...");
                }
                return mc.get();
            }
            for (final MaterialChooser chooser : DataManager.getChoosers().values()) {
                Oregen3.getPlugin().getServer().getScheduler().runTaskAsynchronously(Oregen3.getPlugin(), () -> {
                    //TODO: Support island-only world?
                    if (plugin.getPerm().playerHas(null, Bukkit.getOfflinePlayer(p), chooser.getPermission())
                            && chooser.getPriority() >= mc.get().getPriority()
                            && getLevel(p, null) >= chooser.getLevel()) {
                        if (Oregen3.DEBUG) {
                            System.out.println("Changed gennerator id to " + chooser.getId());
                        }
                        mc.set(chooser);
                    }
                });
            }
        }
        if (Oregen3.DEBUG) {
            System.out.println("Gennerator id: " + mc.get().getId());
        }
        return mc.get();
    }

    private static long getLevel(final UUID id, final Location loc) {
        return Oregen3.getHook().getIslandLevel(id, loc);
    }
}
