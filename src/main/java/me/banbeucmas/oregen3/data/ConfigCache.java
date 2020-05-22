package me.banbeucmas.oregen3.data;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigCache {
    public static FileConfiguration cacheConfig() {
        final FileConfiguration config = Oregen3.getPlugin().getConfig();
        config.getKeys(true).forEach((s) -> {
            final Object string = config.get(s);
            if (string instanceof String) {
                config.set(s, ChatColor.translateAlternateColorCodes('&', (String) string));
            }
        });
        return config;
    }
}
