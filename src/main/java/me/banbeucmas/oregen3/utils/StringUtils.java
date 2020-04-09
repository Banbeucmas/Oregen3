package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class StringUtils {
    public static final Pattern FORMAT = Pattern.compile("%format%", Pattern.LITERAL);
    public static final Pattern PERM = Pattern.compile("%perm%", Pattern.LITERAL);
    public static final Pattern PLAYER = Pattern.compile("%player%", Pattern.LITERAL);

    public static String getColoredString(String s, final OfflinePlayer player) {
        if (Oregen3.getPlugin().hasPlaceholderAPI())
            s = PlaceholderAPI.setPlaceholders(player, s);
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getPrefix() {
        return StringUtils.getColoredString(Oregen3.getPlugin().getConfig().getString("prefix"), null);
    }

    public static String getPrefixString(final String s, final OfflinePlayer p) {
        return getPrefix() + " " + getColoredString(s, p);
    }
}
