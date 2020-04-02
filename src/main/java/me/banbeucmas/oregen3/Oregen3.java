package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.Commands;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.listeners.BlockListener;
import me.banbeucmas.oregen3.listeners.GUIListener;
import me.banbeucmas.oregen3.utils.StringUtils;
import me.banbeucmas.oregen3.utils.hooks.ASkyblockHook;
import me.banbeucmas.oregen3.utils.hooks.AcidIslandHook;
import me.banbeucmas.oregen3.utils.hooks.BentoBoxHook;
import me.banbeucmas.oregen3.utils.hooks.SkyblockHook;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Oregen3 extends JavaPlugin {
    private static Oregen3 plugin;
    private static SkyblockHook hook;
    public static boolean DEBUG;

    private static void updateConfig() {
        if (!plugin.getConfig().isSet("version")) {
            plugin.getConfig().set("version", "1.1.0");

            final Collection<String> l = new ArrayList<>();
            l.add("FENCE");
            l.add("ACACIA_FENCE");
            l.add("BIRCH_FENCE");
            l.add("DARK_OAK_FENCE");
            l.add("IRON_FENCE");
            plugin.getConfig().set("blocks", l);
            plugin.getConfig().set("mode.lavaBlock", false);
            plugin.getConfig().set("mode.waterBlock", true);
            plugin.getConfig().set("mode.lavaFence", null);
            plugin.getConfig().set("mode.waterFence", null);
            plugin.saveConfig();
        }
        if (Objects.equals(plugin.getConfig().getString("version"), "1.1.0")) {
            plugin.getConfig().set("version", "1.2.0");
            plugin.getConfig().set("enableDependency", true);
            plugin.saveConfig();
            updateConfig();
        }
        if (Objects.equals(plugin.getConfig().getString("version"), "1.2.0")) {
            plugin.getConfig().set("version", "1.3.0");
            plugin.getConfig().set("messages.gui.title", "&eChances");
            plugin.getConfig().set("messages.gui.block.displayName", "&6%name%");
            plugin.getConfig().set("messages.gui.block.lore",
                                   Collections.singletonList("&6Chances: &e%chance%&6%"));
            plugin.saveConfig();
        }
    }

    public void onDisable() {
        plugin = null;
        DataManager.unregisterAll();
    }

    public static SkyblockHook getHook() {
        return hook;
    }

    public static Oregen3 getPlugin() {
        return plugin;
    }

    //TODO Seperate these into methods
    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        updateConfig();

        final boolean asbHook = Bukkit.getServer().getPluginManager().isPluginEnabled("ASkyBlock");
        final boolean acidHook = Bukkit.getServer().getPluginManager().isPluginEnabled("AcidIsland");
        final boolean bentoboxHook = Bukkit.getServer().getPluginManager().isPluginEnabled("BentoBox");
        if (asbHook) {
            hook = new ASkyblockHook();
        }
        else if (acidHook) {
            hook = new AcidIslandHook();
        }
        else if (bentoboxHook) {
            hook = new BentoBoxHook();
        }

        final CommandSender sender = Bukkit.getConsoleSender();
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
        Objects.requireNonNull(getCommand("oregen3")).setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }
}
