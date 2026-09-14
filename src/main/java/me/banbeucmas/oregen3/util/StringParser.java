package me.banbeucmas.oregen3.util;

import me.banbeucmas.oregen3.Oregen3;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class StringParser {
    public static final Pattern PLACEHOLDER_PERM_PATTERN = Pattern.compile("%perm%", Pattern.LITERAL);
    public static final Pattern PLACEHOLDER_PLAYER_PATTERN = Pattern.compile("%player%", Pattern.LITERAL);
    public static final Pattern PLACEHOLDER_LABEL_PATTERN = Pattern.compile("%label%", Pattern.LITERAL);
    public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#.##");

    private Oregen3 plugin;

    public StringParser(Oregen3 plugin) {
        this.plugin = plugin;
    }

    public String getColoredString(final String s, final OfflinePlayer player) {
        return ChatColor.translateAlternateColorCodes('&', parsePlaceholder(s, player));
    }

    public String getString(final String s, final OfflinePlayer player) {
        return parsePlaceholder(s, player);
    }

    public String getColoredPrefixString(final String s, final OfflinePlayer p) {
        return getColoredString(plugin.getConfig().getString("messages.prefix", ""), null) + " " + getColoredString(s, p);
    }

    public String getPrefixString(final String s, final OfflinePlayer p) {
        return getColoredString(plugin.getConfig().getString("messages.prefix", ""), null) + " " + getString(s, p);
    }

    private String parsePlaceholder(String s, final OfflinePlayer player) {
        if (plugin.isPapiEnabled())
            s = PlaceholderAPI.setPlaceholders(player, s);
        return s;
    }

}
