package me.banbeucmas.oregen3.listeners;

import me.banbeucmas.oregen3.data.PermissionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        PermissionManager.checkPerms(event.getPlayer());
    }
}
