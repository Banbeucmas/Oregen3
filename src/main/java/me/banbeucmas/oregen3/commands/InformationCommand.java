package me.banbeucmas.oregen3.commands;

import me.banbeucmas.oregen3.Oregen3;
import me.banbeucmas.oregen3.gui.GeneratorMaterialList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;

import static me.banbeucmas.oregen3.util.StringParser.PLACEHOLDER_PLAYER_PATTERN;

public class InformationCommand extends AbstractCommand {
    Oregen3 plugin;
    
    InformationCommand(Oregen3 plugin, final CommandSender sender, final String label, final String[] args) {
        super(plugin, "oregen3.information", sender, label, args);
        this.plugin = plugin;
    }

    @Override
    protected ExecutionResult run() {
        final CommandSender sender = getSender();
        if (!(sender instanceof Player)) {
            return ExecutionResult.NON_PLAYER;
        }

        if (!sender.hasPermission(getPermission())) {
            return ExecutionResult.NO_PERMISSION;
        }

        Player p = (Player) sender;
        final String[] args = getArgs();
        final FileConfiguration config = plugin.getConfig();

        if (args.length > 1) {
            @SuppressWarnings("deprecation") final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (!player.hasPlayedBefore()) {
                return ExecutionResult.NO_PLAYER;
            }
            if (player.isOnline()) {
                p = player.getPlayer();
            }
            else {
                final UUID uuid = player.getUniqueId();
                World world = null;
                if (!plugin.getHook().isIslandWorldSingle() && args.length < 3) {
                    return ExecutionResult.MISSING_ARGS;
                }
                if (args.length > 2) {
                    world = Bukkit.getWorld(args[2]);
                }

                if (!plugin.hasDependency() || plugin.getUtils().getOwner(uuid, world) == null) {
                    sender.sendMessage(PLACEHOLDER_PLAYER_PATTERN.matcher(plugin.getStringParser().getColoredPrefixString(config.getString("messages.noIslandOthers"), p)).replaceAll(Matcher.quoteReplacement(Objects.requireNonNull(player.getName()))));
                    return ExecutionResult.SUCCESS;
                }

                p.openInventory(new GeneratorMaterialList(plugin, player, world).getInventory());
                return ExecutionResult.SUCCESS;
            }
        }

        if (!plugin.hasDependency() || plugin.getUtils().getOwner(p.getLocation()) == null) {
            p.sendMessage(plugin.getStringParser().getColoredPrefixString(config.getString("messages.noIsland"), p));
            return ExecutionResult.SUCCESS;
        }
        p.openInventory(new GeneratorMaterialList(plugin, p, p.getLocation()).getInventory());

        return ExecutionResult.SUCCESS;
    }
}
