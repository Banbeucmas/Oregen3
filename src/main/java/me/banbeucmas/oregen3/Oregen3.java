package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.Commands;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.listeners.BlockListener;
import me.banbeucmas.oregen3.listeners.GUIListener;
import me.banbeucmas.oregen3.utils.StringUtils;
import me.banbeucmas.oregen3.utils.hooks.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;

public final class Oregen3 extends JavaPlugin {
    private boolean hasDependency = true;
    private static Oregen3 plugin;
    private static SkyblockHook hook;
    public static boolean DEBUG;

    public boolean hasDependency() {
        return hasDependency;
    }

    private void updateConfig() {
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

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        updateConfig();
        checkDependency();

        final CommandSender sender = Bukkit.getConsoleSender();
        //Send Message
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------"));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas, updated by xHexed"));
        sender.sendMessage(StringUtils.getColoredString("       &f&oVersion: &e" + getDescription().getVersion()));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------"));


        DataManager.loadData();
        Objects.requireNonNull(getCommand("oregen3")).setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    private void checkDependency() {
        if (plugin.getConfig().getBoolean("enableDependency")) {
            hasDependency = false;
            return;
        }

        final PluginManager manager = Bukkit.getServer().getPluginManager();
        if (manager.isPluginEnabled("ASkyBlock")) {
            hook = new ASkyblockHook();
        }
        else if (manager.isPluginEnabled("AcidIsland")) {
            hook = new AcidIslandHook();
        }
        else if (manager.isPluginEnabled("BentoBox")) {
            hook = new BentoBoxHook();
        }
        else if (manager.isPluginEnabled("SuperiorSkyblock")) {
            hook = new SuperiorSkyblockHook();
        }
        else if (manager.isPluginEnabled("FabledSkyblock")) {
            hook = new FabledSkyblockHook();
        }
        else {
            hasDependency = false;
            final Logger logger = Bukkit.getLogger();
            logger.warning("Plugin dependency for Oregen3 not found! enableDependency will be turn off!");
        }
    }
}
