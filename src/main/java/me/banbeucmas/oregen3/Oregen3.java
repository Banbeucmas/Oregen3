package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.Commands;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.listeners.BlockListener;
import me.banbeucmas.oregen3.listeners.GUIListener;
import me.banbeucmas.oregen3.utils.StringUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Oregen3 extends JavaPlugin implements Listener {
    private static Oregen3 plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Metrics metrics = new Metrics(this);

        saveDefaultConfig();
        updateConfig();

        boolean asbHook = Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock");
        boolean acidHook = Bukkit.getServer().getPluginManager().isPluginEnabled("AcidIsland");

        CommandSender sender = Bukkit.getConsoleSender();
        //Send Message
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------"));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas"));
        sender.sendMessage(StringUtils.getColoredString("       &f&oVersion: &e" + getDescription().getVersion()));
        sender.sendMessage(StringUtils.getColoredString("       &f&oASkyblock: &e" + asbHook));
        sender.sendMessage(StringUtils.getColoredString("       &f&oAcidIsland: &e" + acidHook));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------"));

        if(getConfig().getBoolean("enableDependency")){
            getConfig().set("enableDependency", asbHook || acidHook);
            saveConfig();
        }

        DataManager.loadData();
        getCommand("oregen3").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
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
            getPlugin().saveConfig();
        }
        if(getPlugin().getConfig().getString("version").equals("1.1.0")){
            getPlugin().getConfig().set("version", "1.2.0");
            getPlugin().getConfig().set("enableDependency", true);
            getPlugin().saveConfig();
            updateConfig();
        }
        if(getPlugin().getConfig().getString("version").equals("1.2.0")){
            getPlugin().getConfig().set("version", "1.3.0");
            getPlugin().getConfig().set("messages.gui.title", "&eChances");
            getPlugin().getConfig().set("messages.gui.block.displayName", "&6%name%");
            getPlugin().getConfig().set("messages.gui.block.lore",
                    Arrays.asList("&6Chances: &e%chance%&6%"));
            getPlugin().saveConfig();
        }
    }
}
