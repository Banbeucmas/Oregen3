package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.CommandHandler;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.permission.DefaultPermission;
import me.banbeucmas.oregen3.data.permission.AsyncVaultPermission;
import me.banbeucmas.oregen3.data.permission.PermissionManager;
import me.banbeucmas.oregen3.data.permission.VaultPermission;
import me.banbeucmas.oregen3.gui.EditGUI;
import me.banbeucmas.oregen3.listeners.BlockListener;
import me.banbeucmas.oregen3.listeners.GUIListener;
import me.banbeucmas.oregen3.utils.StringUtils;
import me.banbeucmas.oregen3.utils.hooks.*;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public final class Oregen3 extends JavaPlugin {
    private boolean hasDependency = true;
    public boolean papi;
    public boolean mvdw;
    private static Oregen3 plugin;
    private static SkyblockHook hook;
    private static Permission perm;
    private static PermissionManager permissionManager;
    public static boolean DEBUG;

    public boolean hasDependency() {
        return hasDependency;
    }

    public void updateConfig() {
        reloadConfig();
        final FileConfiguration config = getConfig();
        switch (config.getString("version", "none")) {
            case "none":
                config.addDefault("blocks", Arrays.asList("FENCE", "ACACIA_FENCE", "BIRCH_FENCE", "DARK_OAK_FENCE", "IRON_FENCE"));
                config.addDefault("mode.lavaBlock", false);
                config.addDefault("mode.waterBlock", true);
                config.set("mode.lavaFence", null);
                config.set("mode.waterFence", null);
            case "1.1.0":
                config.addDefault("enableDependency", true);
            case "1.2.0":
                config.addDefault("messages.gui.title", "&eChances");
                config.addDefault("messages.gui.block.displayName", "&6%name%");
                config.addDefault("messages.gui.block.lore", Collections.singletonList("&6Chances: &e%chance%&6%"));
            case "1.3.0":
                config.addDefault("debug", true);
                config.addDefault("messages.noIsland", "&cYou have to be on an island to view this.");
                config.addDefault("sound.enabled", true);
                config.addDefault("sound.created", "BLOCK_FIRE_EXTINGUISH");
                config.addDefault("sound.volume", 3);
                config.addDefault("sound.pitch", 2);
            case "1.3.0.1":
                config.addDefault("messages.noIsland", "&cYou have to be on an island to view this.");
                config.addDefault("messages.missingArgs", "&cFormat: &f + getFormat()");
                config.addDefault("messages.noPermission", "&4Missing Permission: &c + permission");
                config.addDefault("messages.noPlayer", "&4Player is not exist or isn't online");
                config.addDefault("messages.notPlayer", "&4Only player can use this command");
            case "1.3.0.2":
                config.addDefault("hooks.BentoBox.gamemodePriorities", Arrays.asList("AcidIsland", "BSkyBlock", "CaveBlock", "SkyGrid"));
                config.addDefault("messages.commands.help", "&6&o/%label% help &f» Open help pages");
                config.addDefault("messages.commands.reload", "&6&o/%label% reload &f» Reload config");
                config.addDefault("messages.commands.info", "&6&o/%label% info [player] &f» Getting ore spawning chance of the island you are standing on or the targetted player");
                config.addDefault("messages.commands.debug", "&6&o/%label% debug &f» Toggle debugging");
            case "1.3.0.3":
                config.addDefault("global.generators.world.enabled", false);
                config.addDefault("global.generators.world.blacklist", false);
                config.addDefault("global.generators.world.list", config.getStringList("disabledWorlds"));
                config.addDefault("global.generators.sound.enabled", config.getBoolean("sound.enabled", false));
                config.addDefault("global.generators.sound.name", config.getString("sound.created", "BLOCK_FIRE_EXTINGUISH"));
                config.addDefault("global.generators.sound.volume", config.getDouble("sound.volume", 3));
                config.addDefault("global.generators.sound.pitch", config.getDouble("sound.pitch", 2));
                config.addDefault("updater.copyHeader", false);
            case "1.3.0.4":
                config.addDefault("hooks.Vault.forceAsync", false);
                config.addDefault("hooks.Vault.pluginAsyncList", Collections.singletonList("LuckPerms"));
                config.addDefault("messages.commands.edit", "&6&o/%label% edit &f» Edit generators in-game (comming soon...)");
            case "1.3.0.5":
                config.addDefault("hooks.skyblock.getLowestGenerator", false);
                config.set("version", "1.3.0.1");
                saveConfig();
        }
        if (config.getBoolean("updater.copyHeader", false)) {
            config.options().copyHeader(false);
            saveConfig();
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

    public static Permission getPerm() {
        return perm;
    }

    public static PermissionManager getPermissionManager() {
        return permissionManager;
    }

    @Override
    public void onEnable() {
        plugin = this;

        new MetricsLite(this, 3052);

        saveDefaultConfig();
        updateConfig();
        checkDependency();
        setupPermissions();

        if (getConfig().getBoolean("debug", false)) {
            DEBUG = true;
        }

        final CommandSender sender = Bukkit.getConsoleSender();
        //Send Message
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------", null));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("   &fPlugin made by &eBanbeucmas&f, updated by &exHexed", null));
        sender.sendMessage(StringUtils.getColoredString("   &fVersion: &e" + getDescription().getVersion(), null));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------", null));

        DataManager.loadData();

        final PluginCommand command = Objects.requireNonNull(getCommand("oregen3"));
        command.setExecutor(new CommandHandler());
        command.setTabCompleter(new CommandHandler());

        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);

        EditGUI.create();
    }

    private void checkDependency() {
        if (!plugin.getConfig().getBoolean("enableDependency")) {
            hasDependency = false;
            return;
        }

        final PluginManager manager = getServer().getPluginManager();
        if (manager.isPluginEnabled("ASkyBlock")) {
            hook     = new ASkyblockHook();
        }
        else if (manager.isPluginEnabled("AcidIsland")) {
            hook     = new AcidIslandHook();
        }
        else if (manager.isPluginEnabled("BentoBox")) {
            hook     = new BentoBoxHook();
        }
        else if (manager.isPluginEnabled("SuperiorSkyblock2")) {
            hook     = new SuperiorSkyblockHook();
        }
        else if (manager.isPluginEnabled("FabledSkyBlock")) {
            hook     = new FabledSkyBlockHook();
        }
        else if (manager.isPluginEnabled("uSkyBlock")) {
            hook     = new uSkyBlockHook();
        }
        else if (manager.isPluginEnabled("IridiumSkyblock")) {
            hook     = new IridiumSkyblockHook();
        }
        else if (manager.isPluginEnabled("SkyblockX")) {
            hook     = new SkyblockXHook();
        }
        else {
            getLogger().warning(StringUtils.getPrefixString("Plugin dependency for Oregen3 not found! Turning enableDependency off...", null));
            hasDependency = false;
        }

        if (manager.isPluginEnabled("PlaceholderAPI")) {
            papi = true;
        }

        if (manager.isPluginEnabled("MVdWPlaceholderAPI")) {
            mvdw = true;
        }
    }

    private void setupPermissions() {
        final PluginManager manager = getServer().getPluginManager();
        if (manager.isPluginEnabled("Vault")) {
            final RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            perm              = rsp.getProvider();
            permissionManager = new VaultPermission();

            if (getConfig().getBoolean("hooks.Vault.forceAsync")) {
                permissionManager = new AsyncVaultPermission();
            }
            else for (final String plugin : getConfig().getStringList("hooks.Vault.pluginAsyncList")) {
                if (manager.isPluginEnabled(plugin)) {
                    permissionManager = new AsyncVaultPermission();
                    break;
                }
            }
        }
        else {
            getLogger().warning(StringUtils.getPrefixString("Vault not found! Offline player's permission will not be checked! Using bukkit's provided one...", null));
            permissionManager = new DefaultPermission();
        }
    }
}
