package me.banbeucmas.oregen3.util;

import com.cryptomorin.xseries.XSound;
import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.data.Generator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Objects;
import java.util.UUID;

public class PluginUtils {
    private Oregen3 plugin;
    
    public PluginUtils(Oregen3 plugin) {
        this.plugin = plugin;
    }

    public OfflinePlayer getOwner(final Location loc) {
        final UUID uuid = plugin.getHook().getIslandOwner(loc);
        if (uuid == null) {
            return null;
        }
        return Bukkit.getServer().getOfflinePlayer(uuid);
    }

    public OfflinePlayer getOwner(final UUID uuid, World world) {
        final UUID p = plugin.getHook().getIslandOwner(uuid, world);
        if (p == null) {
            return null;
        }
        return Bukkit.getServer().getOfflinePlayer(p);
    }

    public Generator getChosenGenerator(final Location loc) {
        Generator mc = plugin.getDataManager().getGenerators().get(plugin.getConfig().getString("defaultGenerator", ""));
        OfflinePlayer owner = getOwner(loc);
        if (owner == null) return mc;
        switch (plugin.getConfig().getString("hooks.skyblock.getGeneratorMode", "owner")) {
            case "owner": {
                return getMaterialChooser(loc, mc, owner);
            }
            case "lowest": {
                boolean ignore = plugin.getConfig().getBoolean("hooks.skyblock.ignoreOfflinePlayers", false);
                Generator lowestGen = null;
                for (final UUID uuid : plugin.getHook().getMembers(owner.getUniqueId(), loc.getWorld())) {
                    final OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                    if (ignore && !p.isOnline()) continue;
                    final Generator chosen = getMaterialChooser(loc, mc, p);
                    if (lowestGen == null || lowestGen.getPriority() > chosen.getPriority()) {
                        lowestGen = chosen;
                    }
                }
                if (lowestGen == null) {
                    break;
                }
                return lowestGen;
            }
            case "highest": {
                boolean ignore = plugin.getConfig().getBoolean("hooks.skyblock.ignoreOfflinePlayers", false);
                Generator highestGen = null;
                for (final UUID uuid : plugin.getHook().getMembers(owner.getUniqueId(), loc.getWorld())) {
                    final OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
                    if (ignore && !p.isOnline()) continue;
                    final Generator chosen = getMaterialChooser(loc, mc, p);
                    if (highestGen == null || highestGen.getPriority() < chosen.getPriority()) {
                        highestGen = chosen;
                    }
                }
                if (highestGen == null) {
                    break;
                }
                return highestGen;
            }
        }
        return mc;
    }

    public Generator getChosenGenerator(final UUID uuid, World world) {
        Generator mc = plugin.getDataManager().getGenerators().get(plugin.getConfig().getString("defaultGenerator"));
        if (plugin.hasDependency()) {
            final UUID p = plugin.getHook().getIslandOwner(uuid, world);
            if (p == null) {
                return mc;
            }
            for (final Generator chooser : plugin.getDataManager().getGenerators().values()) {
                if (plugin.getPermissionChecker().checkPerm(world.getName(), Bukkit.getOfflinePlayer(p), chooser.getPermission())
                        && chooser.getPriority() >= mc.getPriority()
                        && plugin.getHook().getIslandLevel(p, null) >= chooser.getLevel()) {
                    mc = chooser;
                }
            }
        }
        return mc;
    }

    private Generator getMaterialChooser(final Location loc, Generator mc, final OfflinePlayer p) {
        final double level = plugin.getHook().getIslandLevel(p.getUniqueId(), loc);
        for (final Generator chooser : plugin.getDataManager().getGenerators().values()) {
            if (chooser.isWorldEnabled() && chooser.getWorldList().contains(loc.getWorld().getName()) == chooser.isWorldBlacklist()) continue;
            if (plugin.getPermissionChecker().checkPerm(loc.getWorld().getName(), p, chooser.getPermission())
                    && chooser.getPriority() >= mc.getPriority()
                    && level >= chooser.getLevel()) {
                mc = chooser;
            }
        }
        return mc;
    }

    public void sendBlockEffect(final World world, final Block to, final Generator mc) {
        if (mc.isSoundEnabled())
            world.playSound(to.getLocation(), mc.getSound(), mc.getSoundVolume(), mc.getSoundPitch());
        else if (plugin.getConfig().getBoolean("global.generators.sound.enabled", false)) {
            world.playSound(to.getLocation(),
                    Objects.requireNonNull(XSound.matchXSound(plugin.getConfig().getString("global.generators.sound.name", "BLOCK_FIRE_EXTINGUISH")).map(XSound::parseSound).orElse(XSound.BLOCK_FIRE_EXTINGUISH.parseSound())),
                    (float) plugin.getConfig().getDouble("global.generators.sound.volume", 1),
                    (float) plugin.getConfig().getDouble("global.generators.sound.pitch", 1)
            );
        }
    }
}
