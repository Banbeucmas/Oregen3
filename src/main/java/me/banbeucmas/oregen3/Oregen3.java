package me.banbeucmas.oregen3;

import com.tchristofferson.configupdater.ConfigUpdater;
import me.banbeucmas.oregen3.commands.CommandHandler;
import me.banbeucmas.oregen3.data.DataManager;
import me.banbeucmas.oregen3.data.permission.AsyncVaultPermission;
import me.banbeucmas.oregen3.data.permission.DefaultPermission;
import me.banbeucmas.oregen3.data.permission.PermissionManager;
import me.banbeucmas.oregen3.data.permission.VaultPermission;
import me.banbeucmas.oregen3.gui.EditGUI;
import me.banbeucmas.oregen3.gui.editor.Generator;
import me.banbeucmas.oregen3.gui.editor.GeneratorList;
import me.banbeucmas.oregen3.gui.editor.options.Fallback;
import me.banbeucmas.oregen3.hooks.*;
import me.banbeucmas.oregen3.listeners.BlockListener;
import me.banbeucmas.oregen3.listeners.GUIListener;
import me.banbeucmas.oregen3.utils.StringUtils;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public final class Oregen3 extends JavaPlugin {
    private static FileConfiguration config;
    private static Oregen3 plugin;
    private static SkyblockHook hook;
    private static Permission perm;
    private static PermissionManager permissionManager;
    public static boolean DEBUG;
    public boolean papi;
    public boolean mvdw;
    private boolean hasDependency = true;

    public boolean hasDependency() {
        return hasDependency;
    }

    public void updateConfig() {
        saveConfig();
        final File configFile = new File(getDataFolder(), "config.yml");
        final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        DEBUG = config.getBoolean("debug", false);
        if (config.getBoolean("auto-update", true)) {
            try {
                ConfigUpdater.update(this, "config.yml", configFile, new ArrayList<>());
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }
        reloadConfig();
        Oregen3.config = config;
    }

    public void updateMessages() {
        config.getConfigurationSection("messages").getKeys(true).forEach((s) -> {
            final Object string = config.get(s);
            if (string instanceof String) {
                config.set(s, ChatColor.translateAlternateColorCodes('&', (String) string));
            }
        });
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
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

        updateConfig();
        updateMessages();
        checkDependency();
        setupPermissions();

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
        GeneratorList.create();
        Fallback.create();
        Generator.create();
    }

    private void checkDependency() {
        if (!config.getBoolean("enableDependency")) {
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
            final FileConfiguration config = Oregen3.config;
            if (config.getBoolean("hooks.Vault.forceAsync")) {
                permissionManager = new AsyncVaultPermission();
            }
            else if (config.getStringList("hooks.Vault.pluginAsyncList").stream().anyMatch(manager::isPluginEnabled)) {
                permissionManager = new AsyncVaultPermission();
            }
        }
        else {
            getLogger().warning(StringUtils.getPrefixString("Vault not found! Offline player's permission will not be checked! Using bukkit's provided one...", null));
            permissionManager = new DefaultPermission();
        }
    }
}
