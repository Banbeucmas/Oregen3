package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.ChatColor;

public class StringUtils {
    public static String getColoredString(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private static String getPrefix() {
        return StringUtils.getColoredString(Oregen3.getPlugin().getConfig().getString("prefix"));
    }

    public static String getPrefixString(final String s) {
        return getPrefix() + " " + getColoredString(s);
    }
}
