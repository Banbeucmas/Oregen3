package me.banbeucmas.oregen3.hooks.skyblock;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.addons.GameModeAddon;
import world.bentobox.bentobox.api.addons.request.AddonRequestBuilder;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.AddonsManager;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.*;

public class BentoBoxHook implements SkyblockHook {
    private final Collection<World> worlds = new ArrayList<>();
    private GameModeAddon addon;
    private IslandsManager manager;
    private boolean level = true;

    public BentoBoxHook() {
        Bukkit.getScheduler().runTaskLater(Oregen3.getPlugin(), () -> {
            manager = BentoBox.getInstance().getIslands();

            if (!BentoBox.getInstance().getAddonsManager().getAddonByName("Level").isPresent()) {
                Oregen3.getPlugin().getLogger().warning(StringUtils.getPrefixString("Level addon for BentoBox not found! Turning island level feature off...", null));
                level = false;
            }

            final AddonsManager manager = BentoBox.getInstance().getAddonsManager();
            final List<GameModeAddon> addonList = manager.getGameModeAddons();
            if (addonList.isEmpty()) {
                Oregen3.getPlugin().getLogger().severe(StringUtils.getPrefixString("Couldn't find a gamemode for BentoBox! Disabling plugin...", null));
                Oregen3.getPlugin().getPluginLoader().disablePlugin(Oregen3.getPlugin());
                return;
            }

            for (final String name : Oregen3.getPlugin().getConfig().getStringList("hooks.BentoBox.gamemodePriorities")) {
                final Optional<Addon> gamemode = manager.getAddonByName(name);
                if (!gamemode.isPresent() || !(gamemode.get() instanceof GameModeAddon)) continue;
                final GameModeAddon gamemodeAddon = (GameModeAddon) gamemode.get();
                if (addonList.contains(gamemodeAddon)) {
                    addon = gamemodeAddon;
                    worlds.add(addon.getOverWorld());
                }
            }

            if (addon == null) {
                Oregen3.getPlugin().getServer().getConsoleSender().sendMessage(StringUtils.getPrefixString("Couldn't find any gamemode hook list in config, trying to grab the first one it can find...", null));
                addon = addonList.get(0);
            }
        }, 2);
    }

    @Override
    public double getIslandLevel(final UUID uuid, final Location loc) {
        double num = 0;
        if (level) {
            final AddonRequestBuilder builder = new AddonRequestBuilder()
                    .addon("Level")
                    .label("island-level")
                    .addMetaData("player", uuid);
            if (loc != null) {
                num = ((Long) builder
                        .addMetaData("world-name", loc.getWorld())
                        .request()).doubleValue();
            }
            if (num == 0) {
                for (final World world : worlds) {
                    num = ((Long) builder
                            .addMetaData("world-name", world.getName())
                            .request()).doubleValue();
                    if (num != 0) break;
                }
            }
            return num;
        }
        else
            return 0;
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final Optional<Island> island = manager.getIslandAt(loc);
        return island.map(Island::getOwner).orElse(null);
    }

    @Override
    public UUID getIslandOwner(final UUID uuid) {
        for (final World world : worlds) {
            final UUID owner = manager.getOwner(world, uuid);
            if (owner == null) continue;
            return owner;
        }
        return null;
    }

    @Override
    public List<UUID> getMembers(final UUID uuid) {
        for (final World world : worlds) {
            final Island island = manager.getIsland(world, uuid);
            if (island == null) continue;
            return new ArrayList<>(island.getMembers().keySet());
        }
        return null;
    }
}
