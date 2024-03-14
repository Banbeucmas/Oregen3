package me.banbeucmas.oregen3.listener;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatListener implements Listener {
    private Oregen3 plugin;
    public ChatListener(Oregen3 plugin) {
        this.plugin = plugin;
    }

    private Map<UUID, Consumer<String>> chatActions = new HashMap<>();

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (chatActions.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            Consumer<String> action = chatActions.remove(player.getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> action.accept(message));
        }
    }

    public void addChatAction(HumanEntity player, Consumer<String> action) {
        player.closeInventory();
        chatActions.put(player.getUniqueId(), action);
    }
}
