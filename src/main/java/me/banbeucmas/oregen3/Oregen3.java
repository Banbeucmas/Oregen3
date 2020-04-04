package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.Commands;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.listeners.BlockListener;
import me.banbeucmas.oregen3.listeners.GUIListener;
import me.banbeucmas.oregen3.utils.StringUtils;
import me.banbeucmas.oregen3.utils.hooks.*;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Oregen3 extends JavaPlugin {
    private boolean hasDependency = true;
    private static Oregen3 plugin;
    private static SkyblockHook hook;
    private String hookName = "none";
    private Permission perm;
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
        if (Objects.equals(plugin.getConfig().getString("version"), "1.3.0")) {
            plugin.getConfig().set("version", "1.3.0.1");
            plugin.getConfig().set("debug", true);
            plugin.getConfig().set("messages.noIsland", "&cYou have to be on an island to view this.");
            plugin.getConfig().set("sound.enabled", true);
            plugin.getConfig().set("sound.created", "BLOCK_FIRE_EXTINGUISH");
            plugin.getConfig().set("sound.volume", 3);
            plugin.getConfig().set("sound.pitch", 2);
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

    public Permission getPerm() {
        return perm;
    }

    @Override
    public void onEnable() {
        plugin = this;

        new Metrics(this);

        saveDefaultConfig();
        updateConfig();
        checkDependency();
        setupPermissions();

        if (getConfig().getBoolean("debug")) {
            DEBUG = true;
        }

        final CommandSender sender = Bukkit.getConsoleSender();
        //Send Message
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------"));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas, updated by xHexed"));
        sender.sendMessage(StringUtils.getColoredString("       &f&oVersion: &e" + getDescription().getVersion()));
        sender.sendMessage(StringUtils.getColoredString("       &f&oHooked plugin: &e" + hookName));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------"));

        DataManager.loadData();
        Objects.requireNonNull(getCommand("oregen3")).setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    private void checkDependency() {
        if (!plugin.getConfig().getBoolean("enableDependency")) {
            hasDependency = false;
            return;
        }

        final PluginManager manager = Bukkit.getServer().getPluginManager();
        if (manager.isPluginEnabled("ASkyBlock")) {
            hook     = new ASkyblockHook();
            hookName = "ASkyBlock";
        }
        else if (manager.isPluginEnabled("AcidIsland")) {
            hook     = new AcidIslandHook();
            hookName = "AcidIsland";
        }
        else if (manager.isPluginEnabled("BentoBox")) {
            hook     = new BentoBoxHook();
            hookName = "AcidIsland";
        }
        else if (manager.isPluginEnabled("SuperiorSkyblock")) {
            hook     = new SuperiorSkyblockHook();
            hookName = "SuperiorSkyblock";
        }
        else if (manager.isPluginEnabled("FabledSkyblock")) {
            hook     = new FabledSkyblockHook();
            hookName = "FabledSkyblock";
        }
        else if (manager.isPluginEnabled("uSkyBlock")) {
            hook     = new uSkyBlockHook();
            hookName = "uSkyBlock";
        }
        else {
            Bukkit.getLogger().warning("[Oregen3] Plugin dependency for Oregen3 not found! enableDependency will be turned off!");
            hasDependency = false;
        }
    }

    private void setupPermissions() {
        final RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            getLogger().severe("Vault not found! Disabling plugin...");
            return;
        }
        perm = rsp.getProvider();
    }
}
