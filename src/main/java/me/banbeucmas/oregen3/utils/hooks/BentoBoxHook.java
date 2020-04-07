package me.banbeucmas.oregen3.utils.hooks;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Location;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.request.AddonRequestBuilder;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.Optional;
import java.util.UUID;

public class BentoBoxHook implements SkyblockHook {
    private final IslandsManager manager;
    private boolean level = true;

    public BentoBoxHook() {
        manager = BentoBox.getInstance().getIslands();
        if (!BentoBox.getInstance().getAddonsManager().getAddonByName("Level").isPresent()) {
            Oregen3.getPlugin().getLogger().warning("Level addon for BentoBox not found! Turning island level feature off...");
            level = false;
        }
    }

    @Override
    public long getIslandLevel(final UUID uuid, final Location loc) {
        if (level)
            return (Long) new AddonRequestBuilder()
                    .addon("Level")
                    .label("island-level")
                    .addMetaData("world-name", loc.getWorld().getName())
                    .addMetaData("player", uuid)
                    .request();
        else
            return 0;
    }

    @Override
    public UUID getIslandOwner(final Location loc) {
        final Optional<Island> island = manager.getIslandAt(loc);
        return island.map(Island::getOwner).orElse(null);
    }

    @Override
    public boolean isOnIsland(final Location loc) {
        return manager.getIslandAt(loc).isPresent();
    }
}
