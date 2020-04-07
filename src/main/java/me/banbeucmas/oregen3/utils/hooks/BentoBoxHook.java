package me.banbeucmas.oregen3.utils.hooks;

import org.bukkit.Location;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.request.AddonRequestBuilder;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.Optional;
import java.util.UUID;

public class BentoBoxHook implements SkyblockHook {
    private final AddonRequestBuilder builder;
    private final IslandsManager manager;

    public BentoBoxHook() {
        builder = new AddonRequestBuilder();
        manager = BentoBox.getInstance().getIslands();
    }

    @Override
    public long getIslandLevel(final UUID uuid) {
        return (Long) builder
                .addon("Level")
                .label("island-level")
                //TODO: Find a way to get the island world (seems like bentobox handle world per-addon...)
                .addMetaData("world-name", "i need help")
                .addMetaData("player", uuid)
                .request();
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
