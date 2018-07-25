package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.AdminCommands;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.listeners.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Oregen3 extends JavaPlugin implements Listener {
    private static Oregen3 plugin;

    @Override
    public void onEnable() {
        plugin = this;
        boolean asbHook = Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock");
        boolean acidHook = Bukkit.getServer().getPluginManager().isPluginEnabled("AcidIsland");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------" + ChatColor.WHITE + "[" + ChatColor.YELLOW + "Oregen3" + ChatColor.WHITE + "]" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-------------");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("       " +ChatColor.WHITE+ "" + ChatColor.ITALIC + "Plugin made by " + ChatColor.YELLOW + "" + ChatColor.ITALIC + "Banbeucmas");
        Bukkit.getConsoleSender().sendMessage("       " +ChatColor.WHITE+ "" + ChatColor.ITALIC + "Better than Premium");
        Bukkit.getConsoleSender().sendMessage("       " +ChatColor.WHITE+ "" + ChatColor.ITALIC + "Liên hệ " + ChatColor.YELLOW + "" + ChatColor.ITALIC + "http://bit.ly/BanbeShop");
        Bukkit.getConsoleSender().sendMessage("       " + ChatColor.WHITE + "" + ChatColor.ITALIC + "ASkyblock: " + ChatColor.YELLOW + asbHook);
        Bukkit.getConsoleSender().sendMessage("       " + ChatColor.WHITE + "" + ChatColor.ITALIC + "AcidIsland: " + ChatColor.YELLOW + acidHook);

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "------------------------------------");

        saveDefaultConfig();
        updateConfig();
        DataManager.loadData();
        getCommand("oregen3").setExecutor(new AdminCommands());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
    }

    public void onDisable() {
        plugin = null;
        DataManager.unregisterAll();
    }

    public static Oregen3 getPlugin() {
        return plugin;
    }

    public static void updateConfig(){
        if(!getPlugin().getConfig().isSet("version")){
            getPlugin().getConfig().set("version", "1.1.0");

            List<String> l = new ArrayList<>();
            l.add("FENCE");
            l.add("ACACIA_FENCE");
            l.add("BIRCH_FENCE");
            l.add("DARK_OAK_FENCE");
            l.add("IRON_FENCE");
            getPlugin().getConfig().set("blocks", l);
            getPlugin().getConfig().set("mode.lavaBlock", false);
            getPlugin().getConfig().set("mode.waterBlock", true);
            getPlugin().getConfig().set("mode.lavaFence", null);
            getPlugin().getConfig().set("mode.waterFence", null);
        }
        getPlugin().saveConfig();

    }
}
