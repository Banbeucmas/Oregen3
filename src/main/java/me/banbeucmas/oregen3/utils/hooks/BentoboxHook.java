package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.level.Level;

import java.util.Optional;
import java.util.UUID;

//TODO Use the Hook
public class BentoboxHook implements SkyblockHook{
    private BentoBox api;

    public BentoboxHook(){
        this.api = (BentoBox) Bukkit.getPluginManager().getPlugin("BentoBox");
    }

    @Override
    public long getIslandLevel(UUID uuid, World world) {
        Optional<Addon> addon = api.getAddonsManager().getAddonByName("Level");
        if(addon.isPresent()){
            Level levelAddon = (Level) addon.get();
            return levelAddon.getIslandLevel(world, uuid);
        }
        return -1;
    }

    @Override
    public UUID getIslandOwner(Location loc) {
        return api.getIslands().getIslandAt(loc).get().getOwner();
    }

    @Override
    public boolean isOnIsland(Location loc) {
        return api.getIslands().getIslandAt(loc).orElse(null) != null;
    }
}
