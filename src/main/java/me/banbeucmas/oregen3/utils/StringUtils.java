package me.banbeucmas.oregen3.utils;

import me.banbeucmas.oregen3.Oregen3;
import org.bukkit.ChatColor;

public class StringUtils {
    public static String getColoredString(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getPrefix(){
        return StringUtils.getColoredString(Oregen3.getPlugin().getConfig().getString("prefix"));
    }

    public static String getPrefixString(String s){
        return getPrefix() + " " + getColoredString(s);
    }
}
