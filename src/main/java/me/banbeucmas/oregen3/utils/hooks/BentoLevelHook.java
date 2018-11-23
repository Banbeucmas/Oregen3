package me.banbeucmas.oregen3.utils.hooks;

import me.banbeucmas.oregen3.utils.ReflectionUtils;
import org.bukkit.World;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.level.Level;

import java.util.Optional;
import java.util.UUID;

//TODO Use the API Implementation when it is avaliable
public class BentoLevelHook extends BentoboxHook{
    public BentoLevelHook() {
        super();
    }

    @Override
    public long getIslandLevel(UUID uuid, World world){
        Optional<Addon> addon = getBentoBox().getAddonsManager().getAddonByName("Level");
        if(addon.isPresent()){
            Level levelAddon = (Level) addon.get();
            return levelAddon.getIslandLevel(world, uuid);
        }

        return -1;
    }
}
