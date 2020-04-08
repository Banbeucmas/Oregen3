package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@SuppressWarnings("WeakerAccess")
public class StringUtils {
    public static String getColoredString(String s, final Player player) {
        if (Oregen3.getPlugin().hasPlaceholderAPI())
            s = PlaceholderAPI.setPlaceholders(player, s);
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getPrefix() {
        return StringUtils.getColoredString(Oregen3.getPlugin().getConfig().getString("prefix"), null);
    }

    public static String getPrefixString(final String s, final Player p) {
        return getPrefix() + " " + getColoredString(s, p);
    }
}
