package me.banbeucmas.oregen3;

import me.banbeucmas.oregen3.commands.Commands;
import me.banbeucmas.oregen3.data.DataManager;
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

import java.util.Objects;

public final class Oregen3 extends JavaPlugin {
    private boolean hasDependency = true;
    private boolean papi;
    private boolean mvdw;
    private static Oregen3 plugin;
    private static SkyblockHook hook;
    private String hookName = "None";
    private Permission perm;
    public static boolean DEBUG;

    public boolean hasDependency() {
        return hasDependency;
    }

    public boolean hasPlaceholderAPI() {
        return papi;
    }

    public boolean hasMVDWPlaceholderAPI() {
        return mvdw;
    }

    public void updateConfig() {
        final FileConfiguration config = getConfig();
        if (!config.getString("version", "0").equals("1.3.0.4")) {
            config.addDefault("global.generators.world.enabled", false);
            config.addDefault("global.generators.world.blacklist", false);
            config.addDefault("global.generators.world.list", config.getStringList("disabledWorlds"));
            config.addDefault("global.generators.sound.enabled", config.getBoolean("sound.enabled", false));
            config.addDefault("global.generators.sound.name", config.getString("sound.created", "BLOCK_FIRE_EXTINGUISH"));
            config.addDefault("global.generators.sound.volume", config.getDouble("sound.volume", 3));
            config.addDefault("global.generators.sound.pitch", config.getDouble("sound.pitch", 2));
            config.options().copyDefaults(true);
            if (!config.getBoolean("updater.copyHeader", false)) {
                config.options().copyHeader(false);
            }
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

    public Permission getPerm() {
        return perm;
    }

    @Override
    public void onEnable() {
        plugin = this;

        new MetricsLite(this, 3052);

        saveDefaultConfig();
        updateConfig();
        checkDependency();
        setupPermissions();

        if (getConfig().getBoolean("debug")) {
            DEBUG = true;
        }

        final CommandSender sender = Bukkit.getConsoleSender();
        //Send Message
        sender.sendMessage(StringUtils.getColoredString("&7&m-------------&f[Oregen3&f]&7-------------", null));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("       &fPlugin made by &e&oBanbeucmas&f, updated by &e&oxHexed", null));
        sender.sendMessage(StringUtils.getColoredString("       &f&oVersion: &e" + getDescription().getVersion(), null));
        sender.sendMessage(StringUtils.getColoredString("       &f&oHooked plugin: &e" + hookName, null));
        sender.sendMessage("");
        sender.sendMessage(StringUtils.getColoredString("------------------------------------", null));

        DataManager.loadData();

        final PluginCommand command = Objects.requireNonNull(getCommand("oregen3"));
        command.setExecutor(new Commands());
        command.setTabCompleter(new Commands());

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
            hookName = "BentoBox";
        }
        else if (manager.isPluginEnabled("SuperiorSkyblock")) {
            hook     = new SuperiorSkyblockHook();
            hookName = "SuperiorSkyblock";
        }
        else if (manager.isPluginEnabled("FabledSkyBlock")) {
            hook     = new FabledSkyBlockHook();
            hookName = "FabledSkyBlock";
        }
        else if (manager.isPluginEnabled("uSkyBlock")) {
            hook     = new uSkyBlockHook();
            hookName = "uSkyBlock";
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
        final RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            getLogger().severe(StringUtils.getPrefixString("Vault not found! Disabling plugin...", null));
            return;
        }
        perm = rsp.getProvider();
    }
}
