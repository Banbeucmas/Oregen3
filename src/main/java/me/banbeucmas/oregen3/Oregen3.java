package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.AdminCommands;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.listeners.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
}
