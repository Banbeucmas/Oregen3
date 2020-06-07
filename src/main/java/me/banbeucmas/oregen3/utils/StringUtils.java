package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern PERM = Pattern.compile("%perm%", Pattern.LITERAL);
    public static final Pattern PLAYER = Pattern.compile("%player%", Pattern.LITERAL);
    public static final Pattern LABEL = Pattern.compile("%label%", Pattern.LITERAL);
    public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#.##");

    public static String getColoredString(final String s, final OfflinePlayer player) {
        return ChatColor.translateAlternateColorCodes('&', parsePlaceholder(s, player));
    }

    public static String getString(final String s, final OfflinePlayer player) {
        return parsePlaceholder(s, player);
    }

    public static String getPrefixString(final String s, final OfflinePlayer p) {
        return getColoredString(Oregen3.getPlugin().getConfig().getString("messages.prefix", ""), null) + " " + getColoredString(s, p);
    }

    private static String parsePlaceholder(String s, final OfflinePlayer player) {
        if (Oregen3.getPlugin().papi)
            s = me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, s);
        if (Oregen3.getPlugin().mvdw)
            s = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, s);
        return s;
    }
}
